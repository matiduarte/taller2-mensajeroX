#ifndef SRC_SERVIDOR_SERVIDOR_H_
#define SRC_SERVIDOR_SERVIDOR_H_

#include "../constantes.h"
#include "Servicio.h"
#include "../WebServer/mongoose.h"
#include <string>
#include "../Utilidades/StringUtil.h"
#include <thread>

using namespace std;

enum tipoDeServicio{
	PRUEBA,
	REGISTRAR_USUARIO,
	AUTENTICAR_USUARIO,
	ADMINISTRAR_PERFIL,
	ALMACENAR_CONVERSACION,
	CONSULTAR_USUARIO_ONLINE,
	CHECKIN_USUARIO,
	DESCONECTAR_USUARIO,
	OBTENER_ID_CONVERSACION,
	OBTENER_CONVERSACION,
	OBTENER_CONVERSACIONES,
	OBTENER_CONTACTOS,
	ALMACENAR_LISTA_DIFUSION,
	INVALIDO,
};


/**
 * Esta clase se encarga de proveer un servidor de servicios REST.
 */
class Servidor {
private:
	static Servicio* servicio;
	struct mg_server *webServer;
	static void administrarServicio(struct mg_connection* conn);

	static int ev_handler(struct mg_connection *conn, enum mg_event ev);

	static tipoDeServicio parsearURI(struct mg_connection* uri);

public:
	Servidor();
	virtual ~Servidor();
	void iniciar(char *puerto);
};

#endif /* SRC_SERVIDOR_SERVIDOR_H_ */
