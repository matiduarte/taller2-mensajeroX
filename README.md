# taller2-mensajero  

Uso basico
-----------

### Instalación

Desde la linea de comando descarge el codigo e instale su propia copia:	

```
$ git clone https://github.com/matiduarte/taller2-mensajeroX.git  
$ cd taller2-mensajero/Servidor  
```

### Compilación

En la carpeta del servidor escribir:
```
$ mkdir build  
$ cd /build  
$ cmake ..  
$ make  
```
Una vez finalizado, ejecutar el servidor con ./Servidor  


##API REST
-----------
Instalar plug-in Postman en el navegador web y seguir los siguientes pasos para probar los diferentes servicios:  

###Usuario

####Registrar Usuario:

>URL: http://localhost:8080/registrarUsuario

Parámetros:   
key: params  
value: {"Telefono":"1234", "Nombre":"jose"}  
```
Respuesta Ejemplo: 
{
   "payload" : "Usuario registrado correctamente",
   "success" : "true"
}
```

####Consultar Usuario Online:

>URL: http://localhost:8080/consultarUsuarioOnline

Parámetros:  
key: params  
value: {"Telefono":"1234""}  
```
Respuesta Ejemplo: 
{
   "payload" : "true",
   "success" : "true"
}
```
####Desconectar Usuario:

>URL: http://localhost:8080/desconectarUsuario

Parámetros:  
key: params  
value: {"Telefono":"1234"}  
```
Respuesta Ejemplo: 
{
   "payload" : "El usuario jose cerro sesion correctamente",
   "success" : "true"
}
```
####Autenticar Usuario:

>URL: http://localhost:8080/autenticarUsuario

Parámetros:  
key: params  
value: {"Telefono":"1234"}  
```
Respuesta Ejemplo: 
{
   "payload" : "El usuario jose inicio sesion correctamente. Token: e255da3ce8f92a313557e734d67a9a24",
   "success" : "true"
}
```
###Conversación

####Almacenar Conversación:

>URL: http://localhost:8080/almacenarConversacion

Parámetros:  
key: params  
value: {"IdUsuarioEmisor":"123", "IdUsuarioReceptor":"1234", "Cuerpo":"esto es un mensaje","Fecha":"una fecha"}  
```
Respuesta Ejemplo: 
{
   "payload" : "Mensaje agregado correctamente",
   "success" : "true"
}
```
####Obtener Id Conversación:

>URL: http://localhost:8080/obtenerIdConversacion

Parámetros:  
key: params  
value: {"TelefonoEmisor":"123", "TelefonoReceptor":"1234"}  
```
Respuesta Ejemplo: 
{
   "payload" : "202cb962ac59075b964b07152d234b70-81dc9bdb52d04dc20036dbd8313ed055",
   "success" : "true"
}
```
####Obtener Conversación:

>URL: http://localhost:8080/enviarConversacion

Parámetros:  
key: params  
value: {"Id":"202cb962ac59075b964b07152d234b70-81dc9bdb52d04dc20036dbd8313ed055"}  
```
Respuesta Ejemplo: 
{
   "payload" : "[\n   \"{\\n   \\\"Cuerpo\\\" : \\\"esto es un mensaje\\\",\\n   \\\"Fecha\\\" : \\\"una fecha\\\",\\n   \\\"Id\\\" : \\\"e70d1f3c9c0b6a013a4f4fef1e2231e3\\\",\\n   \\\"IdUsuarioEmisor\\\" : \\\"123\\\"\\n}\\n\",\n   \"{\\n   \\\"Cuerpo\\\" : \\\"esto es un mensaje\\\",\\n   \\\"Fecha\\\" : \\\"una fecha\\\",\\n   \\\"Id\\\" : \\\"e70d1f3c9c0b6a013a4f4fef1e2231e3\\\",\\n   \\\"IdUsuarioEmisor\\\" : \\\"123\\\"\\n}\\n\",\n   \"{\\n   \\\"Cuerpo\\\" : \\\"esto es un mensaje\\\",\\n   \\\"Fecha\\\" : \\\"una fecha\\\",\\n   \\\"Id\\\" : \\\"e70d1f3c9c0b6a013a4f4fef1e2231e3\\\",\\n   \\\"IdUsuarioEmisor\\\" : \\\"123\\\"\\n}\\n\",\n   \"{\\n   \\\"Cuerpo\\\" : \\\"esto es un mensaje\\\",\\n   \\\"Fecha\\\" : \\\"una fecha\\\",\\n   \\\"Id\\\" : \\\"e70d1f3c9c0b6a013a4f4fef1e2231e3\\\",\\n   \\\"IdUsuarioEmisor\\\" : \\\"123\\\"\\n}\\n\",\n   \"{\\n   \\\"Cuerpo\\\" : \\\"esto es un mensaje\\\",\\n   \\\"Fecha\\\" : \\\"una fecha\\\",\\n   \\\"Id\\\" : \\\"e70d1f3c9c0b6a013a4f4fef1e2231e3\\\",\\n   \\\"IdUsuarioEmisor\\\" : \\\"123\\\"\\n}\\n\"\n]\n",
   "succe
```
