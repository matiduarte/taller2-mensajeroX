/*
 * Servicio.cpp
 *
 *  Created on: 24/3/2015
 *      Author: matias
 */

#include "Servicio.h"

int Servicio::tamanioBuffer = (1024 * 1024) * 2;

Servicio::Servicio(struct mg_connection *conn) {
	this->connexion = conn;

}

Servicio::~Servicio() {
}

/**
 * Parsea los parametros en una llamada a servicio
 *
 */
void Servicio::parsearParametros() {
	char buffer[tamanioBuffer];
	//params es un JSON con toda la data
	int result = mg_get_var(this->connexion, "params", buffer, tamanioBuffer);
	if (result < 1) {
		//TODO: loggear error
		//-1 clave no encontrada
	}

	Json::Value valorParams;
	Json::Reader reader;

	bool parseoExitoso = reader.parse(buffer, valorParams);
	if (parseoExitoso) {
		this->parametros = valorParams;
	} else {
		//TODO: loggear error
	}
}

/**
 * Devuelve un parametro cuando se realizar una llamada a servicio
 * @param nombreParametro El nombre del parametro
 * @param valorDefault Valor default que se devuelve si no se encuetra el parametro
 *
 */
string Servicio::getParametro(string nombreParametro, string valorDefault) {
	string metodo(this->connexion->request_method);
	char buffer[tamanioBuffer];
	int resultado = mg_get_var(this->connexion, nombreParametro.c_str(), buffer,
			tamanioBuffer);
	if (resultado < 1) {
		return valorDefault;
	}

	string parametro(buffer);
	return parametro;
}

/**
 * Devuelve un parametro array cuando se realizar una llamada a servicio
 * @param nombreParametro El nombre del parametro
 * @param valorDefault Valor default que se devuelve si no se encuetra el parametro
 *
 */
Json::Value Servicio::getParametroArray(string nombreParametro,
		string valorDefault) {
	string parametro = this->getParametro(nombreParametro, valorDefault);

	Json::Value valorParams;
	Json::Reader reader;

	reader.parse(parametro, valorParams);
	return valorParams;
}

/**
 * Devuelve el id del recurso al realizar una llamada por GET
 * @param urlBase La url de la que se obtendra el id
 *
 */
string Servicio::getParametroIdMetodoGET(string urlBase) {
	string parametrosUri = urlBase + "%s";

	char id[255];
	if (1 == sscanf(this->connexion->uri, parametrosUri.c_str(), &id)) {
		return id;
	}

	return "";
}

/**
 * Se utiliza para responder al cliente
 * @param mensaje La respuesta al cliente
 * @param success Si la consulta fue satisfactoria
 *
 */
void Servicio::responder(string mensaje, bool success) {
	Json::Value respuesta;
	respuesta[keyPayload] = mensaje;
	respuesta[keySuccess] = "true";
	if (!success) {
		respuesta[keySuccess] = "false";
	}

	mg_printf_data(this->connexion, respuesta.toStyledString().c_str());
}

void Servicio::prueba() {
	cout << "Esto es una prueba." << endl;
}

/**
 * Registra un usuario en el sistema
 *
 */
void Servicio::registrarUsuario() {

	Usuario* user = this->obtenerUsuario();
	if (user->getId() != keyIdUsuarioNoEncontrado) {
		//El usuario ya existe. Devuelvo error
		Loger::getLoger()->warn(
				"Se intento registrar un usuario ya existente. Id: "
						+ user->getId());
		this->responder("El usuario ya existe", false);
	} else {
		string nombre = this->getParametro(keyNombre, keyDefault);
		string fotoPerfil = this->getParametro(keyFotoDePerfil, keyDefault);
		string telefono = this->getParametro(keyTelefono, keyDefault);
		string password = this->getParametro(keyPassword, keyDefault);

		Usuario* user = new Usuario(nombre, telefono, password);

		user->persistir();
		this->responder("Usuario registrado correctamente", true);
		Loger::getLoger()->info(
				"Se registro el usuario con Id: " + user->getId());
		delete user;
	}
}

Usuario* Servicio::obtenerUsuario() {

	string telefono = this->getParametro(keyTelefono, keyDefault);
	Usuario* user = Usuario::obtenerPorTelefono(telefono);
	return user;

}

/**
 * Se encarga de actualizar los datos del usuario que recibe, en la
 * base de datos.
 */
void Servicio::administrarPerfil() {

	string nombreUsuario = this->getParametro(keyNombre, keyDefault);
	string estadoDeConexion = this->getParametro(keyEstadoDeConexion,
			keyDefault);
	string fotoDePerfil = this->getParametro(keyFotoDePerfil, keyDefault);
	bool estado = StringUtil::toBoolean(estadoDeConexion);
	string localizacion = this->getParametro(keyLocalizacion, keyDefault);
	string password = this->getParametro(keyPassword, keyDefault);
	Usuario* user = this->obtenerUsuario();
	string token = this->getParametro(keyTokenSesion, keyDefault);

	if (user->getId() != keyIdUsuarioNoEncontrado) {
		if (user->getToken() == token) {
			user->setNombre(nombreUsuario);
			user->setEstadoConexion(estado);
			user->setFotoDePerfil(fotoDePerfil);
			user->setLocalizacion(localizacion);
			user->setPassword(password);
			user->persistir();
			Loger::getLoger()->info(
					"Se modificaron los datos del usuario " + user->getNombre()
							+ " correctamente.");
			this->responder(
					"Se modificaron los datos del usuario " + user->getNombre()
							+ " correctamente. Token:" + user->getToken(),
					true);
		} else {
			Loger::getLoger()->warn(
					"El usuario " + user->getNombre()
							+ " no posee un token de session correcto");
			this->responder(
					"El usuario " + user->getNombre()
							+ " no posee un token de session correcto", false);
		}

	} else {
		Loger::getLoger()->warn(
				"El usuario " + user->getNombre()
						+ " no se encuentra registrado en el sistema");
		this->responder(
				"El usuario " + user->getNombre()
						+ " no se encuentra registrado en el sistema", false);
	}
	Loger::getLoger()->guardarEstado();
	delete user;

}

/**
 * Devuelve los datos correspondientes al usuario con telefono igual al
 * recibido.
 */
void Servicio::consultarUsuarioOnline() {
	string telefono = this->getParametroIdMetodoGET(urlBaseUsuario);
	Usuario* user = Usuario::obtenerPorTelefono(telefono);

	if (user->getId() != keyIdUsuarioNoEncontrado) {
		user->registrarUltimaConexion();

		Json::Value respuesta;
		respuesta[keyNombre] = user->getNombre();
		respuesta[keyPassword] = user->getPassword();
		respuesta[keyTokenSesion] = user->calcularTokenDeSesion();
		respuesta[keyEstadoDeConexion] = StringUtil::toString(
				user->getEstadoConexion());
		respuesta[keyFotoDePerfil] = user->getFotoDePerfil();
		respuesta["idUsuario"] = user->getId();
		respuesta[keyLocalizacion] = user->getLocalizacion();
		user->persistir();

		this->responder(respuesta.toStyledString(), true);
		Loger::getLoger()->info(
				"Consulta del usuario " + user->getNombre() + " exitosa.");

	} else {
		string msj = "No se pudo obtener el estado del usuario con numero: "
				+ this->getParametro(keyTelefono, keyDefault)
				+ " ya que no se encuentra registrado en el sistema.";

		Loger::getLoger()->warn(msj);
		this->responder(msj, false);
	}

	Loger::getLoger()->guardarEstado();
	delete user;

}

/*
 * Agrega el mensaje que envió el cliente a la conversacion correspondiente y luego la almacena en
 * la Base de Datos.
 *
 */
void Servicio::almacenarConversacion() {

	string idEmisor = this->getParametro(keyIdUsuarioEmisor, keyDefault);
	string idReceptor = this->getParametro(keyIdUsuarioReceptor, keyDefault);

	//chequeo que los usuarios existan:
	Usuario *emisor = Usuario::obtenerPorTelefono(idEmisor);
	Usuario *receptor = Usuario::obtenerPorTelefono(idReceptor);

	string token = this->getParametro(keyTokenSesion, keyDefault);

	if (emisor->getId() == keyIdUsuarioNoEncontrado
			|| receptor->getId() == keyIdUsuarioNoEncontrado) {
		string msj_warning = "No se pudo almacenar la conversacion porque: ";
		if (emisor->getId() == keyIdUsuarioNoEncontrado)
			msj_warning.append("el emisor no existe.");
		if (receptor->getId() == keyIdUsuarioNoEncontrado)
			msj_warning.append(" el receptor no existe.");
		this->responder(msj_warning, false);
		Loger::getLoger()->warn(msj_warning);
		Loger::getLoger()->guardarEstado();

	} else {
		if (emisor->getToken() == token) {
			//Obtengo el mensaje:
			string cuerpo = this->getParametro(keyCuerpo, keyDefault);
			string fecha = this->getParametro(keyFecha, keyDefault);
			Mensaje* mensaje = new Mensaje(cuerpo, emisor->getId(), fecha);

			//almaceno la conversacion (si no existe la creo):
			Conversacion *conversacion = Conversacion::obtener(
					emisor->getId() + "-" + receptor->getId());
			if (conversacion->getId() != keyIdConversacionNoEncontrada) {
				conversacion->agregarMensaje(mensaje);
				conversacion->persistir();
				delete conversacion;
			} else {
				Conversacion *conversacion = Conversacion::obtener(
						receptor->getId() + "-" + emisor->getId());
				if (conversacion->getId() != keyIdConversacionNoEncontrada) {
					conversacion->agregarMensaje(mensaje);
					conversacion->persistir();
					delete conversacion;
				} else {
					vector<Usuario*> usuarios;
					usuarios.push_back(emisor);
					usuarios.push_back(receptor);
					vector<Mensaje*> mensajes;
					mensajes.push_back(mensaje);
					Conversacion *nuevaConversacion = new Conversacion(usuarios,
							mensajes);
					nuevaConversacion->persistir();
					delete nuevaConversacion;
				}
			}

			this->responder(mensaje->getId(), true);
			delete mensaje;
		} else {
			string msj = "El usuario " + emisor->getNombre()
					+ " no posee un token de session correcto";
			Loger::getLoger()->warn(msj);
			this->responder(msj, false);
		}
	}

}

/**
 * Devuelve el id de una conversacion a partir de dos telefonos
 *
 */
void Servicio::obtenerIdConversacion() {
	string telefonoUsuarioEmisor = this->getParametro(keyTelefonoEmisor,
			keyDefault);
	;
	string telefonoUsuarioRecceptor = this->getParametro(keyTelefonoReceptor,
			keyDefault);

	Usuario* usuarioEmisor = Usuario::obtenerPorTelefono(telefonoUsuarioEmisor);
	Usuario* usuarioReceptor = Usuario::obtenerPorTelefono(
			telefonoUsuarioRecceptor);

	if (usuarioEmisor->getId() != keyIdUsuarioNoEncontrado
			&& usuarioReceptor->getId() != keyIdUsuarioNoEncontrado) {
		vector<Mensaje*> mensajes;
		vector<Usuario*> usuarios;
		usuarios.push_back(usuarioEmisor);
		usuarios.push_back(usuarioReceptor);
		Conversacion *conversacion = Conversacion::obtener(
				usuarioEmisor->getId() + "-" + usuarioReceptor->getId());
		if (conversacion->getId() == keyIdConversacionNoEncontrada) {
			conversacion = Conversacion::obtener(
					usuarioReceptor->getId() + "-" + usuarioEmisor->getId());
		}
		string idConversacion = conversacion->getId();
		if (idConversacion != keyIdUsuarioNoEncontrado) {
			this->responder(idConversacion, true);
		} else {
			//Si la conversacion no existe devuelvo como id uno default para estos dos usuarios
			Conversacion* conversacion = new Conversacion(usuarios, mensajes);
			string idConversacion = conversacion->getId();

			this->responder(idConversacion, false);
		}
	} else {
		Usuario* user;
		if (usuarioEmisor->getId() == keyIdUsuarioNoEncontrado) {
			user = usuarioEmisor;
		} else {
			user = usuarioReceptor;
		}
		string msj_warn = "Usuario " + user->getNombre()
				+ " no se encuentra registrado en el sistema";

		Loger::getLoger()->warn(msj_warn);
		this->responder(msj_warn, false);
	}

	delete usuarioEmisor;
	delete usuarioReceptor;
}

/**
 * Devuelve una conversacion a partir de dos telefonos
 *
 */
void Servicio::obtenerConversacion() {
	string idConversacion = this->getParametroIdMetodoGET(urlBaseConversacion);
	string idUltimoMensaje = this->getParametro(keyIdUltimoMensaje, keyDefault);

//Puede darse el caso de que la conversacion no exista si este servicio se llamo primero que agregarMensaje
//No deberia pasar ya que agregarMensaje se llama primero, pero por latencia de red podria llamarse a este servicio primero
	Conversacion* conversacion = Conversacion::obtener(idConversacion);
	if (conversacion->getId() != keyIdConversacionNoEncontrada) {
		vector<Mensaje*> mensajes = conversacion->getMensajes();
		vector<Mensaje*> mensajesRespuesta;

		if (idUltimoMensaje != "") {
			bool encontrado = false;
			for (unsigned i = 0; i < mensajes.size(); i++) {
				if (encontrado) {
					mensajesRespuesta.push_back(mensajes[i]);
				}

				if (!encontrado && mensajes[i]->getId() == idUltimoMensaje) {
					//A partir del proximo mensaje tengo que enviar todos
					encontrado = true;
				}
			}
		} else {
			//El cliente no tiene mensajes en su aplicacion. Tengo que enviar la conversacion entera
			mensajesRespuesta = mensajes;
		}

		Json::Value respuesta;
		for (unsigned i = 0; i < mensajesRespuesta.size(); i++) {
			respuesta["mensajes"][i] = mensajesRespuesta[i]->serializar();
		}
		this->responder(respuesta.toStyledString(), true);

	} else {
		string msj_warn = "La conversacion " + idConversacion
				+ " no se encuentra en el sistema";

		Loger::getLoger()->warn(msj_warn);
		this->responder(msj_warn, false);
	}
}

/**
 * Devuelve las conversciones a partir de un usuario. Recibe un arreglo de de ids de conversaciones,
 * y devuelve las que no estan en ese arreglo
 *
 */

void Servicio::obtenerConversaciones() {
	Json::Value idsConversacionesValue = this->getParametroArray(
	keyIdConversaciones, keyDefault);
	vector<string> idsConversaciones = StringUtil::jsonValueToVector(
			idsConversacionesValue);

	string idUsuario = this->getParametroIdMetodoGET(
			urlBaseUsuarioConversaciones);

	Usuario* usuario = Usuario::obtenerPorTelefono(idUsuario);
	if (usuario->getId() != keyIdUsuarioNoEncontrado) {
		vector<string> idsConversacionesActuales =
				usuario->obtnerIdsConversaciones();
		vector<Conversacion*> nuevasConversaciones;

		for (unsigned i = 0; i < idsConversacionesActuales.size(); i++) {
			string idActual = idsConversacionesActuales[i];

			//Si no esta en las conversaciones que me llegan, quiere decir que es una nueva conversacion.
			//Tengo que enviarla al cliente
			if (!StringUtil::vectorContiene(idsConversaciones, idActual)) {
				Conversacion* nuevaConversacion = Conversacion::obtener(
						idActual);
				if (nuevaConversacion->getId()
						!= keyIdConversacionNoEncontrada) {
					nuevasConversaciones.push_back(nuevaConversacion);
				} else {
					Loger::getLoger()->warn(
							"La conversacion " + idActual
									+ " no se encuentra en el sistema");
				}
			}
		}

		Json::Value respuesta;

		for (unsigned i = 0; i < nuevasConversaciones.size(); i++) {
			Conversacion* conv = nuevasConversaciones[i];

			vector<Mensaje*> mens = conv->getMensajes();
			Mensaje* ultimoMensj = mens[mens.size() - 1];

			Usuario* usuarioContacto = conv->getUsuarios().at(0);
			if (usuarioContacto->getId() == usuario->getId()) {
				usuarioContacto = conv->getUsuarios().at(1);
			}

			respuesta["conversaciones"][i]["id"] = conv->getId();
			respuesta["conversaciones"][i]["ultimoMensaje"] =
					ultimoMensj->getCuerpo();
			respuesta["conversaciones"][i]["usuarioNombre"] =
					usuarioContacto->getNombre();
			respuesta["conversaciones"][i]["usuarioTelefono"] =
					usuarioContacto->getTelefono();
			respuesta["conversaciones"][i]["usuarioFotoDePerfil"] =
					usuarioContacto->getFotoDePerfil();

		}

		this->responder(respuesta.toStyledString(), true);

	} else {
		Loger::getLoger()->warn(
				"El usuario " + idUsuario + " no se encuentra en el sistema");
	}

}

/**
 * Obtiene los contactos que se encuentran registrados en el sistemas y estan conectados,
 * a partir de numeros de telefono que recibe
 *
 */
void Servicio::obtenerContactos() {
	Json::Value contactosTelefonoValue = this->getParametroArray(
	keyContantosTelefono, keyDefault);
	vector<string> contactosTelefono = StringUtil::jsonValueToVector(
			contactosTelefonoValue);

	Json::Value respuesta;
	int counter = 0;
	for (unsigned i = 0; i < contactosTelefono.size(); i++) {
		string telefonoActual = contactosTelefono[i];
		Usuario* usuario = Usuario::obtenerPorTelefono(telefonoActual);

		//Agrego los usuarios que estan registrados y que se encuentran conectados

		if (usuario->getId() != keyIdUsuarioNoEncontrado
				&& usuario->getEstadoConexion()) {
			respuesta["contactos"][counter][keyNombre] = usuario->getNombre();
			respuesta["contactos"][counter][keyTelefono] =
					usuario->getTelefono();
			respuesta["contactos"][counter][keyFotoDePerfil] =
					usuario->getFotoDePerfil();
			respuesta["contactos"][counter][keyLocalizacion] = usuario->getLocalizacion();
			counter++;
		}
	}

	this->responder(respuesta.toStyledString(), true);
}

/*
 * Agrega el mensaje que envió el cliente a la conversacion correspondiente y luego la almacena en
 * la Base de Datos.
 *
 */
void Servicio::almacenarListaDifusion() {
	string idEmisor = this->getParametro(keyIdUsuarioEmisor, keyDefault);
	string token = this->getParametro(keyTokenSesion, keyDefault);

	Json::Value contactosTelefonoValue = this->getParametroArray(
	keyContantosTelefono, keyDefault);
	vector<string> contactosTelefono = StringUtil::jsonValueToVector(
			contactosTelefonoValue);

//chequeo que los usuarios existan:
	Usuario *emisor = Usuario::obtenerPorTelefono(idEmisor);

	if (emisor->getId() == keyIdUsuarioNoEncontrado) {
		string msj_warning =
				"No se pudo almacenar la lista de difusion porque el emisor no existe";
		this->responder(msj_warning, false);
		Loger::getLoger()->warn(msj_warning);
		Loger::getLoger()->guardarEstado();
	} else if (emisor->getToken() != token) {
		Loger::getLoger()->warn(
				"El usuario " + emisor->getNombre()
						+ " no posee un token de session correcto");
		this->responder(
				"El usuario " + emisor->getNombre()
						+ " no posee un token de session correcto", false);
	} else {
		//Obtengo el mensaje:
		string cuerpo = this->getParametro(keyCuerpo, keyDefault);
		string fecha = this->getParametro(keyFecha, keyDefault);
		Mensaje* mensaje = new Mensaje(cuerpo, emisor->getId(), fecha);

		//Recorro los contactos del telefono para verificar cuales estan registrados y conectados
		for (unsigned i = 0; i < contactosTelefono.size(); i++) {
			string telefonoActual = contactosTelefono[i];
			Usuario* usuario = Usuario::obtenerPorTelefono(telefonoActual);

			//Envío mensaje a los usuarios que estan registrados y que se encuentran conectados
			if (usuario->getId() != keyIdUsuarioNoEncontrado
					&& usuario->getEstadoConexion()) {
				//almaceno la conversacion (si no existe la creo):
				Conversacion *conversacion = Conversacion::obtener(
						emisor->getId() + "-" + usuario->getId());
				if (conversacion->getId() != keyIdConversacionNoEncontrada) {
					conversacion->agregarMensaje(mensaje);
					conversacion->persistir();
					delete conversacion;
				} else {
					Conversacion *conversacion = Conversacion::obtener(
							usuario->getId() + "-" + emisor->getId());
					if (conversacion->getId()
							!= keyIdConversacionNoEncontrada) {
						conversacion->agregarMensaje(mensaje);
						conversacion->persistir();
						delete conversacion;
					} else {
						vector<Usuario*> usuarios;
						usuarios.push_back(emisor);
						usuarios.push_back(usuario);
						vector<Mensaje*> mensajes;
						mensajes.push_back(mensaje);
						Conversacion *nuevaConversacion = new Conversacion(
								usuarios, mensajes);
						nuevaConversacion->persistir();
						delete nuevaConversacion;
					}
				}
			}
		}

		this->responder("Lista de difusion enviada correctamente", true);
		delete mensaje;
	}
}

/**
 * Se encarga de calcular en que lugar se encuentra el cliente
 * en base a sus coordenadas geográficas y los lugares precargados.
 *
 */
void Servicio::checkIn() {
	Json::Value coordenadas;
	string latitud = this->getParametro(keyLatitud, keyDefault);
	string longitud = this->getParametro(keyLongitud, keyDefault);
	coordenadas["latitud"] = atof(latitud.c_str());
	coordenadas["longitud"] = atof(longitud.c_str());

	Usuario* usuario = this->obtenerUsuario();
	if (usuario->getId() != keyIdUsuarioNoEncontrado) {
		usuario->setLocalizacion(Localizacion::calcularUbicacion(coordenadas));
		this->responder(usuario->getLocalizacion(), true);
	} else {
		this->responder("el usuario no existe.", false);
	}
}

