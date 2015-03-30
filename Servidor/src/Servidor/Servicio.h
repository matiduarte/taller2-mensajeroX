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
#include "../WebServer/mongoose.h"
#include "../json/json.h"

class Servicio {
public:
	Servicio();
	virtual ~Servicio();
	bool autenticarUsuario();
	void registrarUsuario();
	void enviarConversacion(Conversacion *conversacion);
	void prueba();
	string getParametro(string nombreParametro, string valorDefault);
	void parsearParametros(struct mg_connection *conn);
private:
	Json::Value parametros;

};

#endif /* SRC_SERVIDOR_SERVICIO_H_ */
