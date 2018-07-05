/**
 * Copyright (c) 2015-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 *
 * @providesModule Settings
 * @flow
 */
'use strict';

import { DeviceEventEmitter, NativeModules } from 'react-native'

const RNShortcutBadge = NativeModules.RNShortcutBadge

const Settings = {
  counter: RNShortcutBadge.counter,
}

/**
 * Track counter value.
 */
DeviceEventEmitter.addListener(
  RNShortcutBadge.BADGE_EVENT,
  (counter) => Settings.counter = counter
)

export default {

  get launcher () {
    return RNShortcutBadge.launcher
  },

  get supported () {
    return RNShortcutBadge.supported
  },

  disableInOreo: RNShortcutBadge.disableInOreo,

  getCount () {
    return Settings.counter
  },

  setCount (counter) {
    return RNShortcutBadge.setBadge(counter | 0)
  },
}
