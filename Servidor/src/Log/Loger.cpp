/*
 * Loger.cpp
 *
 *  Created on: 2/4/2015
 *      Author: matias
 */

#include "Loger.h"

Loger* Loger::logInstancia = NULL;

Loger::Loger(){

	ofstream* arch = new ofstream;
	arch->open(archivoLog, ofstream::trunc);
	archivo = arch;
	this->nivel = nivelLogger;
	ponerFecha();
}

void Loger::escribir(string texto){
	this->archivo->write(texto.c_str(), strlen(texto.c_str()));
	this->archivo->write("\n", strlen("\n"));

}

void Loger::guardarEstado(){

	this->archivo->close();
	this->archivo->open(archivoLog, ofstream::app);

}

void Loger::ponerFecha(){
	time_t fecha;
	time ( &fecha);
	string sfecha = ctime (&fecha);

	string guion = "-----------------------------------------------------------------";
	escribir(guion);
	sfecha.append(guion);
	escribir(sfecha);
}

void Loger::modificarNivel(string nivel){
	this->setNivel(nivel);
}

void Loger::setNivel(string nivel){
	this->nivel = nivel;
}

string Loger::getNivel(){
	return this->nivel;
}

void Loger::error(string texto){

	this->escribir(texto);

}

void Loger::warn(string texto){

	string nivel = StringUtil::toLower(this->getNivel());
	if ((nivel == Warn) || (nivel == Info)  || (nivel == Debug))
		this->escribir(texto);

}

void Loger::info(string texto){

	string nivel = StringUtil::toLower(this->getNivel());
	if ((nivel == Info)  || (nivel == Debug))
		this->escribir(texto);

}

void Loger::debug(string texto){

	string nivel = StringUtil::toLower(this->getNivel());
	if ((nivel == Debug))
		this->escribir(texto);

}

Loger::~Loger(){

	this->archivo->close();
	delete this->archivo;
}


Loger* Loger::getLoger(){

	if(logInstancia == NULL) logInstancia = new Loger();

	return logInstancia;
}

