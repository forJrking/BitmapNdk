#include <jni.h>
#include <android/bitmap.h>
#include <android/log.h>

extern "C"
JNIEXPORT jobject JNICALL
Java_com_full_ndkdemo_BitmapFilter_gray(JNIEnv *env, jclass clazz, jobject bitmap) {
    //构造 AndroidBitmapInfo
    AndroidBitmapInfo info;
    //将 bitmp 的信息填充给 info
    int getInfo = AndroidBitmap_getInfo(env, bitmap, &info);
    if (getInfo != 0) {
        return bitmap;
    }

    void *pixels;
    AndroidBitmap_lockPixels(env, bitmap, &pixels);

    if (info.format == ANDROID_BITMAP_FORMAT_RGBA_8888) {
        for (int i = 0; i < info.width * info.height; i++) {
            uint32_t *pixel_p = static_cast<uint32_t *>(pixels) + i;
            uint32_t pixel = *pixel_p;
            int a = (pixel >> 24) & 0xff;
            int r = (pixel >> 16) & 0xff;
            int g = (pixel >> 8) & 0xff;
            int b = pixel & 0xff;
            // f = 0.213f * r + 0.715f * g + 0.072f * b
            int gery = (int) (0.213f * r + 0.715f * g + 0.072f * b);
            *pixel_p = (a << 24) | (gery << 16) | (gery << 8) | gery;
        }
    } else if (info.format == ANDROID_BITMAP_FORMAT_RGB_565) {
        for (int i = 0; i < info.width * info.height; ++i) {
            uint16_t *pixel_p = reinterpret_cast<uint16_t *>(pixels) + i;
            uint16_t pixel = *pixel_p;
            //5 0001 1111->1f
            //6 0011 1111->3f
            int r = ((pixel >> 11) & 0x1f) << 3;//8->5
            int g = ((pixel >> 5) & 0x3f) << 2;//8->6
            int b = (pixel & 0x1f) << 3;//8->5
            // f = 0.213f * r + 0.715f * g + 0.072f * b
            int gery = (int) (0.213f * r + 0.715f * g + 0.072f * b);
            //重新设置回去
            *pixel_p = ((gery >> 3) << 11) | ((gery >> 2) << 5) | (gery >> 3);
        }
    }
    //释放锁定，显示出被修改的像素数据
    AndroidBitmap_unlockPixels(env, bitmap);

    return bitmap;
}