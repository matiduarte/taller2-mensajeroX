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
#include "../Entidades/Conversacion.h"


using namespace rocksdb;

class Conversacion;
class Usuario;

class BaseDeDatos {
private:
	static BaseDeDatos* pBaseDeDatos;
	static void destruirBaseDeDatos();
	BaseDeDatos();

	DB* db;
	Options options;
	string claveBaseUsuario;
	string claveBaseConversacion;
	Status estado;
	void setDato(string clave, string valor);
	string getDato(string clave);

public:
	void setUsuario(Usuario* usuario);
	Usuario* getUsuario(string clave);
	void setConversacion(Conversacion* conversacion);
	Conversacion* getConversacion(string clave);
	static BaseDeDatos* getInstance();
	virtual ~BaseDeDatos();
	void setPersistible(Persistible *usuario);
	string getPersistible(string clave);

};

#endif /* SRC_BASEDEDATOS_BASEDEDATOS_H_ */
