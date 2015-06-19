/*
 * TestStringUtil.cpp
 *
 *  Created on: 10/4/2015
 *      Author: matias
 */

#include "TestStringUtil.h"

CPPUNIT_TEST_SUITE_REGISTRATION(TestStringUtil);

TestStringUtil::TestStringUtil() {
	// TODO Auto-generated constructor stub

}

void TestStringUtil::testBooleanToString() {
	CPPUNIT_ASSERT("true" == StringUtil::toString(true));
	CPPUNIT_ASSERT("false" == StringUtil::toString(false));
}

TestStringUtil::~TestStringUtil() {
	// TODO Auto-generated destructor stub
}

void TestStringUtil::testToLowerDeberiaPasarTodoAMinuscula(){


	string enMayuscula = "USUARIO";
	string enMinuscula = StringUtil::toLower(enMayuscula);

	CPPUNIT_ASSERT( "usuario" == enMinuscula );
}

void TestStringUtil::testToUpperDeberiaPasarTodoAMayuscula(){


	string enMinuscula = "usuario";
	string enMayuscula = StringUtil::toUpper(enMinuscula);

	CPPUNIT_ASSERT( "USUARIO" == enMayuscula );
}

void TestStringUtil::testStringToBoolean(){

	CPPUNIT_ASSERT_EQUAL(StringUtil::toBoolean("true") , true);
	CPPUNIT_ASSERT_EQUAL(StringUtil::toBoolean("false") , false);
}
