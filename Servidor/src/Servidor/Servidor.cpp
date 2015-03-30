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

	case prueba: 	servicio->prueba(); 							break;
	case registrarUsuario: 	servicio->registrarUsuario(); 							break;
	case invalido: 	cout << "servicio no encontrado." << endl;	break;
	default: 		cout << "default." << endl;

	};
}

tipoDeServicio Servidor::parsearURI(const char* uri){
	string uri_parseada(uri);
	if(uri_parseada == "/prueba") return prueba;
	else if(uri_parseada == "/registrarUsuario") return registrarUsuario;
	else return invalido;
}
