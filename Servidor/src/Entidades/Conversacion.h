/*
 * Conversacion.h
 *
 *  Created on: 24/3/2015
 *      Author: matias
 */

#ifndef SRC_ENTIDADES_CONVERSACION_H_
#define SRC_ENTIDADES_CONVERSACION_H_

#include "Persistible.h"
#include "Usuario.h"
#include <vector>
#include "../Utilidades/StringUtil.h"
#include "../BaseDeDatos/BaseDeDatos.h"

class BaseDeDatos;
class Conversacion : public Persistible {
private:
	string id;
	vector<string> mensajes;
	vector<Usuario*> usuarios;

public:
	Conversacion(vector<Usuario*> usuarios, vector<string> mensajes);
	Conversacion(string conversacionSerializada);
	virtual ~Conversacion();
	string serializar();
	int deserealizar(string aDeserealizar);
	void persistir();
	string getId();
	vector<string> getMensajes();
	void agregarMensaje(string mensaje);
	vector<Usuario*> getUsuarios();
};

#endif /* SRC_ENTIDADES_CONVERSACION_H_ */
