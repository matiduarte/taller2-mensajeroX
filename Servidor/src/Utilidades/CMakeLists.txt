project(taller2-mensajero)

add_library(utilidades StringUtil.cpp md5.cpp)

target_link_libraries(utilidades jsoncpp)

