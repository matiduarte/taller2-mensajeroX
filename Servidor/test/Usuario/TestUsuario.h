/*
 * TestUsuario.h
 *
 *  Created on: 30/3/2015
 *      Author: matias
 */

#ifndef TEST_TESTUSUARIO_H_
#define TEST_TESTUSUARIO_H_

#include "../src/Entidades/Usuario.h"
#include <cppunit/extensions/HelperMacros.h>

using namespace CPPUNIT_NS;
using namespace std;

class TestUsuario: public TestFixture {
	CPPUNIT_TEST_SUITE(TestUsuario);
	CPPUNIT_TEST(testSerializacionDeDatosDeUnUsuario);

	CPPUNIT_TEST_SUITE_END();
public:
	TestUsuario();
	virtual ~TestUsuario();
    void testSerializacionDeDatosDeUnUsuario();

};


#endif /* TEST_TESTUSUARIO_H_ */
