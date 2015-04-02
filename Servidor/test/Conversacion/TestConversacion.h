/*
 * TestUsuario.h
 *
 *  Created on: 30/3/2015
 *      Author: matias
 */

#ifndef TEST_TESTCONVERSACION_H_
#define TEST_TESTCONVERSACION_H_

#include "../src/Entidades/Conversacion.h"
#include <cppunit/extensions/HelperMacros.h>

using namespace CPPUNIT_NS;
using namespace std;

class TestConversacion: public TestFixture {
	CPPUNIT_TEST_SUITE(TestConversacion);
	CPPUNIT_TEST(testSerializacionDeDatosUnaConversacion);

	CPPUNIT_TEST_SUITE_END();
public:
	TestConversacion();
	virtual ~TestConversacion();
    void testSerializacionDeDatosUnaConversacion();

};


#endif /* TEST_TESTUSUARIO_H_ */
