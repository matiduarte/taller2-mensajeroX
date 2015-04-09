/*
 * Servicio.cpp
 *
 *  Created on: 24/3/2015
 *      Author: matias
 */

#include "Servicio.h"

Servicio::Servicio() {
	// TODO Auto-generated constructor stub

}

Servicio::~Servicio() {
	// TODO Auto-generated destructor stub
}

void Servicio::parsearParametros(struct mg_connection *conn){
	char buffer[1000];
	//params es un JSON con toda la data
	int result = mg_get_var(conn, "params", buffer, 1000);
	if(result < 1){
		//TODO: loggear error
		//-1 clave no encontrada
	}

	Json::Value valorParams;
	Json::Reader reader;

	bool parseoExitoso = reader.parse(buffer, valorParams);
	if(parseoExitoso){
		this->parametros = valorParams;
	}else{
		//TODO: loggear error
	}
}

string Servicio::getParametro(string nombreParametro, string valorDefault){
	return this->parametros.get(nombreParametro, valorDefault).asString();
}

void Servicio::prueba(){
	cout << "Esto es una prueba." << endl;
}

void Servicio::registrarUsuario(){
	string telefono = this->getParametro(keyTelefono, keyDefault);

	string clave = Usuario::obtenerId(telefono);
	Usuario* user = Usuario::obtener(clave);
	if(user->getId() != keyIdUsuarioNoEncontrado){
		//El usuario ya existe. Devuelvo error
		Loger::getLoger()->warn("Se intento registrar un usuario ya existente. Id: " + clave);
	}else{
		string nombre = this->getParametro(keyNombre, keyDefault);
		string fotoPerfil = this->getParametro(keyFotoDePerfil, keyDefault);

		Usuario* user = new Usuario(nombre, fotoPerfil, telefono);
		user->persistir();
	}
}

Usuario* Servicio::obtenerUsuario(){

	string telefono = this->getParametro(keyTelefono, keyDefault);
	string clave = Usuario::obtenerId(telefono);
	Usuario* user = Usuario::obtener(clave);

	return user;

}

void Servicio::autenticarUsuario(){

	Usuario* user = this->obtenerUsuario();

	if (user->getId() != keyIdUsuarioNoEncontrado){
		user->setEstadoConexion(Online);
		user->persistir();
		Loger::getLoger()->info("El usuario "+user->getNombre()+ " inicio sesion correctamente.");
	} else {
		Loger::getLoger()->warn("Usuario "+user->getNombre()+ " no se encuentra registrado en el sistema");
	}
	Loger::getLoger()->guardarEstado();
	delete user;
}

void Servicio::administrarPerfil(){

	string nombreUsuario = this->getParametro(keyNombre, keyDefault);
	string estadoDeConexion = this->getParametro(keyEstadoDeConexion, keyDefault);
	string fotoDePerfil = this->getParametro(keyFotoDePerfil, keyDefault);
	bool estado = StringUtil::toBoolean(estadoDeConexion);
	string localizacion = this->getParametro(keyLocalizacion, keyDefault);
	Usuario* user = this->obtenerUsuario();

	if (user->getId() != keyIdUsuarioNoEncontrado){
		user->setNombre(nombreUsuario);
		user->setEstadoConexion(estado);
		user->setFotoDePerfil(fotoDePerfil);
		user->setLocalizacion(localizacion);
		user->persistir();
		Loger::getLoger()->info("Se modificaron los datos del usuario "+user->getNombre()+ " correctamente.");
	} else {
		Loger::getLoger()->warn("El usuario "+user->getNombre()+ " no se encuentra registrado en el sistema");
	}
	Loger::getLoger()->guardarEstado();
	delete user;

}

bool Servicio::consultarUsuarioOnline() {

	Usuario* user = this->obtenerUsuario();
	bool estado;

	if (user->getId() != keyIdUsuarioNoEncontrado) {
		Loger::getLoger()->info("Consulta de estado del usuario "+user->getNombre()+ " exitosa.");
		estado = user->getEstadoConexion();
	} else {
		estado = false;
		Loger::getLoger()->warn(
								"No se pudo obtener el estado del usuario con numero: "
								+ this->getParametro(keyTelefono, keyDefault)
								+ " ya que no se encuentra registrado en el sistema.");
	}

	Loger::getLoger()->guardarEstado();
	delete user;
	return estado;

}
