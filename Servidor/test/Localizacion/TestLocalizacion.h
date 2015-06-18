/*
 * TestLocalizacion.h
 *
 *  Created on: 17/6/2015
 *      Author: juanma
 */

#ifndef TEST_TESTLOCALIZACION_H_
#define TEST_TESTLOCALIZACION_H_

#include <cppunit/extensions/HelperMacros.h>

using namespace CPPUNIT_NS;
using namespace std;

class TestLocalizacion: public TestFixture {
	CPPUNIT_TEST_SUITE(TestLocalizacion);
	CPPUNIT_TEST(testCargarArchivoInvalido);
	CPPUNIT_TEST(testCargarArchivoValido);
	CPPUNIT_TEST(testCalcularDistancia);
	CPPUNIT_TEST_SUITE_END();

public:
	TestLocalizacion();
	virtual ~TestLocalizacion();
	void testCargarArchivoInvalido();
	void testCargarArchivoValido();
	void testCalcularDistancia();

};

#endif /* TEST_TESTLOCALIZACION_H_ */
