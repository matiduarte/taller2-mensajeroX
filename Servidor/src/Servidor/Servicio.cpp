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
	//params es un JSON
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
	string nombreUsuario = this->getParametro("nombre", "nombreDeafult");
	cout << "Nombre es: "<< nombreUsuario << endl;
}

bool Servicio::autenticarUsuario(){

	/*TODO: solicitar al usuario nombre y contraseña
	 * 		buscar el password en la base de datos
	 * 		comparar con el password ingresado
	 * 		Si password coincide
	 * 			Ingresa al sistema
	 * 		Si no
	 * 			Requerir datos nuevamente
	 * 		Devolver Token (?)
	 */
	return true;
}
