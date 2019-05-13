package com.example.kartcontrolremote;

import android.annotation.SuppressLint;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

class MySingleton {
    private  Context mCtx;
    @SuppressLint("StaticFieldLeak")
    private static MySingleton mInstance;
//    private ImageLoader mImageLoader = new ImageLoader(this.mRequestQueue, new C03761());
    private RequestQueue mRequestQueue = getRequestQueue();

    /* renamed from: com.example.sourabh.kartgrid.MySingleton$1 */
//    class C03761 implements ImageCache {
//        private final LruCache<String, Bitmap> cache = new LruCache<>(20);
//
//        C03761() {
//        }
//
//        public Bitmap getBitmap(String url) {
//            return this.cache.get(url);
//        }
//
//        public void putBitmap(String url, Bitmap bitmap) {
//            this.cache.put(url, bitmap);
//        }
//    }

    private MySingleton(Context context) {
        mCtx = context;
    }

    static synchronized MySingleton getInstance(Context context) {
        MySingleton mySingleton;
        synchronized (MySingleton.class) {
            if (mInstance == null) {
                mInstance = new MySingleton(context);
            }
            mySingleton = mInstance;
        }
        return mySingleton;
    }

    private RequestQueue getRequestQueue() {
        if (this.mRequestQueue == null) {
            if (mCtx != null) {
                this.mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
            }
        }
        return this.mRequestQueue;
    }

    void addToRequestQueue(Request req) {
        getRequestQueue().add(req);
    }

//    public ImageLoader getImageLoader() {
//        return this.mImageLoader;
//    }
}
