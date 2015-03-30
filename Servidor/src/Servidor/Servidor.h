/*
 * Servidor.h
 *
 *  Created on: 24/3/2015
 *      Author: matias
 */

#ifndef SRC_SERVIDOR_SERVIDOR_H_
#define SRC_SERVIDOR_SERVIDOR_H_

#include "../constantes.h"
#include "Servicio.h"
#include "../WebServer/mongoose.h"
#include <string>
#include "../Utilidades/StringUtil.h"

using namespace std;

enum tipoDeServicio{
	PRUEBA,
	REGISTRAR_USUARIO,
	AUTENTICAR_USUARIO,
	INVALIDO,
};

class Servidor {
private:
	static Servicio* servicio;
	struct mg_server *webServer;
	static void administrarServicio(struct mg_connection *conn);
	static int ev_handler(struct mg_connection *conn, enum mg_event ev);

	static tipoDeServicio parsearURI(const char* uri);

public:
	Servidor();
	virtual ~Servidor();
	void iniciar(char *puerto);
};

#endif /* SRC_SERVIDOR_SERVIDOR_H_ */
