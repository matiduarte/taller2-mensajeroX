/*
 * Localizacion.h
 *
 *  Created on: 17/6/2015
 *      Author: juanma
 */

#ifndef SRC_UTILIDADES_LOCALIZACION_H_
#define SRC_UTILIDADES_LOCALIZACION_H_

#include "../json/json.h"
#include <fstream>
#include <iostream>
#include <math.h>

using namespace std;

/**
 * Clase que se encarga de proveer la localización geográfica.
 */
class Localizacion {
	friend class TestLocalizacion;
	static Json::Value lugares;
	static double distanciaMaxima;
	static double calcularDistancia(Json::Value coordenadasA, Json::Value coordenadasB);
public:
	static void cargarLugares(string pathArchivoLugares);
	static string calcularUbicacion(Json::Value coordenadas);
};

#endif /* SRC_UTILIDADES_LOCALIZACION_H_ */
