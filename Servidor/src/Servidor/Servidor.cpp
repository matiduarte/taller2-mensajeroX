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

Servicio* Servidor::servicio = new Servicio();

void Servidor::iniciar(char *puerto){
	webServer = mg_create_server(NULL, ev_handler);
	mg_set_option(webServer, "listening_port", puerto);

	printf("Starting on port %s\n", mg_get_option(webServer, "listening_port"));
	for (;;) {
	  mg_poll_server(webServer, 1000);
	}
}

int Servidor::ev_handler(struct mg_connection *conn, enum mg_event ev){
	  switch (ev) {
	    case MG_AUTH: return MG_TRUE;
	    case MG_REQUEST:
	      administrarServicio(conn);
	      return MG_TRUE;
	    default: return MG_FALSE;
	  }
}

void Servidor::administrarServicio(struct mg_connection *conn){
	int servicioRequerido = parsearURI(conn->uri);

	servicio->parsearParametros(conn);

	switch( servicioRequerido ){

	case PRUEBA: 					servicio->prueba();				break;
	case REGISTRAR_USUARIO: 		servicio->registrarUsuario(); 	break;
	case AUTENTICAR_USUARIO: 		servicio->autenticarUsuario();  break;
	case ADMINISTRAR_PERFIL:		servicio->administrarPerfil(); 	break;
	case CONSULTAR_USUARIO_ONLINE:									break;
	case CHECKIN_USUARIO:			servicio->checkinUsuario();     break;
	case INVALIDO: 	cout << "servicio no encontrado." << endl;	break;
	default: 		cout << "default." << endl;

	};
}

tipoDeServicio Servidor::parsearURI(const char* uri){
	string uri_parseada(uri);
	if(uri_parseada == urlPrueba) return PRUEBA;
	else if(uri_parseada == urlRegistrarUsuario) return REGISTRAR_USUARIO;
	else if (uri_parseada == urlAutenticarUsuario) return AUTENTICAR_USUARIO;
	else if (uri_parseada == urlAdministrarPerfil) return ADMINISTRAR_PERFIL;
	else if (uri_parseada == urlConsultarUsuarioOnline) return CONSULTAR_USUARIO_ONLINE;
	else if (uri_parseada == urlCheckinUsuario) return CHECKIN_USUARIO;
	else return INVALIDO;
}
