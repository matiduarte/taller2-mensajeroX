3-Purebas y Covertura
===================

Instrucciones para correr los test unitarios y calcular el coverage del código

Pruebas
------------

Sobre la carpeta Servidor/build ejecutar:

.. code-block:: bash

   $ make test 


Si no se ha compilado el servidor, antes debe ejecutarse sobre la carpeta Servidor/build:

.. code-block:: bash

   $ cmake ..
   $ make 

Para ver el resultado de las pruebas en detalle

.. code-block:: bash

   $ ctest -V


Pruebas REST API
-----------------

Se incluye un script en python para realizar las pruebas sobre la REST API. Dicho script se encuentra en Servidor/TestRestApi/restTester.py

Dentro de la carpeta TestRestApi/ se encuentra un archivo de configuración de ejemplo: restTest.cfg.sample. Para la ejecución de pruebas se debe crear un archivo restTest.cfg, identico al sample.

Finalmente para correr las pruebas (el servidor debe estar corriendo):

.. code-block:: bash

   $ python restTester.py



Cobertura
----------


Desde la consola ingresar:

.. code-block:: bash

   $ sudo apt-get install lcov

Se ha agregado un script que recoge la información de cobertura a través de las pruebas y la muestra en un explorador web. Para correr este script, dirigirse desde la consola a la carpeta de Servidor/ e ingresar:

.. code-block:: bash

   $ sh cobertura.sh
