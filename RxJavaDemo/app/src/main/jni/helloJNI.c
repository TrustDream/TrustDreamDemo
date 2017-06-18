#include "com_trust_rxjavademo_ndk_JniTest.h"
#include <stdio.h>
#include <string.h>
#include <android/log.h>

#define   TAG "Lhh_ndk"

#define LOGI(...)  __android_log_print(ANDROID_LOG_INFO, TAG, __VA_ARGS__)
#define LOGE(...)  __android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__)
#define LOGI(...)  __android_log_print(ANDROID_LOG_INFO, TAG, __VA_ARGS__)
#define LOGV(...)  __android_log_print(ANDROID_LOG_VERBOSE, TAG, __VA_ARGS__)

/**
*   从native 层返回指定的字符串
*/
JNIEXPORT jstring JNICALL Java_com_trust_rxjavademo_ndk_JniTest_getString
  (JNIEnv * env, jobject jobject1){
  LOGV("log ndk");
  return (*env)->NewStringUTF(env,"hello jni!  ");
  }

/**
*   想文件中写入一段字符串
*/
JNIEXPORT void JNICALL Java_com_trust_rxjavademo_ndk_JniTest_updateFile
    (JNIEnv *env, jobject jclas, jstring path){
        //生成native层的char指针
        const char *file_path = (* env)->GetStringUTFChars(env,path,NULL);
        if(file_path != NULL){
            LOGV("updateFile flie_path %s",file_path);
        }
        //打开文件
        FILE* file = fopen(file_path,"a+");
        if(file != NULL){
            LOGV("open file success  ");
        }
        //把数据写入文件
        char data[] ="I'm TrustTinker";
        int count = fwrite(data,strlen(data),1,file);//写一次

        if(count >0){
            LOGV("write file success");
        }
        //关闭文件
        if(file != NULL){
            fclose(file);
        }
        //释放资源
        (*env)->ReleaseStringUTFChars(env,path,file_path);

    }

JNIEXPORT jintArray JNICALL Java_com_trust_rxjavademo_ndk_JniTest_updateIntArray
  (JNIEnv *env, jobject jclass, jintArray array){
       jint nativeArray[5];
       (*env)->GetIntArrayRegion(env,array,0,5,nativeArray);

       int i ;
       for(i = 0;i<5;i++){
            nativeArray[i]+=5;
            LOGV("from ndk int%d",nativeArray[i]);
       }

       (*env)->SetIntArrayRegion(env,array,0,5,nativeArray);

       return array;
  }