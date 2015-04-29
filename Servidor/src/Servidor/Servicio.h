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
#include "../Log/Loger.h"

class Servicio {
public:
	Servicio(struct mg_connection *conn);
	Servicio();
	virtual ~Servicio();
	void autenticarUsuario();
	void registrarUsuario();
	void enviarConversacion(Conversacion *conversacion);
	void prueba();
	string getParametro(string nombreParametro, string valorDefault);
	Json::Value getParametroArray(string nombreParametro, string valorDefault);
	void parsearParametros();
	void administrarPerfil();
	void checkinUsuario();
	void desconectarUsuario();
	Usuario* obtenerUsuario();
	void consultarUsuarioOnline();
	void almacenarConversacion();
	void enviarConversacion();
	void enviarConversaciones();
	void responder(string mensaje, bool error);

private:
	Json::Value parametros;
	struct mg_connection* connexion;
};

#endif /* SRC_SERVIDOR_SERVICIO_H_ */
