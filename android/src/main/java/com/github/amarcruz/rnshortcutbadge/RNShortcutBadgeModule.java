package com.github.amarcruz.rnshortcutbadge;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter;

import java.util.HashMap;
import java.util.Map;

import me.leolin.shortcutbadger.ShortcutBadger;

public class RNShortcutBadgeModule extends ReactContextBaseJavaModule {

    private static final String TAG = "RNShortcutBadge";
    private static final String BADGE_EVENT = "badge_updated";
    private static final String BADGE_FILE = "BadgeCountFile";
    private static final String BADGE_KEY = "BadgeCount";

    private ReactApplicationContext mReactContext;
    private boolean mIsDisabled = false;

    RNShortcutBadgeModule(ReactApplicationContext context) {
        super(context);
        mReactContext = context;
    }

    @Override
    public String getName() {
        return TAG;
    }

    @Override
    public void initialize() {
        getPreferences().registerOnSharedPreferenceChangeListener(listener);
    }

    @Override
    public Map<String, Object> getConstants() {
        final HashMap<String, Object> constants = new HashMap<>();

        final int counter = getPreferences().getInt(BADGE_KEY, 0);
        final boolean supported = ShortcutBadger.isBadgeCounterSupported(mReactContext);

        if (counter > 0 && supported) {
            ShortcutBadger.applyCount(getCurrentActivity(), counter);
        }

        constants.put("BADGE_EVENT", BADGE_EVENT);
        constants.put("counter", counter);
        constants.put("launcher", getLauncherName());
        constants.put("supported", supported);

        return constants;
    }

    /**
     * Get the current position. This can return almost immediately if the location is cached or
     * request an update, which might take a while.
     */
    @ReactMethod
    public void setBadge(final int badge, final Promise promise) {

        // Temporal workaround for Oreo, till SB has support for this
        if (mIsDisabled) {
            promise.resolve(false);
            return;
        }

        try {
            Context context = getCurrentActivity();
            if (context == null) {
                context = mReactContext.getApplicationContext();
            }

            boolean ok = ShortcutBadger.applyCount(context, badge);
            if (ok) {
                getPreferences().edit().putInt(BADGE_KEY, badge).apply();
                promise.resolve(true);
            } else {
                Log.d(TAG, "Cannot set badge.");
                promise.resolve(false);
            }
        } catch (Exception ex) {
            Log.e(TAG, "Cannot set badge", ex);
            promise.reject(ex);
        }
    }

    /**
     * Disable/Enable ShortcutBadger in Oreo and above.
     */
    @ReactMethod
    public void disableInOreo(final boolean on) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mIsDisabled = on;
        }
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

    private OnSharedPreferenceChangeListener listener = new OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences pref, String key) {
            RCTDeviceEventEmitter emitter = mReactContext.getJSModule(RCTDeviceEventEmitter.class);
            if (emitter == null) {
                Log.d(TAG, "Cannot get RCTDeviceEventEmitter instance.");
                return;
            }

            try {
                int count = pref.getInt(BADGE_KEY, 0);
                emitter.emit(BADGE_EVENT, count);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private SharedPreferences getPreferences() {
        return mReactContext.getSharedPreferences(BADGE_FILE, Context.MODE_PRIVATE);
    }
}
