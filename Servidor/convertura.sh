#!/usr/bin/env bash

cd build/
make tests
lcov --capture --directory src/ --output-file coverage.info
genhtml coverage.info --output-directory out
xdg-open out/index.html
