/*
 * Servicio.h
 *
 *  Created on: 24/3/2015
 *      Author: matias
 */

#ifndef SRC_SERVIDOR_SERVICIO_H_
#define SRC_SERVIDOR_SERVICIO_H_

#include "../Entidades/Usuario.h"
#include "../Entidades/Conversacion.h"

class Servicio {
public:
	Servicio();
	virtual ~Servicio();
	bool autenticarUsuario(Usuario *usuario);
	void registrarUsuario(Usuario *usuario);
	void enviarConversacion(Conversacion *conversacion);
	void prueba();

};

#endif /* SRC_SERVIDOR_SERVICIO_H_ */
