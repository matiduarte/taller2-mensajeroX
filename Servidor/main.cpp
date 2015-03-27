#include <stdio.h>
#include <string.h>
#include "src/Log/Logger.h"
#include "src/Servidor/Servidor.h"

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


	Logger* logger = Logger::getLogger();
	logger->guardarEstado();
	logger->error("ESTO ES UN ERROR");
	logger->warn("ESTO ES UN WARNING");
	logger->info("ESTO ES INFO");
	logger->debug("ESTO ES UN DEBUG");

	Servidor *servidor = new Servidor();
	servidor->iniciar("8080");


	delete logger;

  return 0;
}
