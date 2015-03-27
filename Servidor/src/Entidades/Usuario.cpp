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
	this->ultimaConexion = "";

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

string Usuario::serializar(){
	string serializado = "";
	return serializado;
}

int Usuario::deserealizar(string aDeserealizar){
	return 0;
}

void Usuario::persistir(){
}


Usuario::~Usuario() {
	// TODO Auto-generated destructor stub
}

