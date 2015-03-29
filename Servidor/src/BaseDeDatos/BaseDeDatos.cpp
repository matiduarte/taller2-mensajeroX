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

	estado = DB::Open(options, path_BaseDeDatos, &db);
	assert(estado.ok());

}

BaseDeDatos::~BaseDeDatos() {
	delete db;
}

void BaseDeDatos::setDato(string clave, string dato) {
	estado = db->Put(WriteOptions(), clave, dato);
	assert(estado.ok());

}

string BaseDeDatos::getDato(string clave) {
	string valor;
	estado = db->Get(ReadOptions(), clave, &valor);
	//assert(estado.ok());

	return valor;
}
