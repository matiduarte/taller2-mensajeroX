project(taller2-mensajero)

cmake_minimum_required(VERSION 2.8)

set (CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11 -Wall -Wextra")

# Bibliotecas 

set(CMAKE_MODULE_PATH ${CMAKE_MODULE_PATH} "${PROJECT_SOURCE_DIR}/lib/Modulos/")

set(ROCKSDB_ROOT_DIR "${PROJECT_SOURCE_DIR}/lib/rocksdb/")
set(JSONCPP_ROOT_DIR "${PROJECT_SOURCE_DIR}/src/json/")

find_package(RocksDB)
#find_package(JsonCpp)

# Includes 

set (MONGOOSECPP_PATH "${PROJECT_SOURCE_DIR}/src/WebServer/")
set (ENTIDADES_PATH "${PROJECT_SOURCE_DIR}/src/Entidades/")
set (SERVIDOR_PATH "${PROJECT_SOURCE_DIR}/src/Servidor/")
set (BD_PATH "${PROJECT_SOURCE_DIR}/src/BaseDeDatos/")
set (LOG_PATH "${PROJECT_SOURCE_DIR}/src/Log/")
set (UTILIDADES_PATH "${PROJECT_SOURCE_DIR}/src/Utilidades/")
set (TEST_PATH "${PROJECT_SOURCE_DIR}/test/")
set (CPPUNIT_ROOT_DIR "${PROJECT_SOURCE_DIR}/lib/cppunit")

add_subdirectory(${MONGOOSECPP_PATH})
add_subdirectory(${JSONCPP_ROOT_DIR})


# Librerias
include_directories(${ROCKSDB_INCLUDE_DIRS})
include_directories(${MONGOOSECPP_PATH})
#include_directories(${JSONCPP_INCLUDE_DIRS})


add_subdirectory(${ENTIDADES_PATH})
add_subdirectory(${SERVIDOR_PATH})
add_subdirectory(${BD_PATH})
add_subdirectory(${LOG_PATH})
add_subdirectory(${UTILIDADES_PATH})

# Modulos
add_subdirectory(src)

# Tests
enable_testing()
add_subdirectory(${TEST_PATH})

add_executable(Servidor main.cpp)


