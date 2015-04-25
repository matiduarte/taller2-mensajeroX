project(mensajero)

add_library(basededatos	BaseDeDatos.cpp)

target_link_libraries(basededatos ${ROCKSDB_LIBRARIES})
