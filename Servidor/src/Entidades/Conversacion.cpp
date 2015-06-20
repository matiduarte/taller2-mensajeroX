/*
 * Conversacion.cpp
 *
 *  Created on: 24/3/2015
 *      Author: matias
 */

#include "Conversacion.h"

Conversacion::Conversacion(string conversacionSerializada) {
	this->deserealizar(conversacionSerializada);
}

Conversacion::Conversacion(vector<Usuario*> usuarios, vector<Mensaje*> mensajes){
	this->usuarios = usuarios;
	this->mensajes = mensajes;
	this->id = this->getId();
}

Conversacion::Conversacion(){
	this->id = keyIdConversacionNoEncontrada;
}

Conversacion::~Conversacion() {
	// TODO Auto-generated destructor stub
}

string Conversacion::getId(){
	if(!this->id.empty()){
		return this->id;
	}else{
		string id = "";
		for(unsigned int i=0; i<this->usuarios.size();i++){
			if(id != ""){
				id += "-";
			}
			id += this->usuarios[i]->getId();
		}

		return id;
	}
}

vector<Mensaje*> Conversacion::getMensajes(){
	return this->mensajes;
}


vector<Usuario*> Conversacion::getUsuarios(){
	return this->usuarios;
}

void Conversacion::agregarMensaje(Mensaje* mensaje){
	this->mensajes.push_back(mensaje);
}

/**
 * Serializa la conversacion a formato JSON
 *
 */
string Conversacion::serializar(){
	Json::Value conversacion;

	conversacion[keyIdConversacion] = this->getId();

	string idsUsuarios = "";
	for(unsigned i=0; i<this->usuarios.size();i++){
		if(idsUsuarios != ""){
			idsUsuarios += SeparadorListaBD;
		}
		idsUsuarios += this->usuarios[i]->getId();
	}
	conversacion[keyIdsUsuarios] = idsUsuarios;

	string mensajes = "";
	for(unsigned i=0; i<this->mensajes.size();i++){
		if(mensajes != ""){
			mensajes += SeparadorListaBD;
		}
		mensajes += this->mensajes[i]->serializar();
	}
	conversacion[keyMensajes] = mensajes;


	string conversacionJSON = conversacion.toStyledString();

	return conversacionJSON;
}

/**
 * Obtiene una conversacion a partir de un JSON
 *
 */
int Conversacion::deserealizar(string aDeserealizar){
	Json::Value conversacion;
	Json::Reader reader;

	bool parseoExitoso = reader.parse(aDeserealizar, conversacion);

	if (parseoExitoso){
		this->id = conversacion.get(keyIdConversacion, keyDefault).asString();

		vector<Usuario*> usuarios;
		string idsUsuarios = conversacion.get(keyIdsUsuarios, keyDefault).asString();
		vector<string> idsUsuariosSplitted = StringUtil::split(idsUsuarios, SeparadorListaBD);
		for(unsigned i=0; i<idsUsuariosSplitted.size();i++){
			Usuario* u = Usuario::obtener(idsUsuariosSplitted[i]);
			usuarios.push_back(u);
		}
		this->usuarios = usuarios;

		vector<Mensaje*> mensajes;
		string mensajesSerializados = conversacion.get(keyMensajes, keyDefault).asString();
		vector<string> mensajesSplitted = StringUtil::split(mensajesSerializados, SeparadorListaBD);
		for(unsigned i=0; i<mensajesSplitted.size();i++){
			Mensaje* m = new Mensaje(mensajesSplitted[i]);
			mensajes.push_back(m);
		}
		this->mensajes = mensajes;

		return 1;
	}else{
		//TODO: loggear el error correspondiente
		return 0;
	}
}

/**
 * Guarda la conversacion en la base de datos
 *
 */
void Conversacion::persistir(){
	BaseDeDatos *baseDeDatos = BaseDeDatos::getInstance();
	baseDeDatos->setConversacion(this);
}

/**
 * Obtiene una conversacion de la base de datos
 * @param clave Clave con la que se guarda la conversacion en la base de datos
 *
 */
Conversacion* Conversacion::obtener(string clave){
	BaseDeDatos *baseDeDatos = BaseDeDatos::getInstance();
	return baseDeDatos->getConversacion(clave);
}

/**
 * Devuelve ids de conversaciones asociadas a un usuario
 * @param claveUsuario Identificador del usuario
 *
 */
vector<string> Conversacion::obtenerIdsPorIdUsuario(string claveUsuario){
	BaseDeDatos *baseDeDatos = BaseDeDatos::getInstance();
	return baseDeDatos->getIdsConversacionPorIdUsuario(claveUsuario);
}

/**
 * Elimina una convesacion de la base de datos
 * @param clave Clave indentificadora de la conversacion
 *
 */
void Conversacion::eliminar(string clave) {
	BaseDeDatos *baseDeDatos = BaseDeDatos::getInstance();
	baseDeDatos->eliminarConversacion(clave);
}
