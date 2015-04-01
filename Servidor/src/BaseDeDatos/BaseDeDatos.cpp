/*
 * BaseDeDatos.cpp
 *
 *  Created on: 24/3/2015
 *      Author: matias
 */

#include "BaseDeDatos.h"

BaseDeDatos* BaseDeDatos::pBaseDeDatos = NULL;

/**
 * Abre o crea si no esta creada la base de datos.
 */
BaseDeDatos::BaseDeDatos(string path) {
	claveBaseUsuario = "usuario_";
	claveBaseConversacion = "conversacion_";
	pathBaseDeDatos = path;
	options.IncreaseParallelism();
	options.OptimizeLevelStyleCompaction();
	options.create_if_missing = true;
	//TODO: decidir que hacer cuando hay problemas para abrir la base de datos.
	estado = DB::Open(options, pathBaseDeDatos, &db);
	assert(estado.ok());

}

BaseDeDatos* BaseDeDatos::getInstance() {
    if(pBaseDeDatos == NULL)
    {
    	pBaseDeDatos = new BaseDeDatos(path_BaseDeDatos);
        atexit(&destruirBaseDeDatos);    // At exit, destroy the singleton
    }
    return pBaseDeDatos;
}

/**
 * Se encarga de liberar la memoria. Es el destructor del singleton.
 */
void BaseDeDatos::destruirBaseDeDatos(){
	if(pBaseDeDatos != NULL){
		delete pBaseDeDatos->db;
		delete pBaseDeDatos;
	}
}

BaseDeDatos::~BaseDeDatos() {}


void BaseDeDatos::setDato(string clave, string valor) {
	estado = db->Put(WriteOptions(), clave, valor);
	//TODO: decidir que hacer cuando hay problemas para guardar datos.
	assert(estado.ok());
}


string BaseDeDatos::getDato(string clave) {
	string valor;
	estado = db->Get(ReadOptions(), clave, &valor);
	//TODO: decidir que hacer cuando hay problemas para obtener datos.
	assert(estado.ok());
	return valor;
}


void BaseDeDatos::setUsuario(Usuario* usuario) {
	setDato(claveBaseUsuario+usuario->getId(), usuario->serializar());
}


Usuario* BaseDeDatos::getUsuario(string clave) {

	Usuario *usuario = new Usuario(getDato(claveBaseUsuario+clave));
	return usuario;
}


void BaseDeDatos::setConversacion(Conversacion* conversacion) {
	setDato( claveBaseConversacion+conversacion->getId(), conversacion->serializar() );
}


Conversacion* BaseDeDatos::getConversacion(string clave) {

	Conversacion *conversacion = new Conversacion(claveBaseConversacion+getDato(clave));
	return conversacion;
}
