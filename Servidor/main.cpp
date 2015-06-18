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
#include <cppunit/ui/text/TestRunner.h>

#include "src/Log/Loger.h"
#include "src/Utilidades/Localizacion.h"

using namespace std;

int main(void) {
	BaseDeDatos::setPath(path_BaseDeDatosTests);
	Servidor *servidor = new Servidor();
	char* puerto = StringUtil::str2Char(puertoDefault);
	servidor->iniciar(puerto);
	delete puerto;

  return 0;
}
