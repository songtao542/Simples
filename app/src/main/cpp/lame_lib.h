/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
#include <android/log.h>
/* Header for class com_song_example_jni_Lame */

#ifndef _Included_com_song_example_jni_Lame
#define _Included_com_song_example_jni_Lame

#define TAG "lame-lib" // 这个是自定义的LOG的标识
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,TAG ,__VA_ARGS__) // 定义LOGD类型
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,TAG ,__VA_ARGS__) // 定义LOGI类型
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN,TAG ,__VA_ARGS__) // 定义LOGW类型
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,TAG ,__VA_ARGS__) // 定义LOGE类型
#define LOGF(...) __android_log_print(ANDROID_LOG_FATAL,TAG ,__VA_ARGS__) // 定义LOGF类型

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Class:     com_song_example_jni_Lame
 * Method:    version
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_song_example_jni_Lame_version
        (JNIEnv *, jobject);

/*
 * Class:     com_song_example_jni_Lame
 * Method:    wavToMp3
 * Signature: (Ljava/lang/String;Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_song_example_jni_Lame_wavToMp3__Ljava_lang_String_2Ljava_lang_String_2
        (JNIEnv *, jobject, jstring, jstring);

/*
 * Class:     com_song_example_jni_Lame
 * Method:    wavToMp3
 * Signature: (Ljava/lang/String;Ljava/lang/String;Lcom/song/example/jni/ProgressListener;)V
 */
JNIEXPORT void JNICALL Java_com_song_example_jni_Lame_wavToMp3__Ljava_lang_String_2Ljava_lang_String_2Lcom_song_example_jni_ProgressListener_2
        (JNIEnv *, jobject, jstring, jstring, jobject);

#ifdef __cplusplus
}
#endif
#endif
