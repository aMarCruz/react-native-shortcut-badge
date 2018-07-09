# react-native-shortcut-badge

Badge for the shortcut icon in your React Native Android Apps.

Wrapper for the amazing [ShortcutBadger](https://github.com/leolin310148/ShortcutBadger) library.

**NOTE**: This is Work In Progress, not fully tested.

ShortcutBadger is included in many notification libraries for React Native, this library offers an API and updates that do not depend on any of these libraries.

## Setup

```bash
$ yarn add react-native-shortcut-badge
$ react-native link react-native-shortcut-badge
```

If you are using [Proguard](https://stuff.mit.edu/afs/sipb/project/android/sdk/android-sdk-linux/tools/proguard/docs/), add this to your android/app/proguard-rules.pro

See https://github.com/leolin310148/ShortcutBadger/issues/46

```
-keep class me.leolin.shortcutbadger.impl.** { <init>(...); }
```

## Usage

```js
import ShortcutBadge from 'react-native-shortcut-badge';

const count = ShortcutBadge.getCount();
ShortcutBadge.setCount(count + 1);
```

### Properties

Property | Type | Description
-------- | ---- | -----------
launcher | `string` or `null` | Name of the current launcher "home", or `null` if the launcher could not be detected.
supported | `boolean` | Does ShortcutBadge have support for the current launcher/device?

**NOTE:** The counter remains in the storage of the device even if the launcher is not supported.

### Methods

- `setCount(count: number) => Promise<boolean>`

    Set the counter and update the badge.

    Returns `true` if the operation succeeds.

- `getCount() => Promise<number>`

    Returns the badge counter.

- `requestPermission(): Promise<boolean>`

    Request permission for Badge, mainly for iOS.

    Return `true` if permission is granted.
