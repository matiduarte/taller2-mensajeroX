/*
 * Usuario.h
 *
 *  Created on: 24/3/2015
 *      Author: matias
 */

#ifndef SRC_ENTIDADES_USUARIO_H_
#define SRC_ENTIDADES_USUARIO_H_

#include <string.h>
#include <iostream>
#include <time.h>
#include "Persistible.h"
#include "../Utilidades/md5.h"

using namespace std;

class Usuario: public Persistible {
private:
	string nombre;
	string fotoDePerfil;
	string id;
	string telefono;
	bool conectado;
	string ultimaConexion;

public:
	Usuario(string nombre, string fotoDePerfil, string telefono);
	Usuario();
	void registrar();
	void modificarDatos();
	void registrarUltimaConexion();
	string getUltimaConexion();
	string getId();
	virtual ~Usuario();
};

#endif /* SRC_ENTIDADES_USUARIO_H_ */
