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

void TestStringUtil::testStringToInt(){

	CPPUNIT_ASSERT_EQUAL(StringUtil::str2int("1") , 1);
	CPPUNIT_ASSERT_EQUAL(StringUtil::str2int("15") , 15);
}

void TestStringUtil::testStringToChar(){
	string prueba = "pru";
	char* pruebaConvertida = StringUtil::str2Char(prueba);

	char* resultado = new char[4];
	resultado[0] = 'p';
	resultado[1] = 'r';
	resultado[2] = 'u';
	resultado[3]='\0';

	CPPUNIT_ASSERT_EQUAL(strcmp(pruebaConvertida, resultado), 0);
}

void TestStringUtil::testVectorContiene(){
	vector<string> vector;
	vector.push_back("1");

	bool contiene = StringUtil::vectorContiene(vector,"1");
	bool noContiene = StringUtil::vectorContiene(vector,"2");

	CPPUNIT_ASSERT_EQUAL(contiene, true);
	CPPUNIT_ASSERT_EQUAL(noContiene, false);
}

void TestStringUtil::testJsonValueToVector(){
	Json::Value vectorJson;
	for(unsigned i=0; i < 4; i++){
		vectorJson["valor"][i] = i;
	}

	vector<string> vect = StringUtil::jsonValueToVector(vectorJson["valor"]);
	bool resultado = (vect.at(0) == "0");
	bool resultado2 = (vect.at(1) == "1");
	CPPUNIT_ASSERT_EQUAL(resultado, true);
	CPPUNIT_ASSERT_EQUAL(resultado2, true);
}
