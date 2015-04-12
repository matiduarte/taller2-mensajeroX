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

void Conversacion::persistir(){
	BaseDeDatos *baseDeDatos = BaseDeDatos::getInstance();
	baseDeDatos->setConversacion(this);
}

Conversacion* Conversacion::obtener(string clave){
	BaseDeDatos *baseDeDatos = BaseDeDatos::getInstance();
	return baseDeDatos->getConversacion(clave);
}

vector<string> Conversacion::obtenerIdsPorIdUsuario(string claveUsuario){
	vector<string> idsConversacion;
	return idsConversacion;
}

void Conversacion::eliminar(string clave) {
	BaseDeDatos *baseDeDatos = BaseDeDatos::getInstance();
	baseDeDatos->eliminarConversacion(clave);
}
