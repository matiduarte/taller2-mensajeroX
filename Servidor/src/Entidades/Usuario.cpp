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

void Usuario::setId(string id){
	this->id = id;
}

void Usuario::setNombre(string nombre){
	this->nombre = nombre;
}

void Usuario::setTelefono(string telefono){
	this->telefono = telefono;
}

void Usuario::setEstadoConexion(bool estado){
	this->conectado = estado;
}

void Usuario::setUltimaConexion(string ultimaConexion){
	this->ultimaConexion = ultimaConexion;
}

string Usuario::serializar(){

	Json::Value user;

	user[keyId] = this->getId();
	user[keyNombre] = this->getNombre();
	user[keyTelefono] = this->getTelefono();
	user[keyEstadoDeConexion] = this->getEstadoConexion();
	user[keyUltimaConexion] = this->getUltimaConexion();

	string str_user = user.toStyledString();

	return str_user;
}

int Usuario::deserealizar(string aDeserealizar){

	Json::Value user;
	Json::Reader reader;

	bool parseoExitoso = reader.parse(aDeserealizar, user);

	if ( parseoExitoso ){
		this->setId(user.get(keyId, "").asString());
		this->setNombre(user.get(keyNombre, "").asString());
		this->setTelefono(user.get(keyTelefono, "").asString());
		this->setEstadoConexion(user.get(keyEstadoDeConexion, "").asBool());
		this->setUltimaConexion(user.get(keyUltimaConexion, "").asString());
	}

	cout << this->getNombre() << endl;
	cout << this->getTelefono() << endl;

	return 0;
}

void Usuario::persistir(){

}


Usuario::~Usuario() {
	// TODO Auto-generated destructor stub
}

