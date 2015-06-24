1-Guía de Administrador
=======================

Guía para el Administrador: instalación, configuración, ejecución

Instalación
------------

Desde la linea de comando descarge el codigo e instale su propia copia:

.. code-block:: bash

   $ git clone https://github.com/matiduarte/taller2-mensajeroX.git 


Dependencias
------------

Instlación rocksdb:

.. code-block:: bash

   $ cd taller2-mensajero/Servidor/lib/rocksdb-master/
   $ make static_lib
   $ mv -i librocksdb.a ../

El resto de las dependencias:

.. code-block:: bash

   $ sudo apt-get install libsnappy-dev
   $ sudo apt-get install zlib1g-dev
   $ sudo apt-get install libbz2-dev
   $ sudo apt-get install libcppunit-dev

Compilación
------------

En la carpeta root del proyecto:

.. code-block:: bash

   $ cd /Servidor
   $ mkdir build  
   $ cd /build  
   $ cmake ..  
   $ make  


Ejecución
-----------

Una vez finalizado, ejecutar el servidor:

.. code-block:: bash

   $ ./Servidor -p PUERTO

En caso de no recibir puerto como argumento, se seteará el puerto 8080 por defecto.

