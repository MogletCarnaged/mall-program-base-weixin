// 我其实还是调用wx.request来发送ajax,我要帮助原来调用wx.request发送ajax的地方简化代码
// 原来调用wx.request要传的参数，必传，传给我我帮你伟，相同的不用传，我这里给你传
export const request = function(userConfig) {
    userConfig.url = "http://localhost:8082/api/v1" + userConfig.url;
    userConfig.method = userConfig.method || 'GET';
    return new Promise((resolve, reject) => {
        wx.request({
            ...userConfig,
            success: (result) => {
                if(result.statusCode === 200) {
                    const code = result.data.code;
                    const msg = result.data.msg;
                    const data = result.data.data;
                    if(code === 200) {
                        resolve(data);    
                    }
                } else {
                    // 请求失败（）
                    console.log('statusCode不为200')
                    reject(result.errMsg);
                }
            },
            fail: (error) => {
                 // 严重失败（URL写错了）
                 console.log("fail");
                 console.log(error);
                 reject(error.message);
            },
            complete: () => {
                console.log("complete");
            }
        })
    })
}