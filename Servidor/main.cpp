#include <stdio.h>
#include <string.h>
#include "src/Log/Logger.h"
#include "src/Servidor/Servidor.h"

#include "src/BaseDeDatos/BaseDeDatos.h"

#include "src/Entidades/Usuario.h"
#include <iostream>
#include <cppunit/TestSuite.h>
#include <cppunit/ui/text/TestRunner.h>
#include "test/TestUsuario.h"

using namespace std;
using namespace CppUnit;

/*static int ev_handler(struct mg_connection *conn, enum mg_event ev) {
  switch (ev) {
    case MG_AUTH: return MG_TRUE;
    case MG_REQUEST:
      mg_printf_data(conn, "Hello! Requested URI is [%s]", conn->uri);
      return MG_TRUE;
    default: return MG_FALSE;
  }
}*/

int main(void) {

//	Logger* logger = Logger::getLogger();
//	logger->guardarEstado();
//	logger->error("ESTO ES UN ERROR");
//	logger->warn("ESTO ES UN WARNING");
//	logger->info("ESTO ES INFO");
//	logger->debug("ESTO ES UN DEBUG");
//
	/*Servidor *servidor = new Servidor();
	servidor->iniciar("8081");*/

	CppUnit::TextUi::TestRunner runner;
	TestUsuario* usr_test = new TestUsuario();
	runner.addTest(usr_test);
	runner.run();


//
//	delete logger;
//	delete servidor;
//
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

//	BaseDeDatos *baseDeDatos = new BaseDeDatos();
//	Usuario* user = new Usuario("Pepe", "foto", "1568017070");
//
//	baseDeDatos->setPersistible(user);
//	Usuario* user2 = new Usuario();
//	user2->deserealizar(baseDeDatos->getPersistible(user->getId()));
//
//	cout << "el usuario es: " << user2->getNombre() <<endl;
//
//	delete user;
//	delete user2;

  return 0;
}
