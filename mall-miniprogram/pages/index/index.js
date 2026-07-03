import {request} from '../../utils/request'

Page({
  data: {
    // 顶部导航Tab
    currentTab: "shouye",
    tabList: ["tejia", "shouye", "miaosong", "xinpin"],

    // 分类导航SubTab
    currentSubTab: "tuijian",
    subTabList: ["tuijian", "guobutie", "shipin", "nanzhuang", "nüzhuang", "shouji"],

      // 功能入口数据
        funcList: [],
  
    // 商品数据
    subsidyGoods: [
      { image: "/images/phone.png", price: 3199 },
      { image: "/images/laptop.png", price: 5040 }
    ],
    postageGoods: [
      { image: "/images/watermelon.png", price: 8.01 },
      { image: "/images/phoneshell.png", price: 7.99 }
    ],
    qualityGoods: [
      { image: "/images/bianli.png", text: "超市便利" },
      { image: "/images/yaopin.png", text: "买药秒送" },
      { image: "/images/meishi.png", text: "品质外卖" },
      { image: "/images/jijiu.png", text: "特惠机酒" }
    ],

  },
  async onLoad(options) {
    const list = await request({
      url: "/func-entries"
    })
    this.setData({ funcList: list });
  },
  // 顶部导航切换
  switchTab(e) {
    this.setData({ currentTab: e.currentTarget.dataset.tab });
  },

  // 分类导航切换
  switchSubTab(e) {
    this.setData({ currentSubTab: e.currentTarget.dataset.tab });
  },

  // 跳转搜索页
  goSearchPage() {
    wx.navigateTo({ url: '/pages/search/search' });
  },
  
});