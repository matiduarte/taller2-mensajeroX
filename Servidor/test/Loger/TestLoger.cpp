
#include "TestLoger.h"

CPPUNIT_TEST_SUITE_REGISTRATION(TestLoger);

TestLoger::TestLoger() {
}

TestLoger::~TestLoger() {
}

void TestLoger::testLogError(){
	Loger* loger = Loger::getLoger();
	loger->modificarNivel("Error");
	loger->error("Ocurrio un error");

	loger->destruirLoger();
}

void TestLoger::testLogDebug(){
	Loger* loger = Loger::getLoger();
	loger->modificarNivel("DEBUG");
	loger->debug("Debug..");

	loger->destruirLoger();
}

void TestLoger::testLogInfo(){
	Loger* loger = Loger::getLoger();
	loger->modificarNivel("INFO");
	loger->info("Info..");

	loger->destruirLoger();
}

void TestLoger::testLogWarning(){
	Loger* loger = Loger::getLoger();
	loger->modificarNivel("WARNING");
	loger->warn("Warning..");

	loger->destruirLoger();
}

