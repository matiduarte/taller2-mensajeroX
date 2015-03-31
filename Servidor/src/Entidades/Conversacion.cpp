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

Conversacion::Conversacion(vector<Usuario*> usuarios, vector<string> mensajes){
	this->usuarios = usuarios;
	this->mensajes = mensajes;
	this->id = this->getId();
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

vector<string> Conversacion::getMensajes(){
	return this->mensajes;
}

void Conversacion::agregarMensaje(string mensaje){
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
		mensajes += this->mensajes[i];
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
		this->id = conversacion.get(keyIdConversacion, "").asString();

		vector<Usuario*> usuarios;
		string idsUsuarios = conversacion.get(keyIdsUsuarios, "").asString();
		vector<string> idsUsuariosSplitted = StringUtil::split(idsUsuarios, SeparadorListaBD);
		for(unsigned i=0; i<idsUsuariosSplitted.size();i++){
			//TODO: obtener el usuario de la base de datos con el id
			Usuario* u = new Usuario("nombre","foto","4646");
			usuarios.push_back(u);
		}
		this->usuarios = usuarios;

		string mensajes = conversacion.get(keyMensajes, "").asString();
		vector<string> mensajesSplitted = StringUtil::split(mensajes, SeparadorListaBD);
		this->mensajes = mensajesSplitted;

		return 1;
	}else{
		//TODO: loggear el error correspondiente
		return 0;
	}
}

void Conversacion::persistir(){
}

