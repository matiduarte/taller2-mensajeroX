/*
 * TestUsuario.h
 *
 *  Created on: 30/3/2015
 *      Author: matias
 */

#ifndef TEST_TESTUSUARIO_H_
#define TEST_TESTUSUARIO_H_

#include "../src/Entidades/Usuario.h"
#include <iostream>
#include <string>
#include <list>
#include <cppunit/TestCase.h>
#include <cppunit/TestFixture.h>
#include <cppunit/ui/text/TextTestRunner.h>
#include <cppunit/extensions/HelperMacros.h>
#include <cppunit/extensions/TestFactoryRegistry.h>
#include <cppunit/TestResult.h>
#include <cppunit/TestResultCollector.h>
#include <cppunit/TestRunner.h>
#include <cppunit/BriefTestProgressListener.h>
#include <cppunit/CompilerOutputter.h>
#include <cppunit/XmlOutputter.h>
#include <netinet/in.h>

using namespace CppUnit;
using namespace std;

class TestUsuario: public CppUnit::TestCase {
	CPPUNIT_TEST_SUITE(TestUsuario);
	CPPUNIT_TEST(testAddition);
	CPPUNIT_TEST(testMultiply);
	CPPUNIT_TEST_SUITE_END();
public:
	TestUsuario();
	virtual ~TestUsuario();
    void setUp(void);
    void tearDown(void);
    void runTest();
    void testAddition(void);
    void testMultiply(void);
};


#endif /* TEST_TESTUSUARIO_H_ */
