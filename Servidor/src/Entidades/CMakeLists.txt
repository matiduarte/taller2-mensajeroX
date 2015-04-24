project(taller2-mensajero)

add_library(entidades Usuario.h Usuario.cpp)

target_link_libraries(entidades cppunit entidades)
