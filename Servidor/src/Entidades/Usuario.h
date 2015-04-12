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
#include "../test/Usuario/TestUsuario.h"
#include "../BaseDeDatos/BaseDeDatos.h"
#include "../Log/Loger.h"

class BaseDeDatos;

using namespace std;

class TestUsuario;

class Usuario: public Persistible
	 {
	friend TestUsuario;
private:
	string nombre;
	string fotoDePerfil;
	string id;
	string telefono;
	bool conectado;
	string localizacion;
	string ultimaConexion;
	string token;

public:
	Usuario();
	Usuario(string nombre, string fotoDePerfil, string telefono);
	Usuario(string usuarioSerializado);
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
	string getFotoDePerfil();
	string serializar();
	bool getEstadoConexion();
	int deserealizar(string aDeserealizar);
	void persistir();
	static Usuario* obtener(string clave);
	static void eliminar(string clave);
	static string obtenerId(string telefono);
	string getLocalizacion();
	void setLocalizacion(string localizacion);
	string calcularTokenDeSesion();
	virtual ~Usuario();
	vector<string> obtnerIdsConversaciones();
};

#endif /* SRC_ENTIDADES_USUARIO_H_ */
