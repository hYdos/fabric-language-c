cmake_minimum_required(VERSION 3.17)
project(FabricLangC C)

set(CMAKE_C_STANDARD 11)

find_package(JNI REQUIRED)

include_directories(${JNI_INCLUDE_DIRS})

# LINK_DIRECTORIES(/usr/lib/jvm/java-14-openjdk/lib/server)
LINK_DIRECTORIES("C:\\Program Files\\AdoptOpenJDK\\jdk-8.0.252.09-hotspot\\lib")

add_library(FabricLangC SHARED fabriclangC.c fabriclangC.h Bridge.h class_builder.c class_builder.h)

TARGET_LINK_LIBRARIES(FabricLangC jvm)