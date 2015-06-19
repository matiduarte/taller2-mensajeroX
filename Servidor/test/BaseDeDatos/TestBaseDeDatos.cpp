/*
 * BaseDeDatosTests.cpp
 *
 *  Created on: 31/3/2015
 *      Author: juanma
 */

#include "TestBaseDeDatos.h"

CPPUNIT_TEST_SUITE_REGISTRATION(TestBaseDeDatos);

TestBaseDeDatos::TestBaseDeDatos() {
	BaseDeDatos::setPath(path_BaseDeDatosTests);
	this->baseDeDatos = BaseDeDatos::getInstance();
}

TestBaseDeDatos::~TestBaseDeDatos() {
}

void TestBaseDeDatos::guardarUnDato() {

	string valor = "un dato cuaquiera";
	string clave = "1";
	baseDeDatos->setDato(clave, valor);
	CPPUNIT_ASSERT(valor == baseDeDatos->getDato(clave));

}



void TestBaseDeDatos::obtenerDatoInvalido() {

	string clave = "clave inexistente";
	string valor = baseDeDatos->getDato(clave);
	CPPUNIT_ASSERT(valor == keyDatoNoEncontrado);
}

void TestBaseDeDatos::guardarUsuario() {

	Usuario *unUsuario = new Usuario("Juan", "foto", "1234");
	baseDeDatos->setUsuario(unUsuario);

	Usuario *pUsuario;
	pUsuario = baseDeDatos->getUsuario(unUsuario->getId());

	CPPUNIT_ASSERT(unUsuario->getNombre() == pUsuario->getNombre());
	CPPUNIT_ASSERT(unUsuario->getFotoDePerfil() == pUsuario->getFotoDePerfil());
	CPPUNIT_ASSERT(unUsuario->getId() == pUsuario->getId());

	baseDeDatos->eliminarUsuario(unUsuario->getId());

	delete unUsuario;
	delete pUsuario;

}



void TestBaseDeDatos::eliminarUsuario() {
	Usuario *unUsuario = new Usuario("Juan", "foto", "1234");
	baseDeDatos->setUsuario(unUsuario);

	baseDeDatos->eliminarUsuario(unUsuario->getId());

	unUsuario = baseDeDatos->getUsuario(unUsuario->getId());

	CPPUNIT_ASSERT(keyIdUsuarioNoEncontrado == unUsuario->getId());
}

void TestBaseDeDatos::obtenerConversacionInexistente() {

	Conversacion* pConversacion = baseDeDatos->getConversacion("clave inexistente.");
	CPPUNIT_ASSERT(pConversacion->getId() == keyIdConversacionNoEncontrada);

	delete pConversacion;
}