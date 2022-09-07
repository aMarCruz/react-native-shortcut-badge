import PushNotificationIOS from '@react-native-community/push-notification-ios'

export default {
  launcher: null,
  supported: true,

  setCount (count) {
    // check permission here?
    PushNotificationIOS.setApplicationIconBadgeNumber(count)
    return Promise.resolve(true)
  },

  getCount () {
    return new Promise((resolve) => {
      PushNotificationIOS.getApplicationIconBadgeNumber(resolve)
    })
  },

  requestPermission () {
    return PushNotificationIOS.requestPermissions({ badge: true })
      .then((permissions) => permissions.badge)
  }
}
