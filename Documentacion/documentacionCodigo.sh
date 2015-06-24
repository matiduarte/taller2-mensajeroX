#!/usr/bin/env bash

doxygen ../Servidor/doxygenConfigServidor
doxygen ../Cliente/doxygenConfigCliente

xdg-open ../Documentacion/DocumentacionCodigoCliente/html/index.html
xdg-open ../Documentacion/DocumentacionCodigoServidor/html/index.html
