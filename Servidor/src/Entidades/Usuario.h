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
	string fotoDePerfil = "default";
	string id;
	string telefono;
	bool conectado = true;
	string localizacion = "desconocida";
	string ultimaConexion;
	string token;
	string password;
	static string obtenerId(string telefono);

public:
	Usuario();
	Usuario(string nombre, string fotoDePerfil, string telefono, string password);
	Usuario(string nombre, string telefono, string password);
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
	void setPassword(string password);
	string getUltimaConexion();
	string getId();
	string getNombre();
	string getTelefono();
	string getFotoDePerfil();
	string getPassword();
	string serializar();
	bool getEstadoConexion();
	int deserealizar(string aDeserealizar);
	void persistir();
	static Usuario* obtener(string clave);
	static Usuario* obtenerPorTelefono(string telefono);
	static void eliminar(string clave);
	string getLocalizacion();
	void setLocalizacion(string localizacion);
	string calcularTokenDeSesion();
	virtual ~Usuario();
	vector<string> obtnerIdsConversaciones();
	string getToken();

};

#endif /* SRC_ENTIDADES_USUARIO_H_ */
