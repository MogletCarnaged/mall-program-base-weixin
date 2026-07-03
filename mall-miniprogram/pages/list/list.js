// pages/list/list.js
import {request} from'../../utils/request'
Page({
  data: {
        name:"",//保存用户输入的关键字
        list:[],//当前页面要显示的数据
        page:1,
        limit:6,
        hasMore:true,
        loading:false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
        this.setData({name:options.name});
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  async onReady() {
   this.updateList(1);
  },
  
  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom() {
       this.updateList(this.data.page+1);
  },
  async updateList(newPage){
        if(this.data.loading){
            return;
        }
        if(this.data.hasMore===false){return;}
            const url=`/products?name=${this.data.name}&page=${newPage}&limit=${this.data.limit}`;
            this.setData({loading:true});
            const list= await request({url:url});
            await new Promise(resolve=>setTimeout(resolve,2000));
            this.setData({list:[...this.data.list,...list],
            hasMore:list.length===this.data.limit,
            loading:false,
            page:newPage});
  }
})