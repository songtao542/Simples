#include <jni.h>
#include <string>
#include "lame/lame.h"
#include "lame_lib.h"

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jstring JNICALL Java_com_song_example_jni_JNITestActivity_stringFromJNI(
        JNIEnv *env, jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(get_lame_version());
}

JNIEXPORT jstring JNICALL Java_com_song_example_jni_JNITestActivity_wavToMp3(
        JNIEnv *env, jobject obj, jstring wav_path, jstring mp3_path) {
    jboolean iscopy1 = JNI_FALSE;
    std::string wav = env->GetStringUTFChars(wav_path,&iscopy1);
    jboolean iscopy2 = JNI_FALSE;
    std::string mp3 = env->GetStringUTFChars(mp3_path,&iscopy2);
    LOGD(iscopy1 == JNI_FALSE ? "JNI_FALSE" : "JNI_TRUE");
    LOGD(iscopy2 == JNI_FALSE ? "JNI_FALSE" : "JNI_TRUE");
    LOGD("%s",wav.c_str());
    LOGD("%s",mp3.c_str());
    return env->NewStringUTF(get_lame_version());
}


#ifdef __cplusplus
}
#endif