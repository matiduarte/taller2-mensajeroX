project(taller2-mensajero)

cmake_minimum_required(VERSION 2.8)

set (CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -lz -pthread -lrt -std=c++11 -Wall -Wextra")

# Bibliotecas 

set(CMAKE_MODULE_PATH ${CMAKE_MODULE_PATH} "${PROJECT_SOURCE_DIR}/lib/Modulos/")

set(LIBS_ROOT_DIR "${PROJECT_SOURCE_DIR}/lib/")
set(ROCKSDB_ROOT_DIR "${PROJECT_SOURCE_DIR}/lib/rocksdb/")
set(JSONCPP_ROOT_DIR "${PROJECT_SOURCE_DIR}/src/json/")
set (CPPUNIT_ROOT_DIR "${PROJECT_SOURCE_DIR}/lib/cppunit/")

find_package(BZ2)
find_package(Snappy)
find_package(Zlib)
find_package(RocksDB)
find_package(JsonCpp)
find_package(CppUnit)

# Includes 

set (MONGOOSECPP_PATH "${PROJECT_SOURCE_DIR}/src/WebServer/")


set (ENTIDADES_PATH "${PROJECT_SOURCE_DIR}/src/Entidades/")
set (SERVIDOR_PATH "${PROJECT_SOURCE_DIR}/src/Servidor/")
set (BD_PATH "${PROJECT_SOURCE_DIR}/src/BaseDeDatos/")
set (LOG_PATH "${PROJECT_SOURCE_DIR}/src/Log/")
set (UTILIDADES_PATH "${PROJECT_SOURCE_DIR}/src/Utilidades/")
set (TEST_PATH "${PROJECT_SOURCE_DIR}/test/")


add_subdirectory(${MONGOOSECPP_PATH})
add_subdirectory(${JSONCPP_ROOT_DIR})


# Librerias
#include_directories(${LIBS_ROOT_DIR})
include_directories(${SNAPPY_INCLUDE_DIRS})
include_directories(${ZLIB_INCLUDE_DIRS})
include_directories(${ROCKSDB_INCLUDE_DIRS})
include_directories(${MONGOOSECPP_PATH})
include_directories(${JSONCPP_INCLUDE_DIRS})
include_directories(${CPPUNIT_INCLUDE_DIRS})
include_directories(src)

#message(STATUS "CPPUNIT_FOUND = ${CPPUNIT_FOUND}")

add_subdirectory("${PROJECT_SOURCE_DIR}/src/")
#add_subdirectory(${TEST_PATH}/Usuario/)
#add_subdirectory(${TEST_PATH}/Conversacion/)
add_subdirectory(${ENTIDADES_PATH})
add_subdirectory(${SERVIDOR_PATH})
add_subdirectory(${BD_PATH})
add_subdirectory(${LOG_PATH})
add_subdirectory(${UTILIDADES_PATH})


# Tests
enable_testing()
add_subdirectory(${TEST_PATH})

add_executable(Servidor main.cpp)
#add_test(NAME test/Conversacion/TestConversacion COMMAND Servidor)
#add_test(NAME test/Usuario/TestUsuario.cpp COMMAND Servidor)
#add_test(NAME testSerializacionDeDatosDeUnUsuario COMMAND Servidor)
#add_test(NAME testModificacionDeDatosDelUsuario COMMAND Servidor)
#add_test(NAME test/StringUtil/TestStringUtil COMMAND Servidor)
#add_test(NAME test/BaseDeDatos/BaseDeDatosTests COMMAND Servidor)

target_link_libraries(Servidor ${ROCKSDB_LIBRARIES} ${SNAPPY_LIBRARIES} ${BZ2_LIBRARIES}  basededatos servidor)

