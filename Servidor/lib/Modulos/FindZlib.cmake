
find_library(ZLIB_LIBRARIES z "${LIBS_ROOT_DIR}")

set(ZLIB_FOUND TRUE)

if (NOT BZ2_LIBRARIES)
  set(ZLIB_FOUND FALSE)
endif (NOT BZ2_LIBRARIES)
