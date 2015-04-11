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

void TestStringUtil::testToBooleanDeberiaConvertirLaCadenaEnUnBool(){

	string conectado = "1";
	string desconectado = "0";

	CPPUNIT_ASSERT(StringUtil::toBoolean(conectado) == Online);
	CPPUNIT_ASSERT(StringUtil::toBoolean(desconectado) == Offline);
}
