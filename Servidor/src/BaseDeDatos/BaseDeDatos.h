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
//#include "../../test/Base De Datos/BaseDeDatosTests.h"

using namespace rocksdb;
//class BaseDeDatosTests;

class Conversacion;
class Usuario;

class BaseDeDatos {
	//friend BaseDeDatosTests;
private:
	static BaseDeDatos* pBaseDeDatos;
	static void destruirBaseDeDatos();
	BaseDeDatos();

	DB* db;
	Options options;
	string claveBaseUsuario;
	string claveBaseConversacion;
	string claveBaseConversacionesPorUsuario;
	static string pathBaseDeDatos;
	Status estado;
	void setDato(string clave, string valor);
	string getDato(string clave);
	void eliminar(string clave);

public:
	static void setPath(string path);
	void setUsuario(Usuario* usuario);
	Usuario* getUsuario(string clave);
	void setConversacion(Conversacion* conversacion);
	Conversacion* getConversacion(string clave);
	vector<string> getIdsConversacionPorIdUsuario(string claveUsuario);
	void eliminarUsuario(string clave);
	void eliminarConversacion(string clave);
	static BaseDeDatos* getInstance();
	virtual ~BaseDeDatos();

};

#endif /* SRC_BASEDEDATOS_BASEDEDATOS_H_ */
