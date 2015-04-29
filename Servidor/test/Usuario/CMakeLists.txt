project(taller2-mensajero)

add_library(testUsuario TestUsuario.cpp)

message(STATUS "INCLUYENDO TEST USUARIO")
target_link_libraries(testUsuario cppunit entidades constantes)
