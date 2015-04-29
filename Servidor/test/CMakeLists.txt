project(taller2-mensajero)

include_directories(${PROJECT_SOURCE_DIR}/../src)

set (TEST_USUARIO_PATH "${TEST_PATH}/Usuario/")

add_subdirectory(${TEST_USUARIO_PATH})

#add_executable(test main.cpp)

#target_link_libraries(test cppunit testUsuario)
