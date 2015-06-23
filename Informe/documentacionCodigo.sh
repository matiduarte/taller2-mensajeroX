#!/usr/bin/env bash

doxygen ../Servidor/doxygenConfigServidor
doxygen ../Cliente/doxygenConfigCliente

xdg-open ../Informe/DocumentacionCodigoCliente/html/index.html
xdg-open ../Informe/DocumentacionCodigoServidor/html/index.html
