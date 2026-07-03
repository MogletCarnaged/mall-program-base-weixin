import { wxLogin } from '../../utils/wxUtils'
import { request } from '../../utils/request'

Page({
  data: {
      newUser: false,
      nickname: "",
      loading: false,
      checked: false,
      nicknameErroeMessage: ""
  },
  onLoad(options) {
    if(wx.getStorageSync('token')) {
      wx.switchTab({ url: "/pages/index/index"});
    }
  },
  onCheckedChange: function() {
    this.setData({ checked: !this.data.checked });
  },
  onNicknameChange: function(e) {
    this.setData({
      nickname: e.detail,
      nicknameErrorMessage: e.detail.trim() === "" ? "注册/登录必须填写用户名" : ""
    })
  },
  onLogin: async function() {
    if(this.data.newUser && this.data.nickname.trim() === "") {
      this.setData({ nicknameErrorMessage: "注册/登录必须填写用户名"})
      return ;
    }
    this.setData({ loading: true });
    try {
      const { code } = await wxLogin();
      const data = { code: code };
      if(this.data.newUser) {data.nickname = this.data.nickname };
      const { token, user } = await request({
        method: "POST",
        url: "/users/login",
        data: data
      });
      if(token) {
        wx.setStorageSync('token', token);
        wx.setStorageSync('nickname', user.nickname);
        wx.setStorageSync('openid', user.openid);
        wx.switchTab({ url: "/pages/index/index" });
      } else {
        this.setData({ newUser: true })
      }
    } catch(e) {
      console.log(e);
    } finally {
      this.setData({ loading: false });
    }
  }
})