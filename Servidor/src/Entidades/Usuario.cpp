/*
 * Usuario.cpp
 *
 *  Created on: 24/3/2015
 *      Author: matias
 */

#include "Usuario.h"

Usuario::Usuario(string nombre, string fotoDePerfil, string telefono, string password) {

	this->nombre = nombre;
	this->fotoDePerfil = fotoDePerfil;
	this->telefono = telefono;
	this->conectado = Online;
	this->id = md5(telefono);
	this->registrarUltimaConexion();
	this->localizacion = "";
	this->token = "";
	this->password = password;
}

Usuario::Usuario(string nombre, string telefono, string password){

		this->nombre = nombre;
		this->fotoDePerfil = fotoDePerfilDefault;
		this->telefono = telefono;
		this->conectado = Online;
		this->id = md5(telefono);
		this->registrarUltimaConexion();
		this->localizacion = "";
		this->token = "";
		this->password = password;
}

Usuario::Usuario(){
	this->id = keyIdUsuarioNoEncontrado;
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


Usuario::Usuario(string usuarioSerializado) {
	this->deserealizar(usuarioSerializado);
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

void Usuario::setFotoDePerfil(string foto){
	this->fotoDePerfil = foto;
}

string Usuario::getFotoDePerfil(){
	return this->fotoDePerfil;
}

string Usuario::getLocalizacion(){
	return this->localizacion;
}

void Usuario::setLocalizacion(string localizacion){
	this->localizacion = localizacion;
}

string Usuario::serializar(){

	Json::Value user;

	user[keyId] = this->getId();
	user[keyNombre] = this->getNombre();
	user[keyTelefono] = this->getTelefono();
	user[keyEstadoDeConexion] = this->getEstadoConexion();
	user[keyUltimaConexion] = this->getUltimaConexion();
	user[keyFotoDePerfil] = this->getFotoDePerfil();
	user[keyLocalizacion] = this->getLocalizacion();
	user[keyTokenSesion] = this->token;
	user[keyPassword] = this->getPassword();

	string str_user = user.toStyledString();

	return str_user;
}

int Usuario::deserealizar(string aDeserealizar){

	Json::Value user;
	Json::Reader reader;

	bool parseoExitoso = reader.parse(aDeserealizar, user);

	if ( parseoExitoso ){
		this->setId(user.get(keyId, keyDefault).asString());
		this->setNombre(user.get(keyNombre, keyDefault).asString());
		this->setTelefono(user.get(keyTelefono, keyDefault).asString());
		//this->setEstadoConexion(user.get(keyEstadoDeConexion, keyDefault).asBool());
		this->setUltimaConexion(user.get(keyUltimaConexion, keyDefault).asString());
		this->setFotoDePerfil(user.get(keyFotoDePerfil, keyDefault).asString());
		this->setLocalizacion(user.get(keyLocalizacion, keyDefault).asString());
		this->token = user.get(keyTokenSesion, keyDefault).asString();
		this->setPassword(user.get(keyPassword,keyDefault).asString());
	} else {
		Loger::getLoger()->error("no se pudieron deserializar los datos correctamente");
	}

	Loger::getLoger()->guardarEstado();

	return parseoExitoso;

}

void Usuario::persistir(){
	BaseDeDatos *baseDeDatos = BaseDeDatos::getInstance();
	baseDeDatos->setUsuario(this);
}

Usuario* Usuario::obtener(string clave){
	BaseDeDatos *baseDeDatos = BaseDeDatos::getInstance();
	return baseDeDatos->getUsuario(clave);
}

Usuario* Usuario::obtenerPorTelefono(string clave){
	BaseDeDatos *baseDeDatos = BaseDeDatos::getInstance();
	return baseDeDatos->getUsuario(obtenerId(clave));
}

void Usuario::eliminar(string clave){
	BaseDeDatos *baseDeDatos = BaseDeDatos::getInstance();
	baseDeDatos->eliminarUsuario(clave);
}


Usuario::~Usuario() {
	// TODO Auto-generated destructor stub
}

string Usuario::obtenerId(string telefono){
	return md5(telefono);

}

string Usuario::calcularTokenDeSesion(){

	return this->token = md5(this->getTelefono() + this->getUltimaConexion());
}

vector<string> Usuario::obtnerIdsConversaciones(){
	return Conversacion::obtenerIdsPorIdUsuario(this->getId());
}

string Usuario::getToken(){
	return this->token;
}

string Usuario::getPassword(){
	return this->password;
}

void Usuario::setPassword(string password){
	this->password = password;
}
