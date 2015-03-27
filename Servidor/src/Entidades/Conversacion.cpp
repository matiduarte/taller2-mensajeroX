/*
 * Conversacion.cpp
 *
 *  Created on: 24/3/2015
 *      Author: matias
 */

#include "Conversacion.h"

Conversacion::Conversacion() {
	// TODO Auto-generated constructor stub
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
	if(this->id.empty()){
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
	string serializado = "";
	return serializado;
}

int Conversacion::deserealizar(string aDeserealizar){
	return 0;
}

void Conversacion::persistir(){
}

