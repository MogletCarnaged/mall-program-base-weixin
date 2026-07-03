// pages/search/search.js
Page({
    data:{
        name:""
    },
    searchChangeHandler:function(e){
        this.setData({name:e.detail});
    },
    goListPage:function(){
    wx.navigateTo({
      url: '/pages/list/list?name=' + this.data.name
    })
}
})