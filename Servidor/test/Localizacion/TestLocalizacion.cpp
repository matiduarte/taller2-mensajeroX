/*
 * TestLocalizacion.cpp
 *
 *  Created on: 17/6/2015
 *      Author: juanma
 */

#include "../Localizacion/TestLocalizacion.h"
#include "../../src/Utilidades/Localizacion.h"

CPPUNIT_TEST_SUITE_REGISTRATION(TestLocalizacion);

TestLocalizacion::TestLocalizacion() {
}

TestLocalizacion::~TestLocalizacion() {
}

void TestLocalizacion::testCargarArchivoInvalido() {
	CPPUNIT_ASSERT_THROW( Localizacion::cargarLugares(" "), runtime_error );
}

void TestLocalizacion::testCargarArchivoValido() {

	CPPUNIT_ASSERT_NO_THROW(Localizacion::cargarLugares("test/Localizacion/LugaresTest.txt"));
	CPPUNIT_ASSERT(Localizacion::lugares[0]["nombre"] == "Almagro" );
}

void TestLocalizacion::testCalcularDistancia() {
	Json::Value coordenadasA,coordenadasB;
	coordenadasA["latitud"] = 2;
	coordenadasA["longitud"] = 2;
	coordenadasB["latitud"] = 5;
	coordenadasB["longitud"] = 6;
	CPPUNIT_ASSERT(5 == Localizacion::calcularDistancia(coordenadasA,coordenadasB));
}
