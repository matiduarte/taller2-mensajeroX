/*
 * Usuario.cpp
 *
 *  Created on: 24/3/2015
 *      Author: matias
 */

#include "Usuario.h"

 /**
  * Constructor.
  * @param nombre: nombre asignado a un usuario.
  * @param fotoDePerfil: foto de perfil asignada a un usuario.
  * @param telefono: telefono asignado a un usuario.
  * @param password: pass asginado a un usuario
  */
Usuario::Usuario(string nombre, string fotoDePerfil, string telefono, string password) {

	this->nombre = nombre;
	this->fotoDePerfil = fotoDePerfil;
	this->telefono = telefono;
	this->id = md5(telefono);
	this->registrarUltimaConexion();
	this->localizacion = "";
	this->token = "";
	this->password = password;
}
/**
 * Constructor.
 * @param nombre: nombre asignado a un usuario.
 * @param telefono: telefono asignado a un usuario.
 * @param password: pass asginado a un usuario
 */
Usuario::Usuario(string nombre, string telefono, string password){

		this->nombre = nombre;
		this->fotoDePerfil = fotoDePerfilDefault;
		this->telefono = telefono;
		this->id = md5(telefono);
		this->registrarUltimaConexion();
		this->localizacion = "";
		this->token = "";
		this->password = password;
}

/**
 * Constructor.
 */
Usuario::Usuario(){
	this->id = keyIdUsuarioNoEncontrado;
}

/**
 * Registra el horario de la ultima conexion del usuario.
 */
void Usuario::registrarUltimaConexion(){

	time_t tiempo;
	time(&tiempo);
	this->ultimaConexion = ctime (&tiempo);

}
/**
 * Getter.
 * @return ultimaConexion: ultima vez que el usuario inicio sesion.
 */
string Usuario::getUltimaConexion(){
	return this->ultimaConexion;
}

/**
 * Getter.
 * @return id: identificador del usuario.
 */
string Usuario::getId(){
	return this->id;
}

/**
 * Constructor.
 * @param usuarioSerializado: usuario con todos su datos en formato string.
 */
Usuario::Usuario(string usuarioSerializado) {
	this->deserealizar(usuarioSerializado);
}

/**
 * Getter.
 * @return nombre: nombre del usuario
 */
string Usuario::getNombre(){
	return this->nombre;
}
/**
 * Getter.
 * @return telefono: telefono del usuario
 */
string Usuario::getTelefono(){
	return this->telefono;
}
/**
 * Getter.
 * @return conectado: true si se encuentra logueado, false en caso contrario
 */
bool Usuario::getEstadoConexion(){
	return this->conectado;
}
/**
 * Setter.
 * @param id: identificador de un usuario
 */
void Usuario::setId(string id){
	this->id = id;
}
/**
 * Setter.
 * @param nombre: nombre asignado a un usuario
 */
void Usuario::setNombre(string nombre){
	this->nombre = nombre;
}
/**
 * Setter.
 * @param telefono: telefono de un usuario
 */
void Usuario::setTelefono(string telefono){
	this->telefono = telefono;
}
/**
 * Setter.
 * @param estado: identificador de un usuario
 */
void Usuario::setEstadoConexion(bool estado){
	this->conectado = estado;
}
/**
 * Setter.
 * @param ultimaConexion: horario de ultima conexion del usuario
 */
void Usuario::setUltimaConexion(string ultimaConexion){
	this->ultimaConexion = ultimaConexion;
}
/**
 * Setter.
 * @param fotoDePerfil: foto de perfil del usuario
 */
void Usuario::setFotoDePerfil(string foto){
	this->fotoDePerfil = foto;
}
/**
 * Getter.
 * @return fotoDePerfil: foto de perfil del usuario
 */
string Usuario::getFotoDePerfil(){
	return this->fotoDePerfil;
}
/**
 * Getter.
 * @return localizacion: coordenadas en donde se encuentra el usuario
 */
string Usuario::getLocalizacion(){
	return this->localizacion;
}
/**
 * Setter.
 * @param localizacion: coordenadas en donde se encuentra el usuario.
 */
void Usuario::setLocalizacion(string localizacion){
	this->localizacion = localizacion;
}

/**
 * Almacena todos los atributos del usuario en formato Json.
 * @return		un usuario serializado en formato string.
 */
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

/**
 * Obtiene los datos de un usuario en formato Json y luego se los setea.
 * @param		usuario a deserializar en formato string.
 * @return		true si el parseo fue existo, false sino.
 */
int Usuario::deserealizar(string aDeserealizar){

	Json::Value user;
	Json::Reader reader;

	bool parseoExitoso = reader.parse(aDeserealizar, user);

	if ( parseoExitoso ){
		this->setId(user.get(keyId, keyDefault).asString());
		this->setNombre(user.get(keyNombre, keyDefault).asString());
		this->setTelefono(user.get(keyTelefono, keyDefault).asString());
		string estado = user.get(keyEstadoDeConexion, keyDefault).asString();
		this->setEstadoConexion( StringUtil::toBoolean(estado) );
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
/**
 * Almacena un usuario en la base de datos.
 */
void Usuario::persistir(){
	BaseDeDatos *baseDeDatos = BaseDeDatos::getInstance();
	baseDeDatos->setUsuario(this);
}
/**
 * Obtiene un usuario de la base de datos a partir de su clave.
 * @param		clave, telefono del usuario hasheado.
 * @return 		instacia de Usuario.
 */
Usuario* Usuario::obtener(string clave){
	BaseDeDatos *baseDeDatos = BaseDeDatos::getInstance();
	return baseDeDatos->getUsuario(clave);
}
/**
 * Obtiene un usuario de la base de datos por medio de su telefono.
 * @param		clave, telefono del usuario hasheado.
 * @return 		instacia de Usuario.
 */
Usuario* Usuario::obtenerPorTelefono(string clave){
	BaseDeDatos *baseDeDatos = BaseDeDatos::getInstance();
	return baseDeDatos->getUsuario(obtenerId(clave));
}
/**
 * Elimina un usuario de la base de datos.
 * @param		clave, telefono del usuario hasheado
 */
void Usuario::eliminar(string clave){
	BaseDeDatos *baseDeDatos = BaseDeDatos::getInstance();
	baseDeDatos->eliminarUsuario(clave);
}


Usuario::~Usuario() {
	// TODO Auto-generated destructor stub
}
/**
 * Devuelve el identificador de un usuario.
 * @param	telefono: numero de telefono asignado a un usuario.
 * @return	id.
 */
string Usuario::obtenerId(string telefono){
	return md5(telefono);

}

string Usuario::calcularTokenDeSesion(){

	return this->token = md5(this->getTelefono() + this->getUltimaConexion());
}
/**
 * Devuelve los identificadores de las conversaciones a la cual pertenece un usuario.
 * @return vector<String>: vector con los identificadores
 */
vector<string> Usuario::obtnerIdsConversaciones(){
	return Conversacion::obtenerIdsPorIdUsuario(this->getId());
}

/**
 * Getter.
 * @return token: se calcular a partir del telefono y otros datos
 */
string Usuario::getToken(){
	return this->token;
}

/**
 * Getter.
 * @return password: pass de un usuario.
 */
string Usuario::getPassword(){
	return this->password;
}
/**
 * Setter.
 * @param password: pass de un usuario.
 */
void Usuario::setPassword(string password){
	this->password = password;
}
