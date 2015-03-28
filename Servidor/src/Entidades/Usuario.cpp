/*
 * Usuario.cpp
 *
 *  Created on: 24/3/2015
 *      Author: matias
 */

#include "Usuario.h"

Usuario::Usuario(string nombre, string fotoDePerfil, string telefono) {

	this->nombre = nombre;
	this->fotoDePerfil = fotoDePerfil;
	this->telefono = telefono;
	this->conectado = true;
	this->id = md5(telefono);
	this->registrarUltimaConexion();

}

void Usuario::registrarUltimaConexion(){

	time_t tiempo;
	time(&tiempo);
	this->ultimaConexion = ctime (&tiempo);

}

string Usuario::getUltimaConexion(){
	return this->ultimaConexion;
}

string Usuario::getId(){
	return this->id;
}

string Usuario::getNombre(){
	return this->nombre;
}

string Usuario::getTelefono(){
	return this->telefono;
}

bool Usuario::getEstadoConexion(){
	return this->conectado;
}

string Usuario::serializar(){

	Json::Value user;

	user["Id"] = this->getId();
	user["Nombre"] = this->getNombre();
	user["Telefono"] = this->getTelefono();
	user["Estado de conexion"] = this->getEstadoConexion();
	user["Ultima Conexion"] = this->getUltimaConexion();

	string str_user = user.toStyledString();

	return str_user;
}

int Usuario::deserealizar(string aDeserealizar){

	Json::Value user;
	Json::Reader reader;

	bool parseoExitoso = reader.parse(aDeserealizar, user);

	if ( !parseoExitoso ){
		//TODO: definir que pasa cuando no lo puede convertir
	}

	return 0;
}

void Usuario::persistir(){

}


Usuario::~Usuario() {
	// TODO Auto-generated destructor stub
}

