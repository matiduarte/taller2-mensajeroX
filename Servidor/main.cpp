#include <stdio.h>
#include <string.h>
#include "src/Log/Logger.h"
#include "src/Servidor/Servidor.h"
#include "src/Entidades/Usuario.h"
#include <iostream>

using namespace std;

/*static int ev_handler(struct mg_connection *conn, enum mg_event ev) {
  switch (ev) {
    case MG_AUTH: return MG_TRUE;
    case MG_REQUEST:
      mg_printf_data(conn, "Hello! Requested URI is [%s]", conn->uri);
      return MG_TRUE;
    default: return MG_FALSE;
  }
}*/

int main(void) {

	Usuario* user = new Usuario("Pepe", "foto", "1568017070");
	Usuario* user2 = new Usuario("Jose", "foto2", "156801515");
	string a = user->serializar();
	user->deserealizar(a);

	vector<Usuario*> usuarios;
	usuarios.push_back(user);
	usuarios.push_back(user2);

	vector<string> mensajes;
	mensajes.push_back("mensaje 1");
	mensajes.push_back("mensaje 2");

	Conversacion* conversacion = new Conversacion(usuarios, mensajes);

	string conversacionSerializada = conversacion->serializar();

	Conversacion* conversacion2 = new Conversacion();
	conversacion->deserealizar(conversacionSerializada);


	Servidor *servidor = new Servidor();
	servidor->iniciar("8080");

  return 0;
}
