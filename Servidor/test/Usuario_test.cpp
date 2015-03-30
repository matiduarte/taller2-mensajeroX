/*
 * Usuario_test.cpp
 *
 *  Created on: 24/3/2015
 *      Author: matias
 */

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

//-----------------------------------------------------------------------------

class Usuario_test : public CppUnit::TestFixture
{
    CPPUNIT_TEST_SUITE(Usuario_test);
    CPPUNIT_TEST(testAddition);
    CPPUNIT_TEST(testMultiply);
    CPPUNIT_TEST_SUITE_END();

public:
    void setUp(void);
    void tearDown(void);

protected:
    void testAddition(void);
    void testMultiply(void);
};

void Usuario_test::testAddition(void)
{
    CPPUNIT_ASSERT(5 == 5);
}

void Usuario_test::testMultiply(void)
{
    CPPUNIT_ASSERT(6 == 6);
}

void Usuario_test::setUp(void)
{
    //mTestObj = new CBasicMath();
}

void Usuario_test::tearDown(void)
{
    //delete mTestObj;
}

