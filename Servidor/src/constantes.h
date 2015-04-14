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

/*constantes para estado de conexion*/
const bool Online = true;
const bool Offline = false;

const string path_BaseDeDatos = "Base de Datos";
const string keyDefault = "";

/*Claves principales de los Usuarios*/
#define keyNombre "Nombre"
#define keyId "Id"
#define keyFotoDePerfil "Foto de Perfil"
#define keyEstadoDeConexion "Estado de Conexion"
#define keyUltimaConexion "Ultima Conexion"
#define keyTelefono "Telefono"
#define keyLocalizacion "Localizacion"

/*Claves principales de Conversacion*/
#define keyIdConversacion "Id"
#define keyIdsUsuarios "IdsUsuarios"
#define keyMensajes "Mensajes"

/*Claves principales de Mensaje*/
#define keyCuerpo "Cuerpo"
#define keyIdUsuarioEmisor "IdUsuarioEmisor"
#define keyFecha "Fecha"
#define keyIdUsuarioReceptor "IdUsuarioReceptor"



#define SeparadorListaBD '#'

/*Constantes para Servicios*/
#define urlRegistrarUsuario "/registrarUsuario"
#define urlPrueba "/prueba"
#define urlAutenticarUsuario "/autenticarUsuario"
#define urlAdministrarPerfil "/administrarPerfil"
#define urlConsultarUsuarioOnline "/consultarUsuarioOnline"

/*Constantes para el manejo de errores*/
const string keyDatoNoEncontrado = "NotFound";
const string keyIdUsuarioNoEncontrado = "UsarioInvalido";
const string keyIdConversacionNoEncontrada = "ConversacionInvalida";


#endif /* SRC_CONSTANTES_H_ */
