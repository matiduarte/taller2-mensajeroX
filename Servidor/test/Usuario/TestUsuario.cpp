/*
 * TestUsuario.cpp
 *
 *  Created on: 30/3/2015
 *      Author: matias
 */

#include "TestUsuario.h"

CPPUNIT_TEST_SUITE_REGISTRATION(TestUsuario);

TestUsuario::TestUsuario() {
	BaseDeDatos::setPath(path_BaseDeDatosTests);
}

TestUsuario::~TestUsuario() {
}

void TestUsuario::testSerializacionDeDatosDeUnUsuario(){
	Usuario* unUsuario = new Usuario("Pedro" , "123456789" , "contraseña");
	string usuarioSerializado = unUsuario->serializar();

	Json::Value usuario;
	Json::Reader reader;
	reader.parse(usuarioSerializado,usuario);

	CPPUNIT_ASSERT(usuario.get(keyNombre,"").asString() == "Pedro");
	CPPUNIT_ASSERT(usuario.get(keyPassword,"").asString() == "contraseña");
	CPPUNIT_ASSERT(usuario.get(keyTelefono,"").asString() == "123456789");

	delete unUsuario;
}

void TestUsuario::testModificacionDeDatosDelUsuario(){

	Usuario* unUsuario = new Usuario("Pedro" ,"foto", "123456789" , "contraseña");
	string nuevoNombre = "Roberto";
	string nuevaFoto = "OtraFoto";
	unUsuario->setNombre(nuevoNombre);
	unUsuario->setFotoDePerfil(nuevaFoto);
	unUsuario->persistir();

	CPPUNIT_ASSERT(Usuario::obtener(unUsuario->getId())->getNombre() == nuevoNombre );
	CPPUNIT_ASSERT(Usuario::obtener(unUsuario->getId())->getFotoDePerfil() == nuevaFoto);

	delete unUsuario;
}

void TestUsuario::testEliminarUsuario(){

	Usuario* unUsuario = new Usuario("Pedro" ,"foto", "123456789" , "contraseña");
	unUsuario->persistir();
	CPPUNIT_ASSERT(Usuario::obtener(unUsuario->getId())->getId() != keyIdUsuarioNoEncontrado );

	Usuario::eliminar(unUsuario->getId());
	CPPUNIT_ASSERT(Usuario::obtenerPorTelefono(unUsuario->getTelefono())->getId() == keyIdUsuarioNoEncontrado );

	delete unUsuario;
}
