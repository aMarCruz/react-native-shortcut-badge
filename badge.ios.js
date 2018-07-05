export default {
  launcher: null,
  supported: false,

  disableInOreo () {
  },
  setCount () {
    return Promise.resolve(false)
  },
  getCount () {
    return 0
  },
}
