/*
 * TestUsuario.cpp
 *
 *  Created on: 30/3/2015
 *      Author: matias
 */

#include "TestConversacion.h"

CPPUNIT_TEST_SUITE_REGISTRATION(TestConversacion);

TestConversacion::TestConversacion() {
	// TODO Auto-generated constructor stub

}

TestConversacion::~TestConversacion() {
	// TODO Auto-generated destructor stub
}

void TestConversacion::testSerializacionDeDatosUnaConversacion(){
	Usuario* user = new Usuario("Pepe", "foto", "1568017070");
	Usuario* user2 = new Usuario("Jose", "foto2", "156801515");

	BaseDeDatos* db = BaseDeDatos::getInstance();
	db->setUsuario(user);
	db->setUsuario(user2);

	vector<Usuario*> usuarios;
	usuarios.push_back(user);
	usuarios.push_back(user2);

	vector<string> mensajes;
	mensajes.push_back("mensaje 1");
	mensajes.push_back("mensaje 2");

	Conversacion* conversacion = new Conversacion(usuarios, mensajes);
	string conversacionSerializada = conversacion->serializar();

	Conversacion* conv2 = new Conversacion(conversacionSerializada);
	vector<string> mensajesDeserealizados = conv2->getMensajes();

	vector<Usuario*> usuariosDeserealizados = conv2->getUsuarios();

	CPPUNIT_ASSERT(mensajes[0] == mensajesDeserealizados[0]);
	CPPUNIT_ASSERT(mensajes[1] == mensajesDeserealizados[1]);

	CPPUNIT_ASSERT(user->getNombre() == usuariosDeserealizados[0]->getNombre());
	CPPUNIT_ASSERT(user->getId() == usuariosDeserealizados[0]->getId());

	CPPUNIT_ASSERT(user2->getNombre() == usuariosDeserealizados[1]->getNombre());
	CPPUNIT_ASSERT(user2->getId() == usuariosDeserealizados[1]->getId());


	delete user;
	delete user2;
	delete conversacion;
	delete conv2;


}
