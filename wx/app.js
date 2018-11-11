//app.js
var config = {
    onLaunch: function () {
        //查询本地数据库，初始化用户信息
        try {
            var sessionkey = wx.getStorageSync('sessionkey')
            if (sessionkey) {
                console.log("sessionkey: " + sessionkey)
                wx.request({
                    url: 'http://localhost:8080/checkSession',
                    method: "POST",
                    data: {"sessionkey": sessionkey},
                    success(resp) {
                        if (resp.data.msg == "NotTimeOut")//session未过期
                        {

                        }
                        else if (resp.data.msg == "TimeOut")//session过期
                        {
                            wx.setStorageSync("sessionOk", false)
                            wx.setStorageSync("sessionKey", "")
                        }
                    }
                })
            }
            else//本地数据库没有sessionkey，新用户
            {
                console.log("*****new user, no sessionkey*****")
            }
        }
        catch (e) {
            //读取数据库出错
        }

    },
    globalData:
        {
            userInfo: wx.getStorageSync('userInfo'),
        }
}
App(config)