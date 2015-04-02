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
	if (!estado.ok()){
		Loger::getLoger()->error("ERROR: No se pudo abrir la Base De Datos con path: "+path);
		Loger::getLoger()->guardarEstado();
		cerr << "No se pudo abrir la Base de Datos." << endl;
	}
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
	if (!estado.ok()){
		Loger::getLoger()->error("ERROR: No se pudo guardar el objeto con clave: "+clave);
		Loger::getLoger()->guardarEstado();
		cerr << "No se pudo guardar el objeto." << endl;
	}
}


string BaseDeDatos::getDato(string clave) {
	string valor;
	estado = db->Get(ReadOptions(), clave, &valor);
	//TODO: decidir que hacer cuando hay problemas para obtener datos.
	if (!estado.ok()){
		Loger::getLoger()->error("ERROR: No se encontró el objeto con clave: "+clave);
		Loger::getLoger()->guardarEstado();
		cerr << "No se encontró el objeto." << endl;
	}
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

void BaseDeDatos::eliminar(string clave) {
	estado = db->Delete(rocksdb::WriteOptions(), clave);
	//TODO: decidir que hacer cuando hay problemas para eliminar datos.
	if (!estado.ok()){
		Loger::getLoger()->error("ERROR: No se pudo eliminar el objeto con clave: "+clave);
		Loger::getLoger()->guardarEstado();
		cerr << "No se pudo eliminar el objeto." << endl;
	}
}

void BaseDeDatos::eliminarUsuario(string clave) {
	eliminar(claveBaseUsuario+clave);
}

void BaseDeDatos::eliminarConversacion(string clave) {
	eliminar(claveBaseConversacion+clave);
}
