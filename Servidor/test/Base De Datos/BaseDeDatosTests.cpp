/*
 * BaseDeDatosTests.cpp
 *
 *  Created on: 31/3/2015
 *      Author: juanma
 */

#include "BaseDeDatosTests.h"

CPPUNIT_TEST_SUITE_REGISTRATION(BaseDeDatosTests);

void BaseDeDatosTests::guardarUnDato(){

	BaseDeDatos *baseDeDatos = new BaseDeDatos("Base De Datos TEST");
	string valor = "un dato cuaquiera";
	string clave = "1";
	baseDeDatos->setDato(clave,valor);
	CPPUNIT_ASSERT(valor == baseDeDatos->getDato(clave) );

	//elimino los cambios
    WriteBatch batch;
    batch.Delete(clave);
    baseDeDatos->db->Write(WriteOptions(), &batch);

	delete baseDeDatos;
}
