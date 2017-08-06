# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${CMAKE_CURRENT_SOURCE_DIR}/../../../libs/${ANDROID_ABI}/ )

file(GLOB lame_src lame_lib.cpp lame/*.c)

set(lame_src ${lame_src})

add_library( # Sets the name of the library.
             lame-lib

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             ${lame_src} )

#set_property(TARGET  lame-lib PROPERTY IMPORTED_LOCATION ${CMAKE_CURRENT_SOURCE_DIR}/libs/${ANDROID_ABI}/liblame-lib.so )
MESSAGE(STATUS "libsdir=${CMAKE_CURRENT_SOURCE_DIR}/../../../libs/${ANDROID_ABI}/")
# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )
MESSAGE(STATUS "log-lib=${log-lib}")
# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

target_link_libraries( # Specifies the target library.
                       lame-lib

                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib} )

set(ffmpeg_dir ${CMAKE_CURRENT_SOURCE_DIR}/../../../../ffmpeg-3.3.3/build/android/${ANDROID_ABI})
set(ENV{PKG_CONFIG_PATH} "${ffmpeg_dir}/lib/pkgconfig/:$ENV{PKG_CONFIG_PATH}")
INCLUDE_DIRECTORIES(${ffmpeg_dir}/include)


file(GLOB ffmpeg_src ffmpeg_lib.cpp)

set(ffmpeg_src ${ffmpeg_src})

add_library( # Sets the name of the library.
             ffmpeg-lib

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             ${ffmpeg_src}
             )
#set_property(TARGET ffmpeg-lib PROPERTIES LINKER_LANGUAGE CXX)

set(ffmpeg_static_lib_dir ${ffmpeg_dir}/lib)
MESSAGE(STATUS "ffmpeg_static_lib_dir=${ffmpeg_static_lib_dir}")

#link_directories(${ffmpeg_static_lib_dir})

add_library(avcodec STATIC IMPORTED)
set_property(TARGET avcodec PROPERTY IMPORTED_LOCATION ${ffmpeg_static_lib_dir}/libavcodec.a)

add_library(avfilter STATIC IMPORTED)
set_property(TARGET avfilter PROPERTY IMPORTED_LOCATION ${ffmpeg_static_lib_dir}/libavfilter.a)

add_library(avformat STATIC IMPORTED)
set_property(TARGET avformat PROPERTY IMPORTED_LOCATION ${ffmpeg_static_lib_dir}/libavformat.a)

add_library(avutil STATIC IMPORTED)
set_property(TARGET avutil PROPERTY IMPORTED_LOCATION ${ffmpeg_static_lib_dir}/libavutil.a)

add_library(swresample STATIC IMPORTED)
set_property(TARGET swresample PROPERTY IMPORTED_LOCATION ${ffmpeg_static_lib_dir}/libswresample.a)

add_library(swscale STATIC IMPORTED)
set_property(TARGET swscale PROPERTY IMPORTED_LOCATION ${ffmpeg_static_lib_dir}/libswscale.a)

find_library( # Sets the name of the path variable.
              z-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              z )
find_library( # Sets the name of the path variable.
              m-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              m )
find_library( # Sets the name of the path variable.
              android-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              android )

add_definitions(-lavcodec -lm -lz -pthread -lgcc -lavfilter -lavformat -lavutil -lswresample -lswscale)

target_link_libraries( # Specifies the target library.
                       ffmpeg-lib

                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib}
                       ${android-lib}
                       swscale
                       avformat
                       avcodec
                       swresample
                       avutil
                       avfilter
                       ${z-lib}
                       ${m-lib}
                       )