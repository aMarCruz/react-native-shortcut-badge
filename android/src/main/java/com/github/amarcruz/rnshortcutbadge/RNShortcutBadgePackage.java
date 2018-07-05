package com.github.amarcruz.rnshortcutbadge;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RNShortcutBadgePackage implements ReactPackage {

    public RNShortcutBadgePackage() {
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }

    @Override
    public List<NativeModule> createNativeModules(
            ReactApplicationContext reactContext) {
        List<NativeModule> modules = new ArrayList<>();
        modules.add(new RNShortcutBadgeModule(reactContext));
        return modules;
    }

    /**
     * Make happy Studio.
     */
    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return null;
    }
}
