#include <jni.h>
#include <string>
#include "lame/lame.h"
#include "lame_lib.h"
#include <sys/stat.h>

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jstring JNICALL Java_com_song_example_jni_Lame_version(
        JNIEnv *env, jobject /* this */) {
    std::string hello = "Hello from C++";
    hello += " this is append part.";
    return env->NewStringUTF(get_lame_version());
}

int file_size(const char *filename) {
    struct stat stat_buf;
    stat(filename, &stat_buf);
    int size = stat_buf.st_size;
    return size;
}
void convert_wav_2_mp3(const char *wav, const char *mp3, void (*progress)(float)) {
    FILE *fwav = fopen(wav, "rb");
    FILE *fmp3 = fopen(mp3, "wb");
    int total_size = file_size(wav);
    LOGD("total_size: %d", total_size);
//    //1.初始化lame的编码器
//    lame_t lame =  lame_init();
//    //2. 设置lame mp3编码的采样率
//    lame_set_in_samplerate(lame , 44100);
//    lame_set_num_channels(lame,2);
//    // 3. 设置MP3的编码方式
//    lame_set_VBR(lame, vbr_default);
//    lame_init_params(lame);

    short int wav_buffer[8192 * 2];
    unsigned char mp3_buffer[8192];

    lame_t lame = lame_init();
    lame_set_in_samplerate(lame, 44100);
    lame_set_num_channels(lame, 2);
    lame_set_VBR(lame, vbr_default);
    lame_init_params(lame);

//    LOGI("lame init finish");
//    int read ; int write; //代表读了多少个次 和写了多少次
//    int total=0; // 当前读的wav文件的byte数目
//    do{
//        if(flag==404){
//            return;
//        }
//        read = fread(wav_buffer,sizeof(short int)*2, 8192,fwav);
//        total +=  read* sizeof(short int)*2;
//        LOGI("converting ....%d", total);
//        publishJavaProgress(env,obj,total);
//        // 调用java代码 完成进度条的更新
//        if(read!=0){
//            write = lame_encode_buffer_interleaved(lame,wav_buffer,read,mp3_buffer,8192);
//            //把转化后的mp3数据写到文件里
//            fwrite(mp3_buffer,sizeof(unsigned char),write,fmp3);
//        }
//        if(read==0){
//            lame_encode_flush(lame,mp3_buffer,8192);
//        }
//    }while(read!=0);
//    LOGI("convert  finish");
//
//    lame_close(lame);
//    fclose(fwav);
//    fclose(fmp3);

    LOGD("lame init finish");
    int read, write, total = 0;
    int flag = 0;;
    do {
        if (flag == 404) {
            return;
        }
        read = fread(wav_buffer, sizeof(short int) * 2, 8192, fwav);
        total += read * sizeof(short int) * 2;
        LOGD("converting......%d", total);
        progress(total * 1.0f / total_size);
        if (read != 0) {
            write = lame_encode_buffer_interleaved(lame, wav_buffer, read, mp3_buffer, 8192);
            fwrite(mp3_buffer, sizeof(unsigned char), write, fmp3);
        }
        if (read == 0) {
            lame_encode_flush(lame, mp3_buffer, 8192);
        }
    } while (read != 0);
    LOGD("convert finish");
    lame_close(lame);
    fclose(fwav);
    fclose(fmp3);
}

JNIEnv *global_env;
//jobject global_obj;
jclass clazz;
jmethodID method_id;
jobject global_listener;

void publish_progress_2_java(float progress) {
    LOGD("convert percent: %f", progress);

    if (global_env != NULL && global_listener != NULL) {
        if (clazz == NULL) {
            clazz = global_env->FindClass("com/song/example/jni/ProgressListener");
        }
        if (method_id == NULL) {
            method_id = global_env->GetMethodID(clazz, "onProgress", "(F)V");
        }
        global_env->CallVoidMethod(global_listener, method_id, progress);
    }
}


JNIEXPORT void JNICALL
Java_com_song_example_jni_Lame_wavToMp3__Ljava_lang_String_2Ljava_lang_String_2(
        JNIEnv *env, jobject obj, jstring wav_path, jstring mp3_path) {
    jboolean iscopy1 = JNI_FALSE;
    //std::string wav = env->GetStringUTFChars(wav_path,&iscopy1);
    const char *wav = env->GetStringUTFChars(wav_path, &iscopy1);
    jboolean iscopy2 = JNI_FALSE;
    //std::string mp3 = env->GetStringUTFChars(mp3_path,&iscopy2);
    const char *mp3 = env->GetStringUTFChars(mp3_path, &iscopy2);
    LOGD(iscopy1 == JNI_FALSE ? "JNI_FALSE" : "JNI_TRUE");
    LOGD(iscopy2 == JNI_FALSE ? "JNI_FALSE" : "JNI_TRUE");
//    LOGD("wav=%s",wav.c_str());
//    LOGD("mp3=%s",mp3.c_str());
    LOGD("wav=%s", wav);
    LOGD("mp3=%s", mp3);
    convert_wav_2_mp3(wav, mp3, publish_progress_2_java);
}

JNIEXPORT void JNICALL
Java_com_song_example_jni_Lame_wavToMp3__Ljava_lang_String_2Ljava_lang_String_2Lcom_song_example_jni_ProgressListener_2(
        JNIEnv *env, jobject obj, jstring wav_path,
        jstring mp3_path, jobject listener) {
    const char *wav = env->GetStringUTFChars(wav_path, NULL);
    const char *mp3 = env->GetStringUTFChars(mp3_path, NULL);
    LOGD("wav=%s", wav);
    LOGD("mp3=%s", mp3);

    jclass clazz;
    jmethodID method_id;

    clazz = env->FindClass("com/song/example/jni/ProgressListener");
    method_id = env->GetMethodID(clazz, "onProgress", "(F)V");

    FILE *fwav = fopen(wav, "rb");
    FILE *fmp3 = fopen(mp3, "wb");
    int total_size = file_size(wav);
    LOGD("total_size: %d", total_size);

    short int wav_buffer[8192 * 2];
    unsigned char mp3_buffer[8192];

    lame_t lame = lame_init();
    lame_set_in_samplerate(lame, 44100);
    lame_set_num_channels(lame, 2);
    lame_set_VBR(lame, vbr_default);
    lame_init_params(lame);

    LOGD("lame init finish");
    int read, write, total = 0;
    int flag = 0;;
    do {
        if (flag == 404) {
            return;
        }
        read = fread(wav_buffer, sizeof(short int) * 2, 8192, fwav);
        total += read * sizeof(short int) * 2;
        LOGD("converting......%d", total);
        env->CallVoidMethod(listener, method_id, total * 1.0f / total_size);
        if (read != 0) {
            write = lame_encode_buffer_interleaved(lame, wav_buffer, read, mp3_buffer, 8192);
            fwrite(mp3_buffer, sizeof(unsigned char), write, fmp3);
        }
        if (read == 0) {
            lame_encode_flush(lame, mp3_buffer, 8192);
        }
    } while (read != 0);
    LOGD("convert finish");
    env->CallVoidMethod(listener, method_id, 1.0f);
    lame_close(lame);
    fclose(fwav);
    fclose(fmp3);
}


#ifdef __cplusplus
}
#endif