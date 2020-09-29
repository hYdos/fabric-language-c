cmake_minimum_required(VERSION 3.17)
project(FabricLangC C)

set(CMAKE_C_STANDARD 11)

find_package(JNI REQUIRED)

include_directories(${JNI_INCLUDE_DIRS})

LINK_DIRECTORIES(/usr/lib/jvm/java-14-openjdk/lib/server)

add_library(FabricLangC SHARED fabriclangC.c fabriclangC.h Bridge.h)

TARGET_LINK_LIBRARIES(FabricLangC jvm)