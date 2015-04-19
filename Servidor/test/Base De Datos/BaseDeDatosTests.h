/*
 * BaseDeDatosTests.h
 *
 *  Created on: 31/3/2015
 *      Author: juanma
 */

#ifndef TEST_BASE_DE_DATOS_BASEDEDATOSTESTS_H_
#define TEST_BASE_DE_DATOS_BASEDEDATOSTESTS_H_

#include <cppunit/extensions/HelperMacros.h>
#include "../../src/BaseDeDatos/BaseDeDatos.h"

using namespace CPPUNIT_NS;
using namespace std;

class BaseDeDatosTests: public TestFixture {

	CPPUNIT_TEST_SUITE(BaseDeDatosTests);
	CPPUNIT_TEST(guardarUnDato);
	CPPUNIT_TEST(getDatoInvalido);

	CPPUNIT_TEST_SUITE_END();
public:
	BaseDeDatosTests();

protected:
	BaseDeDatos *baseDeDatos;
	void guardarUnDato();
	void getDatoInvalido();
};

#endif /* TEST_BASE_DE_DATOS_BASEDEDATOSTESTS_H_ */
