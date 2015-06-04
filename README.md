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

###POST

####Registrar Usuario:

>URL: http://localhost:808/usuario

Parámetros:  
key: Nombre    value: Juan  
key: Telefono  value: 1112345678  
key: Password  value: goku  
```
Respuesta Ejemplo: 
{
   "payload" : "Usuario registrado correctamente",
   "success" : "true"
}
```
####Enviar Mensaje:

>URL: http://localhost:808/conversacion

Parametros:  
key: IdUsuarioEmisor	value: 11223344  
key: IdUsuarioReceptor	value: 11334455  
key: Cuerpo		value: Hola  
key: Fecha		value: 2015-06-03 18:30:23  
key: Token		value: g231lsaqwe123129fjq2j123  
```
Respuesta Ejemplo: 
{
   "payload" : "188c3d61cf52bda68a52f7afe6070727",
   "success" : "true"
}
```

###PUT

####Registrar Usuario:

>URL: http://localhost:8080/usuario

Parámetros:  
key: Nombre		value: Juan  
key: Telefono	        value: 112233  
key: Password	        value: goku  
key: FotoDePerfil	value: default  
key Token		value: kasd912jdsr12jjajs291283192  
key: EstadoDeConexion	value: true  
```
Respuesta Ejemplo:
{
   "payload" : "Se modificaron los datos del usuario Juan correctamente. Token:",
   "success" : "true"
}
```

###GET

####Consultar usuario:

>URL: http://localhost:8080/usuario/112233
```
Respuesta Ejemplo:
{
   "payload" : "{\n   \"EstadoDeConexion\" : \"true\",\n   \"FotoDePerfil\" : \"default\",\n   \"Nombre\" : \"Juan\",\n   \"Password\" : \"goku\",\n   \"Token\" : \"b1d3cc2ebd0d1ebfcdeb16de173e99b6\",\n   \"idUsuario\" : \"0a0625f4dba80e60e7bb4e37114f744f\"\n}\n",
   "success" : "true"
}

```
