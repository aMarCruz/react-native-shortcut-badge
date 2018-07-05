# react-native-shortcut-badge

Badge for the shortcut icon in your React Native Android Apps

Simple wrapper for the amazing [ShortcutBadger](https://github.com/leolin310148/ShortcutBadger) library.

**NOTE**: This is Work In Progress, not ready for production.


## Setup

```bash
$ yarn add react-native-shortcut-badge
$ react-native link react-native-shortcut-badge
```

If using Proguard, add this to your android/app/proguard-rules.pro
See  https://github.com/leolin310148/ShortcutBadger/issues/46

```
-keep class me.leolin.shortcutbadger.impl.AdwHomeBadger { <init>(...); }
```

If you need Xiaomi support, add this inside the `application` tag of your android/src/main/AndroidManifest.xml:

```xml
    <application
        ...>

        <service
            android:name=".BadgeIntentService"
            android:exported="false" />
    </application>
```
