project(taller2-mensajero)

add_library(logger Logger.cpp)

target_link_libraries(logger utilidades)
