/*
 * TestStringUtil.h
 *
 *  Created on: 10/4/2015
 *      Author: matias
 */

#ifndef TEST_STRINGUTIL_TESTSTRINGUTIL_H_
#define TEST_STRINGUTIL_TESTSTRINGUTIL_H_

#include "../src/Utilidades/StringUtil.h"
#include <cppunit/extensions/HelperMacros.h>

using namespace CPPUNIT_NS;
using namespace std;

class TestStringUtil: public TestFixture {
	CPPUNIT_TEST_SUITE(TestStringUtil);
	CPPUNIT_TEST(testToLowerDeberiaPasarTodoAMinuscula);
	CPPUNIT_TEST(testToUpperDeberiaPasarTodoAMayuscula);
	CPPUNIT_TEST(testToBooleanDeberiaConvertirLaCadenaEnUnBool);
	CPPUNIT_TEST_SUITE_END();
public:
	TestStringUtil();
    void testToLowerDeberiaPasarTodoAMinuscula();
    void testToUpperDeberiaPasarTodoAMayuscula();
    void testToBooleanDeberiaConvertirLaCadenaEnUnBool();
	virtual ~TestStringUtil();
};

#endif /* TEST_STRINGUTIL_TESTSTRINGUTIL_H_ */
