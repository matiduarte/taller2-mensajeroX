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

void Servidor::administrarServicio(struct mg_connection* conn){
	int servicioRequerido = parsearURI(conn);

	Servicio* servicio = new Servicio(conn);
	servicio->parsearParametros();

	switch( servicioRequerido ){
	case PRUEBA: 					servicio->prueba();					break;
	case REGISTRAR_USUARIO: 		servicio->registrarUsuario(); 		break;
	case ADMINISTRAR_PERFIL:		servicio->administrarPerfil(); 		break;
	case ALMACENAR_CONVERSACION:	servicio->almacenarConversacion();	break;
	case OBTENER_ID_CONVERSACION:	servicio->obtenerIdConversacion();	break;
	case OBTENER_CONVERSACION:	servicio->obtenerConversacion();	break;
	case CONSULTAR_USUARIO_ONLINE:	servicio->consultarUsuarioOnline();	break;
	case INVALIDO: 	cout << "servicio no encontrado." << endl;	break;
	default: 		cout << "default." << endl;

	};
	//mg_send_header(conn, "Content-Type", "application/json");

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
	}

	if(strcmp(metodo,"GET") == 0){
		if(uri_parseada.find(urlBaseUsuario)!= std::string::npos){
			return CONSULTAR_USUARIO_ONLINE;
		}
		if(uri_parseada.find(urlBaseConversacion)!= std::string::npos && uri_parseada.find(keyIdConversacion)!= std::string::npos){
			return OBTENER_CONVERSACION;
		}
		if(uri_parseada.find(urlBaseConversacion)!= std::string::npos && uri_parseada.find(keyTelefonoEmisor)!= std::string::npos && uri_parseada.find(keyTelefonoReceptor)!= std::string::npos){
			return OBTENER_ID_CONVERSACION;
		}
	}

	if(strcmp(metodo,"PUT") == 0){
		if(uri_parseada == urlBaseUsuario){
			return ADMINISTRAR_PERFIL;
		}
//		if(uri_parseada == urlBaseConversacion){
//			return ALMACENAR_CONVERSACION;
//		}
	}


	if(uri_parseada == urlPrueba)
		return PRUEBA;

	return INVALIDO;
}
