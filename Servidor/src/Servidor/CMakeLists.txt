project(taller2-mensajero)

add_library(servidor Servidor.cpp Servicio.cpp)

#Falta linkear la libreria de thread
target_link_libraries(servidor cppunit entidades mongoose jsoncpp loger)
