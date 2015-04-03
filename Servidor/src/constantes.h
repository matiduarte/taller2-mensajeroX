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

/*Constantes de Logeo*/
#define archivoLog "Logger.txt"
#define nivelLogger "Info"
#define Error "error"
#define Warn "warn"
#define Info "info"
#define Debug "debug"


const string path_BaseDeDatos = "Base de Datos";
const string keyDefault = "";

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

/*Constantes para Servicios*/
#define urlRegistrarUsuario "/registrarUsuario"
#define urlPrueba "/prueba"
#define urlAutenticarUsuario "/autenticarUsuario"

/*Constantes para el manejo de errores*/
const string keyDatoNoEncontrado = "NotFound";
const string keyIdUsuarioNoEncontrado = "UsarioInvalido";
const string keyIdConversacionNoEncontrada = "ConversacionInvalida";

#endif /* SRC_CONSTANTES_H_ */
