/*
 * TestLocalizacion.cpp
 *
 *  Created on: 17/6/2015
 *      Author: juanma
 */

#include "../Localizacion/TestLocalizacion.h"

CPPUNIT_TEST_SUITE_REGISTRATION(TestLocalizacion);

TestLocalizacion::TestLocalizacion() {
}

TestLocalizacion::~TestLocalizacion() {
}

void TestLocalizacion::testCargarArchivoInvalido() {
	CPPUNIT_ASSERT_THROW( Localizacion::cargarLugares(" "), runtime_error );
}

void TestLocalizacion::testCargarArchivoValido() {

	CPPUNIT_ASSERT_NO_THROW(Localizacion::cargarLugares("Localizacion/LugaresTest.txt"));
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

void TestLocalizacion::testCalcularUbicacion() {
	Localizacion::cargarLugares("Localizacion/LugaresTest.txt");

	Json::Value coordenadasFIUBA;
	coordenadasFIUBA["latitud"] = -34.617630;
	coordenadasFIUBA["longitud"] = -58.368254;

	CPPUNIT_ASSERT(Localizacion::calcularUbicacion(coordenadasFIUBA) == "Facultad de Ingeniería");
}

void TestLocalizacion::testCalcularUbicacionSinCargarLugares() {
	Localizacion::lugares.clear();
	Json::Value coordenadas;
	coordenadas["latitud"] = -34.617630;
	coordenadas["longitud"] = -58.368254;

	CPPUNIT_ASSERT(Localizacion::calcularUbicacion(coordenadas) == "desconocido");
}
