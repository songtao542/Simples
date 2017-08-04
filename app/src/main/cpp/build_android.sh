#!/bin/bash
NDK=$HOME/Android/Sdk/ndk-bundle
ANDROID_API_LEVEL=22
GCC_VERSION=4.9

#arm="arm  "
#arm64="arm64 aarch64-linux-android-4.9 aarch64-linux-android-"
#x86="x86 x86-4.9 i686-linux-android-"
#x86_64="x86_64 x86_64-4.9 x86_64-linux-android-"

function build_one
{
SYSROOT=""
TOOLCHAIN=""

arch=""
sysroot=""
cross_prefix=""
cpu=""
extra_cflags="-marm"
extra_ldflags=""
config=""

prefix=$(pwd)/build/android/$1
if [ ! -d "$prefix" ]; then
   mkdir -p ${prefix}
fi

case $1 in
  armeabi)
    arch='arm'
    SYSROOT=${NDK}/platforms/android-${ANDROID_API_LEVEL}/arch-arm
    sysroot=${SYSROOT}
    TOOLCHAIN=${NDK}/toolchains/arm-linux-androideabi-${GCC_VERSION}/prebuilt/linux-x86_64
    cross_prefix=${TOOLCHAIN}/bin/arm-linux-androideabi-
    cpu="cortex-a8"
  ;;
  armeabi-v7a)
    arch='arm'
    SYSROOT=${NDK}/platforms/android-${ANDROID_API_LEVEL}/arch-arm
    sysroot=${SYSROOT}
    TOOLCHAIN=${NDK}/toolchains/arm-linux-androideabi-${GCC_VERSION}/prebuilt/linux-x86_64
    cross_prefix=${TOOLCHAIN}/bin/arm-linux-androideabi-
    cpu="cortex-a8"
    extra_cflags="$extra_cflags -march=armv7-a -mcpu=cortex-a8 -mfpu=vfpv3-d16 -mfloat-abi=softfp -mthumb"
    extra_ldflags="$extra_ldflags -Wl,--fix-cortex-a8"
    config="$config --enable-neon --enable-thumb"
  ;;
  arm64)
    arch='aarch64'
    SYSROOT=${NDK}/platforms/android-${ANDROID_API_LEVEL}/arch-arm64
    sysroot=${SYSROOT}
    TOOLCHAIN=${NDK}/toolchains/aarch64-linux-android-${GCC_VERSION}/prebuilt/linux-x86_64
    cross_prefix=${TOOLCHAIN}/bin/aarch64-linux-android-
    cpu="cortex-a8"
    config="$config --enable-yasm"
  ;;
  x86)
    arch='x86'
    SYSROOT=${NDK}/platforms/android-${ANDROID_API_LEVEL}/arch-x86
    sysroot=${SYSROOT}
    TOOLCHAIN=${NDK}/toolchains/x86-${GCC_VERSION}/prebuilt/linux-x86_64
    cross_prefix=${TOOLCHAIN}/bin/i686-linux-android-
    cpu="i686"
    extra_cflags="$extra_cflags -march=i686 -march=atom -msse3 -ffast-math -mfpmath=sse"
    config="$config --enable-yasm"
  ;;
  x86_64)
    arch='x86_64'
    SYSROOT=${NDK}/platforms/android-${ANDROID_API_LEVEL}/arch-x86_64
    sysroot=${SYSROOT}
    TOOLCHAIN=${NDK}/toolchains/x86_64-${GCC_VERSION}/prebuilt/linux-x86_64
    cross_prefix=${TOOLCHAIN}/bin/x86_64-linux-android-
    cpu="i686"
    extra_cflags="$extra_cflags -march=i686"
    config="$config --enable-yasm"
  ;;
esac


echo "cpu=$cpu"
echo "PREFIX=$prefix"
echo "SYSROOT=$SYSROOT"
echo "TOOLCHAIN=$TOOLCHAIN"
echo "cross-prefix=$cross_prefix"

if [ ! -d "$(pwd)/tmp" ]; then
   mkdir $(pwd)/tmp
fi
export TMPDIR="$(pwd)/tmp"
#make clean

./configure \
--prefix=${prefix} \
--disable-shared \
--enable-static \
--disable-doc \
--disable-ffmpeg \
--disable-ffplay \
--disable-ffprobe \
--disable-ffserver \
--disable-avdevice \
--disable-doc \
--disable-symver \
--cross-prefix=${cross_prefix} \
--target-os=linux \
--arch=${cpu} \
--extra-libs=-lgcc \
--enable-cross-compile \
--sysroot=${sysroot} \
--extra-cflags="-Os -fpic $extra_cflags" \
--extra-ldflags="$extra_ldflags" \
${config}

#make
#make install
}



build_one armeabi
echo "---------------------------------------------------------------------------"
build_one armeabi-v7a
echo "---------------------------------------------------------------------------"
build_one x86
echo "---------------------------------------------------------------------------"
build_one x86_64
