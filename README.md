# react-native-shortcut-badge

Badge for the shortcut icon in your React Native App (Android & iOS).

In my country (México), software developers are poorly paid, so I have had to look for another job to earn a living and I cannot dedicate more time to maintaining this and other repositories that over the years have never generated any money for me. If anyone is interested in maintaining this repository, I'd be happy to transfer it to them, along with the associated npm package. |
:---: |
En mi país (México), los desarrolladores de software somos pésimamente pagados, por lo que he tenido que buscar otro trabajo para ganarme la vida y no puedo dedicar más tiempo a mantener éste y otros repositorios que a través de los años nunca me generaron dinero. Si a alguien le interesa dar mantenimiento a este repositorio, con gusto se lo transferiré, así como el paquete de npm asociado. |

iOS have native support for badges and this library uses the React Native [PushNotificationIOS](https://facebook.github.io/react-native/docs/pushnotificationios) module.

In Android, this is a wrapper for the amazing [ShortcutBadger](https://github.com/leolin310148/ShortcutBadger) library.

ShortcutBadger is included in many notification libraries for React Native, rn-shortcut-badge offers an API and updates that do not depend on any of these libraries, although it can present the same drawbacks (before Android 8 there is no native support for badges, in Android 8 and later the badge is defined by the notifications).

**NOTE**: This is Work In Progress, not fully tested.

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

ShortcutBadge.getCount((count) => {
    ShortcutBadge.setCount(count + 1);
});
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
