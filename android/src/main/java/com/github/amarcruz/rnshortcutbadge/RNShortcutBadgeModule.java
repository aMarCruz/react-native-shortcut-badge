package com.github.amarcruz.rnshortcutbadge;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.content.SharedPreferences;
import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.HashMap;
import java.util.Map;

import me.leolin.shortcutbadger.ShortcutBadger;

public class RNShortcutBadgeModule extends ReactContextBaseJavaModule {

    private static final String TAG = "RNShortcutBadge";
    private static final String BADGE_FILE = "BadgeCountFile";
    private static final String BADGE_KEY = "BadgeCount";

    private NotificationManager mNotificationManager;
    private static int mNotificationId = 0;

    private ReactApplicationContext mReactContext;
    private SharedPreferences mPrefs;
    private boolean mSupported = false;
    private boolean mIsXiaomi = false;

    RNShortcutBadgeModule(ReactApplicationContext reactContext) {
        super(reactContext);

        mReactContext = reactContext;
        mPrefs = reactContext.getSharedPreferences(BADGE_FILE, Context.MODE_PRIVATE);
    }

    @Override
    public String getName() {
        return TAG;
    }


    @Override
    public Map<String, Object> getConstants() {
        final HashMap<String, Object> constants = new HashMap<>();
        boolean supported = false;

        try {
            Context context = getCurrentActivity();
            if (context == null) {
                context = mReactContext.getApplicationContext();
            }
            int counter = mPrefs.getInt(BADGE_KEY, 0);
            supported = ShortcutBadger.applyCount(context, counter);

            if (!supported && Build.MANUFACTURER.equalsIgnoreCase("Xiaomi")) {
                supported = true;
                mIsXiaomi = true;
                mNotificationManager = (NotificationManager)
                        mReactContext.getSystemService(Context.NOTIFICATION_SERVICE);
            }
        } catch (Exception e) {
            Log.e(TAG, "Cannot initialize ShortcutBadger", e);
        }

        mSupported = supported;

        constants.put("launcher", getLauncherName());
        constants.put("supported", supported);

        return constants;
    }

    /**
     * Get the current position. This can return almost immediately if the location is cached or
     * request an update, which might take a while.
     */
    @ReactMethod
    public void setCount(final int count, final Promise promise) {
        try {
            // Save the counter unconditionally
            mPrefs.edit().putInt(BADGE_KEY, count).apply();

            if (!mSupported) {
                promise.resolve(false);
                return;
            }

            Context context = getCurrentActivity();
            if (context == null) {
                context = mReactContext.getApplicationContext();
            }
            boolean ok;

            if (mIsXiaomi) {
                ok = setXiaomiBadge(context, count);
            } else {
                ok = ShortcutBadger.applyCount(context, count);
            }

            if (ok) {
                promise.resolve(true);
            } else {
                Log.d(TAG, "Cannot set badge.");
                promise.resolve(false);
            }
        } catch (Exception ex) {
            Log.e(TAG, "Error setting the badge", ex);
            promise.reject(ex);
        }
    }

    /**
     * Get the badge from the storage.
     */
    @ReactMethod
    public void getCount(final Promise promise) {
        promise.resolve(mPrefs.getInt(BADGE_KEY, 0));
    }

    /**
     * Dummy method to request permissions in Android.
     */
    @ReactMethod
    public void requestPermission(final Promise promise) {
        promise.resolve(true);
    }

    /**
     * Support Xiaomi devices.
     */
    private boolean setXiaomiBadge(final Context context, final int count) {

        mNotificationManager.cancel(mNotificationId);
        mNotificationId++;

        Notification.Builder builder = new Notification.Builder(context)
                .setContentTitle("")
                .setContentText("");
        //.setSmallIcon(R.drawable.ic_launcher);
        Notification notification = builder.build();
        ShortcutBadger.applyNotification(context, notification, count);
        mNotificationManager.notify(mNotificationId, notification);

        return true;
    }

    /**
     * Find the package name of the current launcher
     */
    private String getLauncherName() {
        String name = null;

        try {
            final Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            final ResolveInfo resolveInfo = mReactContext.getPackageManager()
                    .resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
            name = resolveInfo.activityInfo.packageName;
        } catch (Exception ignore) {
        }

        return name;
    }
}
