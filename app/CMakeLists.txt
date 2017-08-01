# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

add_library( # Sets the name of the library.
             lame-lib

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             src/main/cpp/lame_lib.cpp src/main/cpp/lame/bitstream.c src/main/cpp/lame/version.c src/main/cpp/lame/presets.c src/main/cpp/lame/set_get.c src/main/cpp/lame/lame.c src/main/cpp/lame/fft.c src/main/cpp/lame/quantize_pvt.c src/main/cpp/lame/mpglib_interface.c src/main/cpp/lame/takehiro.c src/main/cpp/lame/vbrquantize.c src/main/cpp/lame/newmdct.c src/main/cpp/lame/reservoir.c src/main/cpp/lame/VbrTag.c src/main/cpp/lame/gain_analysis.c src/main/cpp/lame/psymodel.c src/main/cpp/lame/id3tag.c src/main/cpp/lame/tables.c src/main/cpp/lame/util.c src/main/cpp/lame/encoder.c src/main/cpp/lame/quantize.c )

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

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

target_link_libraries( # Specifies the target library.
                       lame-lib

                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib} )