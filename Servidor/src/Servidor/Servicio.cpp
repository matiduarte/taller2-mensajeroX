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
	string nombreUsuario = this->getParametro(keyNombre, keyDefault);
	cout << "Nombre es: "<< nombreUsuario << endl;
}

void Servicio::autenticarUsuario(){

	string telefono = this->getParametro(keyTelefono, keyDefault);
	string clave = Usuario::obtenerId(telefono);
	Usuario* user = Usuario::obtener(clave);

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

}
