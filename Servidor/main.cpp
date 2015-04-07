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

int main(void) {

//	BaseDeDatos *baseDeDatos = BaseDeDatos::getInstance();
//	Usuario* user = new Usuario("Pepe", "foto", "1122");
//	user->setEstadoConexion(false);
//	baseDeDatos->setUsuario(user);
//	delete user;
//	Servidor *servidor = new Servidor();
//	servidor->iniciar("8081");
//	delete servidor;


//	Usuario* user = new Usuario("Pepe", "foto", "1568017070");
//	Usuario* user2 = new Usuario("Jose", "foto2", "156801515");
//	string a = user->serializar();
//	user->deserealizar(a);
//
//	vector<Usuario*> usuarios;
//	usuarios.push_back(user);
//	usuarios.push_back(user2);
//
//	vector<string> mensajes;
//	mensajes.push_back("mensaje 1");
//	mensajes.push_back("mensaje 2");
//
//	Conversacion* conversacion = new Conversacion(usuarios, mensajes);
//	string conversacionSerializada = conversacion->serializar();
//	Conversacion* conversacion2 = new Conversacion();
//	conversacion->deserealizar(conversacionSerializada);


	CPPUNIT_NS::TestResult controller;
	CPPUNIT_NS::TestResultCollector result;

	controller.addListener(&result);
	CPPUNIT_NS::BriefTestProgressListener progress;

	controller.addListener(&progress);
	CPPUNIT_NS::TestRunner runner;

	runner.addTest( CPPUNIT_NS::TestFactoryRegistry::getRegistry().makeTest());
	runner.run(controller);

	CPPUNIT_NS::CompilerOutputter outputter(&result, CPPUNIT_NS::stdCOut());
	outputter.write();


  return 0;
}
