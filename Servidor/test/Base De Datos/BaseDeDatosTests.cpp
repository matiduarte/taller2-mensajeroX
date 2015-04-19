/*
 * BaseDeDatosTests.cpp
 *
 *  Created on: 31/3/2015
 *      Author: juanma
 */

#include "BaseDeDatosTests.h"

CPPUNIT_TEST_SUITE_REGISTRATION(BaseDeDatosTests);


void BaseDeDatosTests::guardarUnDato(){

	string valor = "un dato cuaquiera";
	string clave = "1";
	baseDeDatos->setDato(clave,valor);
	CPPUNIT_ASSERT(valor == baseDeDatos->getDato(clave) );

	//elimino los cambios
    WriteBatch batch;
    batch.Delete(clave);
    baseDeDatos->db->Write(WriteOptions(), &batch);

}

BaseDeDatosTests::BaseDeDatosTests() {
	this->baseDeDatos = BaseDeDatos::getInstance();
}



void BaseDeDatosTests::getDatoInvalido(){

	string clave = "clave inexistente";
	string valor = baseDeDatos->getDato(clave);
	CPPUNIT_ASSERT(valor == keyDatoNoEncontrado );
}

