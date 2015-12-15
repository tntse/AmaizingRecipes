package com.taeyeona.amaizingunicornrecipes;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Chau on 9/27/2015.
 */

/**
 * VolleySingleton class to queue network calls that use volley.
 *
 * Used from https://developer.android.com/training/volley/requestqueue.html
 */
public class VolleySingleton {
    private static VolleySingleton volleyRequest;
    private RequestQueue volleyRequestQueue;
    private ImageLoader volleyImageLoader;
    private static Context context;

    @TargetApi(12)
    private VolleySingleton(Context context) {
        this.context = context;
        volleyRequestQueue = getRequestQueue();

        volleyImageLoader = new ImageLoader(volleyRequestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    /**
     *
     * @param context The context from the fragment/activity that called this method
     * @return The Singleton
     */
    public static synchronized VolleySingleton getInstance(Context context) {
        if (volleyRequest == null) {
            volleyRequest = new VolleySingleton(context);
        }
        return volleyRequest;
    }

    /**
     *
     * @return The request queue to enqueue in the tasks
     */
    public RequestQueue getRequestQueue() {
        if (volleyRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            volleyRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return volleyRequestQueue;
    }

    /**
     *
     * @param req The request, with a generic typing, to do the task
     * @param <T> A generic type to allow adding in different types of requests
     */
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    /**
     *
     * @return The ImageLoader
     */
    public ImageLoader getImageLoader() {
        return volleyImageLoader;
    }
}
