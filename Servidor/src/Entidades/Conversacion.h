/*
 * Conversacion.h
 *
 *  Created on: 24/3/2015
 *      Author: matias
 */

#ifndef SRC_ENTIDADES_CONVERSACION_H_
#define SRC_ENTIDADES_CONVERSACION_H_

#include "Persistible.h"
#include <vector>
#include "../Utilidades/StringUtil.h"
#include "../BaseDeDatos/BaseDeDatos.h"
#include "Mensaje.h"

class BaseDeDatos;
class Usuario;
class Conversacion : public Persistible {
private:
	string id;
	vector<Mensaje*> mensajes;
	vector<Usuario*> usuarios;

public:
	Conversacion(vector<Usuario*> usuarios, vector<Mensaje*> mensajes);
	Conversacion(string conversacionSerializada);
	Conversacion();
	virtual ~Conversacion();
	string serializar();
	int deserealizar(string aDeserealizar);
	void persistir();
	static Conversacion* obtener(string clave);
	static void eliminar(string clave);
	string getId();
	vector<Mensaje*> getMensajes();
	void agregarMensaje(Mensaje* mensaje);
	vector<Usuario*> getUsuarios();
};

#endif /* SRC_ENTIDADES_CONVERSACION_H_ */
