/*
 * TestUsuario.h
 *
 *  Created on: 30/3/2015
 *      Author: matias
 */

#ifndef TEST_TESTLOG_H_
#define TEST_TESTLOG_H_

#include "../src/Log/Loger.h"
#include <cppunit/extensions/HelperMacros.h>

using namespace CPPUNIT_NS;
using namespace std;

class TestLoger: public TestFixture {
	CPPUNIT_TEST_SUITE(TestLoger);
	CPPUNIT_TEST(testLogError);
	CPPUNIT_TEST(testLogDebug);
	CPPUNIT_TEST(testLogInfo);
	CPPUNIT_TEST(testLogWarning);
	CPPUNIT_TEST_SUITE_END();
public:
	TestLoger();
	virtual ~TestLoger();
    void testLogError();
    void testLogDebug();
    void testLogInfo();
    void testLogWarning();
};


#endif
