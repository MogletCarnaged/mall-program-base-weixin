// 把wx上的要用到的方法做一个语法转变，用起来更加简洁，支持async/await
export function wxLogin() {
  return new Promise((resolve, reject) => {
      return wx.login({
          success: resolve,
          fail: reject
      });
  })
}