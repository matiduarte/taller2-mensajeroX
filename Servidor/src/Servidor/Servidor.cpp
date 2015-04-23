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
	int servicioRequerido = parsearURI(conn->uri);

	servicio->parsearParametros(conn);

	switch( servicioRequerido ){
	case PRUEBA: 					servicio->prueba();					break;
	case REGISTRAR_USUARIO: 		servicio->registrarUsuario(); 		break;
	case AUTENTICAR_USUARIO: 		servicio->autenticarUsuario();  	break;
	case ADMINISTRAR_PERFIL:		servicio->administrarPerfil(); 		break;
	case CONSULTAR_USUARIO_ONLINE:	servicio->consultarUsuarioOnline();	break;
	case CHECKIN_USUARIO:			servicio->checkinUsuario();     	break;
	case DESCONECTAR_USUARIO:		servicio->desconectarUsuario(); 	break;
	case ALMACENAR_CONVERSACION:	servicio->almacenarConversacion();	break;
	case INVALIDO: 	cout << "servicio no encontrado." << endl;	break;
	default: 		cout << "default." << endl;

	};
	//mg_send_header(conn, "Content-Type", "application/json");
	mg_printf_data(conn, "prueba respuesta");
}

tipoDeServicio Servidor::parsearURI(const char* uri){

	//TDD STYLE!!!!!!!!!!!!!!!!!!!!!!!
	string uri_parseada(uri);
	if(uri_parseada == urlPrueba)
		return PRUEBA;
	if(uri_parseada == urlRegistrarUsuario)
		return REGISTRAR_USUARIO;
	if (uri_parseada == urlAutenticarUsuario)
		return AUTENTICAR_USUARIO;
	if (uri_parseada == urlAdministrarPerfil)
		return ADMINISTRAR_PERFIL;
	if (uri_parseada == urlConsultarUsuarioOnline)
		return CONSULTAR_USUARIO_ONLINE;
	if (uri_parseada == urlCheckinUsuario)
		return CHECKIN_USUARIO;
	if (uri_parseada == urlDesconectarUsuario)
		return DESCONECTAR_USUARIO;
	if (uri_parseada == urlAlmacenarConversacion)
		return ALMACENAR_CONVERSACION;
	return INVALIDO;
}
