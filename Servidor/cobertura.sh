#!/usr/bin/env bash

cd build/
make test

lcov --capture --directory src/ --output-file coverage.info
lcov -r coverage.info /usr/include/\* -o coverage.info
lcov -r coverage.info /usr/local/include/\* -o coverage.info
lcov -r coverage.info src/json/\* -o coverage.info
lcov -r coverage.info src/Entidades/Persistible.cpp -o coverage.info
genhtml coverage.info --output-directory out

xdg-open out/index.html
