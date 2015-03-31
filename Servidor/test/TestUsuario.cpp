/*
 * TestUsuario.cpp
 *
 *  Created on: 30/3/2015
 *      Author: matias
 */

#include "TestUsuario.h"

TestUsuario::TestUsuario() {
	// TODO Auto-generated constructor stub

}

TestUsuario::~TestUsuario() {
	// TODO Auto-generated destructor stub
}

void TestUsuario::testAddition(void)
{
    CPPUNIT_ASSERT(5 == 54);
}

void TestUsuario::testMultiply(void)
{
    CPPUNIT_ASSERT(6 == 6);
}

void TestUsuario::setUp(void)
{
    //mTestObj = new CBasicMath();
}

void TestUsuario::tearDown(void)
{
    //delete mTestObj;
}

void TestUsuario::runTest(){
	CPPUNIT_ASSERT(6 == 3);
}
