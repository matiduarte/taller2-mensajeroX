/*
 * Logger.cpp
 *
 *  Created on: 24/3/2015
 *      Author: matias
 */

#include "Logger.h"

Logger* Logger::logInstancia = NULL;

Logger::Logger(){

	ofstream* arch = new ofstream;
	arch->open(archivoLog, ofstream::trunc);
	archivo = arch;
	this->nivel = nivelLogger;
	ponerFecha();
}

void Logger::escribir(string texto){
	this->archivo->write(texto.c_str(), strlen(texto.c_str()));
	this->archivo->write("\n", strlen("\n"));

}

void Logger::guardarEstado(){

	this->archivo->close();
	this->archivo->open(archivoLog, ofstream::app);

}

void Logger::ponerFecha(){
	time_t fecha;
	time ( &fecha);
	string sfecha = ctime (&fecha);

	string guion = "-----------------------------------------------------------------";
	escribir(guion);
	sfecha.append(guion);
	escribir(sfecha);
}

void Logger::modificarNivel(string nivel){
	this->setNivel(nivel);
}

void Logger::setNivel(string nivel){
	this->nivel = nivel;
}

string Logger::getNivel(){
	return this->nivel;
}

void Logger::error(string texto){

	this->escribir(texto);

}

void Logger::warn(string texto){

	string nivel = StringUtil::toLower(this->getNivel());
	if ((nivel == Warn) || (nivel == Info)  || (nivel == Debug))
		this->escribir(texto);

}

void Logger::info(string texto){

	string nivel = StringUtil::toLower(this->getNivel());
	if ((nivel == Info)  || (nivel == Debug))
		this->escribir(texto);

}

void Logger::debug(string texto){

	string nivel = StringUtil::toLower(this->getNivel());
	if ((nivel == Debug))
		this->escribir(texto);

}

Logger::~Logger(){

	this->archivo->close();
	delete this->archivo;
}


Logger* Logger::getLogger(){

	if(logInstancia == NULL) logInstancia = new Logger();

	return logInstancia;
}
