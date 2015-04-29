project(taller2-mensajero)

add_library(entidades Persistible.cpp Conversacion.cpp Mensaje.cpp Usuario.cpp)

target_link_libraries(entidades utilidades cppunit)
