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
 * Abre la base de datos.
 * En caso que no exista, la crea.
 */
BaseDeDatos::BaseDeDatos() {
	claveBaseUsuario = "usuario_";
	claveBaseConversacion = "conversacion_";
	claveBaseConversacionesPorUsuario = "conversaciones_por_usuario_";
	options.IncreaseParallelism();
	options.OptimizeLevelStyleCompaction();
	options.create_if_missing = true;
	estado = DB::Open(options, pathBaseDeDatos, &db);
	if (!estado.ok()){
			Loger::getLoger()->error("No se pudo abrir la Base De Datos con path: "+BaseDeDatos::pathBaseDeDatos);
			Loger::getLoger()->guardarEstado();
			cerr << "No se pudo abrir la Base de Datos." << endl;
		}
}

BaseDeDatos* BaseDeDatos::getInstance() {
	if (pBaseDeDatos == NULL) {
		pBaseDeDatos = new BaseDeDatos();
		atexit(&destruirBaseDeDatos);
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

/**
 * Almacena un dato en la base de datos.
 * @param clave es la clave del dato a almacenar.
 * @param valor dato a ser guardado.
 */
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

	return valor;
}


void BaseDeDatos::setUsuario(Usuario* usuario) {
	setDato(claveBaseUsuario+usuario->getId(), usuario->serializar());
}

/**
 * Devuelve el usuario con clave igual a la pasada por parámetro.
 * Si no lo encuentra, devuelve un usuario por defecto.
 */
Usuario* BaseDeDatos::getUsuario(string clave) {

	string valor = getDato(claveBaseUsuario + clave);
	if (valor == keyDatoNoEncontrado) {
		Loger::getLoger()->warn(
				"El usuario con clave: " + clave
						+ " no se encuentra en la base de datos.");
		Loger::getLoger()->guardarEstado();
		return new Usuario();
	} else {
		return new Usuario(valor);
	}
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

vector<string> BaseDeDatos::getIdsConversacionPorIdUsuario(string claveUsuario) {
	string valor = getDato(claveBaseConversacionesPorUsuario + claveUsuario);
	return StringUtil::split(valor, SeparadorListaBD);
}

/**
 * Devuelve la conversación almacenada con la clave que recibe
 * por parámetro. Si no la encuentra devuelve una conversación
 * por defecto.
 */
Conversacion* BaseDeDatos::getConversacion(string clave) {

	string valor = getDato(claveBaseConversacion + clave);
	if (valor == keyDatoNoEncontrado) {
		Loger::getLoger()->warn(
				"La conversación con clave: " + clave
						+ " no se encuentra en la base de datos.");
		Loger::getLoger()->guardarEstado();
		return new Conversacion();
	} else {
		return new Conversacion(valor);
	}

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
