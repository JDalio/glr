//index.js
//获取应用实例
const app = getApp()
Page({
    data:
        {
            userInfo: app.globalData.userInfo,
            sessionOk: wx.getStorageSync("sessionOk"),
            canIUse: wx.canIUse("button.open-type.getUserInfo")
        },
    onLoad: function (params) {
    },
    doLogin: function (e) {//e.detail.userInfo用户公开信息
        var that = this
        wx.login({
            success: function (res) {
                var code = res.code
                //调用后端，换取session_key，secret
                wx.request({
                    url: 'http://localhost:8080/wxLogin',
                    data: {'code': code},
                    method: "POST",
                    success: function (resp) {
                        //更新app.globalData 以及 local storage
                        wx.setStorageSync("sessionkey", resp.data.data)
                        wx.setStorageSync("userInfo", e.detail.userInfo)
                        wx.setStorageSync("sessionOk", true)
                        app.globalData.userInfo = e.detail.userInfo
                        //更新当前页信息
                        that.setData({
                            "userInfo": app.globalData.userInfo,
                            "sessionOk": true,
                        })
                    }
                })
            }
        })
    },
})


// Page({
//   /**
//    * 生命周期函数--监听页面初次渲染完成
//    */
//   onReady: function () {

//   },

//   /**
//    * 生命周期函数--监听页面显示
//    */
//   onShow: function () {

//   },

//   /**
//    * 生命周期函数--监听页面隐藏
//    */
//   onHide: function () {

//   },

//   /**
//    * 生命周期函数--监听页面卸载
//    */
//   onUnload: function () {

//   },

//   /**
//    * 页面相关事件处理函数--监听用户下拉动作
//    */
//   onPullDownRefresh: function () {

//   },

//   /**
//    * 页面上拉触底事件的处理函数
//    */
//   onReachBottom: function () {

//   },

//   /**
//    * 用户点击右上角分享
//    */
//   onShareAppMessage: function () {

//   }
// })