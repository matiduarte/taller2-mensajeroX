/*
 * BaseDeDatos.cpp
 *
 *  Created on: 24/3/2015
 *      Author: matias
 */

#include "BaseDeDatos.h"

BaseDeDatos::BaseDeDatos() {
	options.IncreaseParallelism();
	options.OptimizeLevelStyleCompaction();
	options.create_if_missing = true;
	//TODO: decidir que hacer cuando hay problemas para abrir la base de datos.
	estado = DB::Open(options, path_BaseDeDatos, &db);
	assert(estado.ok());

}

BaseDeDatos::~BaseDeDatos() {
	delete db;
}


void BaseDeDatos::setPersistible(Persistible* dato) {
	estado = db->Put(WriteOptions(), dato->getId(), dato->serializar());
	//TODO: decidir que hacer cuando hay problemas para guardar datos.
	assert(estado.ok());
}


string BaseDeDatos::getPersistible(string clave) {
	string valor;
	estado = db->Get(ReadOptions(), clave, &valor);
	//TODO: decidir que hacer cuando hay problemas para obtener datos.
	assert(estado.ok());

	return valor;
}


