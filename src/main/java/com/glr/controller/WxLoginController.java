package com.glr.controller;

import com.glr.model.WxSessionModel;
import com.glr.utils.GlrJSONResult;
import com.glr.utils.HttpClientUtil;
import com.glr.utils.JsonUtil;
import com.glr.utils.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class WxLoginController
{
    @Autowired
    private RedisOperator redis;

    @RequestMapping(value = "/wxLogin", method = {RequestMethod.POST})
    public GlrJSONResult wxLogin(String code)
    {
        System.out.println("wxLogin - code: " + code);

        String url = "https://api.weixin.qq.com/sns/jscode2session";
        Map<String, String> param = new HashMap<>();
        param.put("appid", "wxe92a7efd88f51aab");
        param.put("secret", "157f18ebdc5d1d6e3794bd42161ec446");
        param.put("js_code", code);
        param.put("grant_type", "authorization_code");

        String wxres = HttpClientUtil.doGet(url, param);
        System.out.println(wxres);

        WxSessionModel model = JsonUtil.jsonToPojo(wxres, WxSessionModel.class);

        //save in reids
        redis.set("user-redis-session:" + model.getOpenid(), model.getSession_key(), 3600 * 24 * 7);

        return GlrJSONResult.ok();
    }
}
