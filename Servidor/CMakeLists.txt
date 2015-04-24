project(taller2-mensajero)

cmake_minimum_required(VERSION 2.8)

###############
# Bibliotecas #
###############

set(CMAKE_MODULE_PATH ${CMAKE_MODULE_PATH} "${PROJECT_SOURCE_DIR}/lib/Modulos/")

set(ROCKSDB_ROOT_DIR "${PROJECT_SOURCE_DIR}/lib/rocksdb/")
#set(JSONCPP_ROOT_DIR "${PROJECT_SOURCE_DIR}/src/jsoncpp/")

find_package(RocksDB)
#find_package(JsonCpp)

############
# Includes #
############

set (MONGOOSECPP_PATH "${PROJECT_SOURCE_DIR}/src/WebServer/")

# Compilo libs

#message(STATUS "MONGOOSECPP_PATH = ${MONGOOSECPP_PATH}")

add_subdirectory(${MONGOOSECPP_PATH})

include_directories(${ROCKSDB_INCLUDE_DIRS})
include_directories(${MONGOOSECPP_PATH})
#include_directories(${JSONCPP_INCLUDE_DIRS})


#message(STATUS "JSONCPP_INCLUDE_DIRS = ${JSONCPP_INCLUDE_DIRS}")
#message(STATUS "JSONCPP_LIBRARIES = ${JSONCPP_LIBRARIES}")
#message(STATUS "ROCKSDB_INCLUDE_DIRS = ${ROCKSDB_INCLUDE_DIRS}")

###########
# Modulos #
###########

#add_subdirectory(src)

# Tests
enable_testing()
set (CPPUNIT_ROOT_DIR "${PROJECT_SOURCE_DIR}/lib/cppunit")
add_subdirectory(test)

