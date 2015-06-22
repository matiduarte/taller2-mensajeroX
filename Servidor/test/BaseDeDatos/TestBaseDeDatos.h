/*
 * BaseDeDatosTests.h
 *
 *  Created on: 31/3/2015
 *      Author: juanma
 */

#ifndef TEST_BASEDEDATOS_TESTBASEDEDATOS_H_
#define TEST_BASEDEDATOS_TESTBASEDEDATOS_H_

#include <cppunit/extensions/HelperMacros.h>
#include "../../src/BaseDeDatos/BaseDeDatos.h"
#include "../../src/Entidades/Usuario.h"

using namespace CPPUNIT_NS;
using namespace std;

class TestBaseDeDatos: public TestFixture {

	CPPUNIT_TEST_SUITE(TestBaseDeDatos);
	CPPUNIT_TEST(guardarUnDato);
	CPPUNIT_TEST(obtenerDatoInvalido);
	CPPUNIT_TEST(guardarUsuario);
	CPPUNIT_TEST(eliminarUsuario);
	CPPUNIT_TEST(obtenerConversacionInexistente);
	CPPUNIT_TEST(guardarConversacion);
	CPPUNIT_TEST(eliminarConversacion);
	CPPUNIT_TEST_SUITE_END();

	BaseDeDatos *baseDeDatos;
public:
	TestBaseDeDatos();
	virtual ~TestBaseDeDatos();
	void guardarUnDato();
	void obtenerDatoInvalido();
	void guardarUsuario();
	void eliminarUsuario();
	void obtenerConversacionInexistente();
	void guardarConversacion();
	void eliminarConversacion();
};

#endif /* TEST_BASEDEDATOS_TESTBASEDEDATOS_H_ */
