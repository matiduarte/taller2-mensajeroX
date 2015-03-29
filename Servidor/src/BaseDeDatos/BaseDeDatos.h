/*
 * BaseDeDatos.h
 *
 *  Created on: 24/3/2015
 *      Author: matias
 */

#ifndef SRC_BASEDEDATOS_BASEDEDATOS_H_
#define SRC_BASEDEDATOS_BASEDEDATOS_H_

#include "../Entidades/Usuario.h"
#include "../Entidades/Conversacion.h"
#include "rocksdb/db.h"
#include "rocksdb/slice.h"
#include "rocksdb/options.h"
#include "../constantes.h"
#include "../Entidades/Usuario.h"

using namespace rocksdb;

class BaseDeDatos {
private:
	DB* db;
	Options options;
	string path_BaseDeDatosDefault;
	string ClaveBaseUsuario;
	string ClaveBaseConversacion;
	Status estado;
public:
	BaseDeDatos();
	virtual ~BaseDeDatos();
	void setUsuario(Usuario *usuario);
	Usuario* getUsuario(string clave);
	void setConversacion(Conversacion *conversacion);
	Conversacion* getConversacion();
	void setDato(string clave, string dato);
	string getDato(string clave);
};

#endif /* SRC_BASEDEDATOS_BASEDEDATOS_H_ */
