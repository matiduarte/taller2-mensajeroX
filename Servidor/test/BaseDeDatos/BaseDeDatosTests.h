/*
 * BaseDeDatosTests.h
 *
 *  Created on: 31/3/2015
 *      Author: juanma
 */

#ifndef TEST_BASEDEDATOS_BASEDEDATOSTESTS_H_
#define TEST_BASEDEDATOS_BASEDEDATOSTESTS_H_

#include <cppunit/extensions/HelperMacros.h>
#include "../../src/BaseDeDatos/BaseDeDatos.h"
#include "../../src/Entidades/Usuario.h"

using namespace CPPUNIT_NS;
using namespace std;

class BaseDeDatosTests: public TestFixture {

	CPPUNIT_TEST_SUITE(BaseDeDatosTests);
	CPPUNIT_TEST(guardarUnDato);
	CPPUNIT_TEST(getDatoInvalido);
	CPPUNIT_TEST(guardarUsuario);

	CPPUNIT_TEST_SUITE_END();
public:
	BaseDeDatosTests();

protected:
	BaseDeDatos *baseDeDatos;
	void guardarUnDato();
	void getDatoInvalido();
	void guardarUsuario();
};

#endif /* TEST_BASEDEDATOS_BASEDEDATOSTESTS_H_ */