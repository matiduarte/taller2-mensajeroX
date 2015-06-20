# taller2-mensajero  

Uso basico
-----------

### Instalación

Desde la linea de comando descarge el codigo e instale su propia copia:	

```
$ git clone https://github.com/matiduarte/taller2-mensajeroX.git  
$ cd taller2-mensajero/Servidor  
```

### Dependencias

```

$ cd Servidor/lib/rocksdb-master/
$ make static_lib
$ mv -i librocksdb.a ../

$ sudo apt-get install libsnappy-dev
$ sudo apt-get install zlib1g-dev
$ sudo apt-get install libbz2-dev
$ sudo apt-get install libcppunit-dev
```

### Compilación

En la carpeta del servidor escribir:
```
$ mkdir build  
$ cd /build  
$ cmake ..  
$ make  
```
Una vez finalizado, ejecutar el servidor con ./Servidor -p PUERTO

En caso de no recibir puerto como argumento, se seteará el puerto 8080 por defecto.

### Pruebas

Sobre la carpeta build ejecutar:  
```
$ make test 
```

Para ver el resultado de las pruebas en detalle
```
$ ctest -V
```
### Cobertura  

Desde la consola ingresar:  
```
sudo apt-get install lcov
```

Se agrega un script que recoge la información de cobertura a través de las pruebas y la muestra en un explorador web. Para correr este script, dirigirse desde la consola a la carpeta del Servidor e ingresar:

```
$ sh cobertura.sh
```



##API REST
-----------
Instalar plug-in Postman en el navegador web y seguir los siguientes pasos para probar los diferentes servicios:  


####Registrar Usuario:
operación: POST
>URL: http://localhost:8080/usuario/

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
operación: POST
>URL: http://localhost:8080/conversacion

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


####Editar Perfil:
operación: PUT
>URL: http://localhost:8080/usuario/

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


####Consultar usuario:
operación: GET
>URL: http://localhost:8080/usuario/112233  

```
Respuesta Ejemplo:
{
   "payload" : "{\n   \"EstadoDeConexion\" : \"true\",\n   \"FotoDePerfil\" : \"default\",\n   \"Nombre\" : \"Juan\",\n   \"Password\" : \"goku\",\n   \"Token\" : \"b1d3cc2ebd0d1ebfcdeb16de173e99b6\",\n   \"idUsuario\" : \"0a0625f4dba80e60e7bb4e37114f744f\"\n}\n",
   "success" : "true"
}

```

####Obtener conversacion:
operación: GET
>URL: http://localhost:8080/usuarioConversacion/1112233  

```
respuesta:
{
   "payload" : "{\n   \"conversaciones\" : [\n      {\n         \"id\" : \"0a0625f4dba80e60e7bb4e37114f744f-8ca745744c1910342bb2441b61951494\",\n         \"ultimoMensaje\" : \"Todo bien. ¿Vos?\",\n         \"usuarioFotoDePerfil\" : \"default\",\n         \"usuarioNombre\" : \"Pepe\",\n         \"usuarioTelefono\" : \"1111223344\"\n      }\n   ]\n}\n",
   "success" : "true"
}
```  
