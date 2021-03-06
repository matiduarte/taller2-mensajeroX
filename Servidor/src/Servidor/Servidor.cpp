/*
 * Servidor.cpp
 *
 *  Created on: 24/3/2015
 *      Author: matias
 */

#include "Servidor.h"

Servidor::Servidor() {
	webServer = mg_create_server(NULL, ev_handler);
}

Servidor::~Servidor() {
	mg_destroy_server(&webServer);
}

/**
 * Incia el servidor en el puerto especificado.
 * @param puerto El puerto a ser utilizado.
 */
void Servidor::iniciar(char *puerto){
	webServer = mg_create_server(NULL, ev_handler);
	mg_set_option(webServer, "listening_port", puerto);

	printf("Starting on port %s\n", mg_get_option(webServer, "listening_port"));

	for (;;) {
	  mg_poll_server(webServer, 1000);
	}

}

void test(){

}

int Servidor::ev_handler(struct mg_connection *conn, enum mg_event ev){
	if(ev == MG_REQUEST){
		thread threadCliente(administrarServicio, conn);
		threadCliente.join();
		return MG_TRUE;
	}
	else if(ev == MG_AUTH){
	  return MG_TRUE;
	}

	return MG_FALSE;
}

/**
 * Se encarga de llamar al servicio que resuelve la request.
 */
void Servidor::administrarServicio(struct mg_connection* conn){
	int servicioRequerido = parsearURI(conn);

	Servicio* servicio = new Servicio(conn);

	switch( servicioRequerido ){
	case PRUEBA: 					servicio->prueba();					break;
	case REGISTRAR_USUARIO: 		servicio->registrarUsuario(); 		break;
	case ADMINISTRAR_PERFIL:		servicio->administrarPerfil(); 		break;
	case ALMACENAR_CONVERSACION:	servicio->almacenarConversacion();	break;
	case OBTENER_ID_CONVERSACION:	servicio->obtenerIdConversacion();	break;
	case OBTENER_CONVERSACION:		servicio->obtenerConversacion();	break;
	case OBTENER_CONVERSACIONES:	servicio->obtenerConversaciones();	break;
	case CONSULTAR_USUARIO_ONLINE:	servicio->consultarUsuarioOnline();	break;
	case OBTENER_CONTACTOS:			servicio->obtenerContactos();		break;
	case ALMACENAR_LISTA_DIFUSION:	servicio->almacenarListaDifusion();	break;
	case CHECKIN_USUARIO:			servicio->checkIn();				break;
	case INVALIDO: 	mg_printf_data(conn, "Servicio no encontrado");	break;
	default: 		cout << "default." << endl;

	};

	delete servicio;
}

tipoDeServicio Servidor::parsearURI(struct mg_connection* conn){
	const char* uri = conn->uri;
	const char* metodo = conn->request_method;

	string uri_parseada(uri);
	string metodo_parseada(metodo);

	if(strcmp(metodo,"POST") == 0){
		if(uri_parseada == urlBaseUsuario){
			return REGISTRAR_USUARIO;
		}
		if(uri_parseada == urlBaseConversacion){
			return ALMACENAR_CONVERSACION;
		}
		if(uri_parseada == urlBaseListaDifusion){
			return ALMACENAR_LISTA_DIFUSION;
		}
	}

	if(strcmp(metodo,"GET") == 0){

		if(uri_parseada.find(urlBaseUsuarioConversaciones)!= std::string::npos){
			return OBTENER_CONVERSACIONES;
		}
		if(uri_parseada.find(urlBaseUsuario)!= std::string::npos){
			return CONSULTAR_USUARIO_ONLINE;
		}
		if(uri_parseada.find(urlBaseConversacionId)!= std::string::npos){
			return OBTENER_ID_CONVERSACION;
		}
		if(uri_parseada.find(urlBaseConversacion)!= std::string::npos){
			return OBTENER_CONVERSACION;
		}
		if(uri_parseada.find(urlBaseContactos)!= std::string::npos){
			return OBTENER_CONTACTOS;
		}
	}

	if(strcmp(metodo,"PUT") == 0){
		if(uri_parseada == urlBaseUsuario){
			return ADMINISTRAR_PERFIL;
		}
		if(uri_parseada.find(urlCheckinUsuario)!= std::string::npos){
			return CHECKIN_USUARIO;
		}
	}


	if(uri_parseada == urlPrueba)
		return PRUEBA;

	return INVALIDO;
}
