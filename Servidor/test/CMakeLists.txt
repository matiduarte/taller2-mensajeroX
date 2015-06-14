
add_executable(TestUsuario Usuario/TestUsuario.cpp main.cpp)
target_link_libraries(TestUsuario entidades  basededatos loger)

add_executable(TestConversacion Conversacion/TestConversacion.cpp main.cpp)
target_link_libraries(TestConversacion entidades basededatos loger)

add_executable(TestBaseDeDatos BaseDeDatos/BaseDeDatosTests.cpp main.cpp)
target_link_libraries(TestBaseDeDatos entidades loger)

add_executable(TestStringUtil StringUtil/TestStringUtil.cpp main.cpp)
target_link_libraries(TestStringUtil utilidades cppunit jsoncpp)

add_test(NAME "TestUsuario" COMMAND TestUsuario)
add_test(NAME "TestConversacion" COMMAND TestConversacion)
add_test(NAME "TestStringUtil" COMMAND TestStringUtil)
add_test(NAME "TestBaseDeDatos" COMMAND TestBaseDeDatos)
