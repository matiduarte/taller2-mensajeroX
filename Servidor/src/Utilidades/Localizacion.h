/*
 * Localizacion.h
 *
 *  Created on: 17/6/2015
 *      Author: juanma
 */

#ifndef SRC_UTILIDADES_LOCALIZACION_H_
#define SRC_UTILIDADES_LOCALIZACION_H_

#include "../json/json.h"
using namespace std;

/**
 * Clase que se encarga de proveer la localización geográfica.
 */
class Localizacion {
	static Json::Value ubicaciones;
	static void cargarUbicaciones(string pathArchivoUbicaciones);
	static double calcularDistancia(Json::Value coordenadasA, Json::Value coordenadasB);
	static string calcularUbicacion(Json::Value coordenadas);
};

#endif /* SRC_UTILIDADES_LOCALIZACION_H_ */
