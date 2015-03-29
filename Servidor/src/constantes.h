/*
 * constantes.h
 *
 *  Created on: 25/3/2015
 *      Author: matias
 */

#ifndef SRC_CONSTANTES_H_
#define SRC_CONSTANTES_H_

#include <string>
using namespace std;

#define archivoLog "Logger.txt"
#define nivelLogger "Error"
#define Error "error"
#define Warn "warn"
#define Info "info"
#define Debug "debug"


const string path_BaseDeDatos = "Base de Datos";

/*Claves principales de los Usuarios*/
#define keyNombre "Nombre"
#define keyId "Id"
#define keyFotoDePerfil "Foto de Perfil"
#define keyEstadoDeConexion "Estado de Conexion"
#define keyUltimaConexion "Ultima Conexion"
#define keyTelefono "Telefono"

/*Claves principales de Conversacion*/
#define keyIdConversacion "Id"
#define keyIdsUsuarios "IdsUsuarios"
#define keyMensajes "Mensajes"

#define SeparadorListaBD '#'


#endif /* SRC_CONSTANTES_H_ */
