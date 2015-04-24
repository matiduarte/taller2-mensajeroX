#include <stdio.h>
#include <string.h>
#include "src/Servidor/Servidor.h"

#include "src/BaseDeDatos/BaseDeDatos.h"

#include "src/Entidades/Usuario.h"
#include <iostream>
#include <cppunit/TestSuite.h>
#include <cppunit/ui/text/TestRunner.h>
#include "test/Usuario/TestUsuario.h"

#include <cppunit/BriefTestProgressListener.h>
#include <cppunit/CompilerOutputter.h>
#include <cppunit/extensions/TestFactoryRegistry.h>
#include <cppunit/TestResult.h>
#include <cppunit/TestResultCollector.h>
#include <cppunit/TestRunner.h>

#include "src/Log/Loger.h"

using namespace std;
using namespace CppUnit;

/*int main(void) {
	BaseDeDatos::setPath(path_BaseDeDatosTests);
	Servidor *servidor = new Servidor();
	servidor->iniciar("8080");

//	CPPUNIT_NS::TestResult controller;
//	CPPUNIT_NS::TestResultCollector result;
//
//	controller.addListener(&result);
//	CPPUNIT_NS::BriefTestProgressListener progress;
//
//	controller.addListener(&progress);
//	CPPUNIT_NS::TestRunner runner;
//
//	runner.addTest( CPPUNIT_NS::TestFactoryRegistry::getRegistry().makeTest());
//	runner.run(controller);
//
//	CPPUNIT_NS::CompilerOutputter outputter(&result, CPPUNIT_NS::stdCOut());
//	outputter.write();


  return 0;
}*/
