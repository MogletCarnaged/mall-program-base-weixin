import {request} from '../../utils/request.js';
Page({
  data:{
    active:0,
    categoryList:[],
    series:[]
  },
  onReady:async function(){
    const categoryList=await request({url:"/categories"});
    this.setData({categoryList:categoryList});
    this.toggleId(categoryList[0].id);
  },
  tapHandler:function(e){
    this.toggleId(e.currentTarget.dataset.id)
  },
 toggleId: async function(newId)
 {
   if(newId===this.data.activeId){return;}
   this.setData({activeId:newId});
   const productList =await request({url:"/products?categoryId=" + newId});
      const series =productList.reduce((result,product)=>{
        let seriesName =product.series;
        let i= result.findIndex(item => item.seriesName === seriesName);
        if(i === -1){
        result.push({seriesName:seriesName, list:[{...product}]})} 
        else {
        result[i].list.push({...product})
        }
        return result;
      },[]);
        this.setData({ series: series});
 },
 goSearchPage:function(){
  wx.navigateTo({
    url: '/pages/search/search',
  })
}
})
