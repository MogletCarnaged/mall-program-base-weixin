import { wxLogin } from '../../utils/wxUtils'
import { request } from '../../utils/request'

Page({
  data: {
    nickname: "",
    services: [],
  },
  async onLoad(options) {
    try {
      const { code } = await wxLogin();
      const data = { code: code };
      const { token, user } = await request({
        method: "POST",
        url: "/users/login",
        data: data
      });
      this.setData({ nickname: user.nickname })
      const serviceList = await request({ url: "/services"});
      const services = serviceList.reduce((result, service) => {
        let serviceName = service.series;
        let i = result.findIndex(item => item.serviceName === serviceName);
        if(i === -1) {
            result.push({serviceName: serviceName, list: [{...service}]})
        } else {
            result[i].list.push({...service})
        }
        return result;
    }, []);
    this.setData({services: services})
    } catch(e) {
      console.log(e);
    } 

  },
})