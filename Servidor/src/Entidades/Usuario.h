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
#include "../json/json.h"
#include "../constantes.h"

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
	void setId(string id);
	void setNombre(string nombre);
	void setFotoDePerfil(string fotoDePerfil);
	void setTelefono(string telefono);
	void setEstadoConexion(bool estado);
	void setUltimaConexion(string ultimaConexion);
	string getUltimaConexion();
	string getId();
	string getNombre();
	string getTelefono();
	string serializar();
	bool getEstadoConexion();
	int deserealizar(string aDeserealizar);
	void persistir();
	virtual ~Usuario();
};

#endif /* SRC_ENTIDADES_USUARIO_H_ */
