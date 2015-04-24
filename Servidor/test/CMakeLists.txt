project(taller2-mensajero)

include_directories(${PROJECT_SOURCE_DIR}/../main)

add_subdirectory(Usuario)

add_executable(test main.cpp)

target_link_libraries(test cppunit testUsuario)
