/*
 * BaseDeDatos.cpp
 *
 *  Created on: 24/3/2015
 *      Author: matias
 */

#include "BaseDeDatos.h"

BaseDeDatos* BaseDeDatos::pBaseDeDatos = NULL;
string BaseDeDatos::pathBaseDeDatos = path_BaseDeDatos;

/**
 * Abre o crea si no esta creada la base de datos.
 */
BaseDeDatos::BaseDeDatos() {
	claveBaseUsuario = "usuario_";
	claveBaseConversacion = "conversacion_";
	claveBaseConversacionesPorUsuario = "conversaciones_por_usuario_";
	options.IncreaseParallelism();
	options.OptimizeLevelStyleCompaction();
	options.create_if_missing = true;
	//TODO: decidir que hacer cuando hay problemas para abrir la base de datos.
	estado = DB::Open(options, pathBaseDeDatos, &db);
	if (!estado.ok()){
		Loger::getLoger()->error("No se pudo abrir la Base De Datos con path: "+BaseDeDatos::pathBaseDeDatos);
		Loger::getLoger()->guardarEstado();
		cerr << "No se pudo abrir la Base de Datos." << endl;
	}
}

BaseDeDatos* BaseDeDatos::getInstance() {
    if(pBaseDeDatos == NULL)
    {
    	pBaseDeDatos = new BaseDeDatos();
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

void BaseDeDatos::setPath(string path) {
	BaseDeDatos::pathBaseDeDatos = path;
}

BaseDeDatos::~BaseDeDatos() {}


void BaseDeDatos::setDato(string clave, string valor) {
	estado = db->Put(WriteOptions(), clave, valor);
	if (!estado.ok()){
		Loger::getLoger()->warn("No se pudo guardar el objeto con clave: "+clave);
		Loger::getLoger()->guardarEstado();
	}
}


string BaseDeDatos::getDato(string clave) {
	string valor;
	estado = db->Get(ReadOptions(), clave, &valor);

	if (estado.IsNotFound())
		valor = keyDatoNoEncontrado;
	else if (!estado.ok())
		throw new std::runtime_error("No se pudo obtener el dato: "+estado.ToString());

	return valor;
}


void BaseDeDatos::setUsuario(Usuario* usuario) {
	setDato(claveBaseUsuario+usuario->getId(), usuario->serializar());
}


Usuario* BaseDeDatos::getUsuario(string clave) {
	try {
		string valor = getDato(claveBaseUsuario + clave);
		if (valor != keyDatoNoEncontrado)
			return new Usuario(valor);
		else{
			Loger::getLoger()->warn("El usuario con clave: " +clave+ " no se encuentra en la base de datos.");
			Loger::getLoger()->guardarEstado();
		}
	} catch (std::runtime_error &e) {
		Loger::getLoger()->error(string(e.what()));
		Loger::getLoger()->guardarEstado();
	}
	return new Usuario();
}


void BaseDeDatos::setConversacion(Conversacion* conversacion) {
	setDato( claveBaseConversacion+conversacion->getId(), conversacion->serializar() );

	//Guardo conversacion por usuarios
	vector<Usuario*> usuarios = conversacion->getUsuarios();
	for(unsigned i=0; i<usuarios.size(); i++){
		Usuario* usuarioActual = usuarios[i];
		string valor = getDato(claveBaseConversacionesPorUsuario + usuarioActual->getId());
		vector<string> idsConversaciones = StringUtil::split(valor, SeparadorListaBD);
		if(!StringUtil::vectorContiene(idsConversaciones, conversacion->getId())){
			//Si la conversacion actual no esta agregada al indice para el usuario, la agrego
			valor += SeparadorListaBD + conversacion->getId();
		}
		setDato(claveBaseConversacionesPorUsuario + usuarioActual->getId(), valor);
	}

}


Conversacion* BaseDeDatos::getConversacion(string clave) {

	try {
		string valor = getDato(claveBaseConversacion + clave);
		if (valor != keyDatoNoEncontrado)
			return new Conversacion(valor);
		else{
			Loger::getLoger()->warn("La conversación con clave: " +clave+ " no se encuentra en la base de datos.");
			Loger::getLoger()->guardarEstado();
		}
	} catch (std::runtime_error &e) {
		Loger::getLoger()->error(string(e.what()));
		Loger::getLoger()->guardarEstado();
	}
	return new Conversacion();
}


void BaseDeDatos::eliminar(string clave) {
	estado = db->Delete(rocksdb::WriteOptions(), clave);
	if (!estado.ok()){
		Loger::getLoger()->warn(" No se pudo eliminar el objeto con clave: "+clave);
		Loger::getLoger()->guardarEstado();
	}
}

void BaseDeDatos::eliminarUsuario(string clave) {
	eliminar(claveBaseUsuario+clave);
}

void BaseDeDatos::eliminarConversacion(string clave) {
	eliminar(claveBaseConversacion+clave);
}
