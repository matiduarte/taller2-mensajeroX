project(taller2-mensajero)

add_library(testUsuario TestUsuario.cpp)

target_link_libraries(testUsuario cppunit entidades)
