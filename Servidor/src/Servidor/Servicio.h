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
#include "../Utilidades/Localizacion.h"

/**
 * Clase encargada de proporcionar los servicios
 *
 *
 */
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
	string getParametroIdMetodoGET(string urlBase);
	void parsearParametros();
	void administrarPerfil();
	Usuario* obtenerUsuario();
	void almacenarConversacion();
	void obtenerConversacion();
	void obtenerConversaciones();
	void obtenerIdConversacion();
	void consultarUsuarioOnline();
	void obtenerContactos();
	void almacenarListaDifusion();
	void checkIn();
	void responder(string mensaje, bool error);

private:
	Json::Value parametros;
	struct mg_connection* connexion;
	static int tamanioBuffer;
};

#endif /* SRC_SERVIDOR_SERVICIO_H_ */
