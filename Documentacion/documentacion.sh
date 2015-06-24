#!/usr/bin/env bash

doxygen ../Servidor/doxygenConfigServidor
doxygen ../Cliente/doxygenConfigCliente
make html

xdg-open ../Documentacion/_build/html/index.html
