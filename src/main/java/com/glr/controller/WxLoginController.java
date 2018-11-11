package com.glr.controller;

import com.glr.model.WxSessionModel;
import com.glr.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class WxLoginController
{
    @Autowired
    private RedisOperator redis;

    @PostMapping("/wxLogin")
    public GlrJSONResult wxLogin(@RequestBody Map<String, String> login)
    {
        System.out.println("wxLogin - code: " + login.get("code"));

        String url = "https://api.weixin.qq.com/sns/jscode2session";
        Map<String, String> param = new HashMap<>();
        param.put("appid", "wxe92a7efd88f51aab");
        param.put("secret", "bd8d935cee881c3513ca0af5a329bbb6");
        param.put("js_code", login.get("code"));
        param.put("grant_type", "authorization_code");

        String wxres = HttpClientUtil.doGet(url, param);
        System.out.println(wxres);

        WxSessionModel model = JsonUtil.jsonToPojo(wxres, WxSessionModel.class);

        //save in reids
        String thirdSessionKey = null;
        try
        {
            thirdSessionKey = ThirdSessionUtil.create();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        redis.set(thirdSessionKey, model.getOpenid() + " " + model.getSession_key(), 3600 * 24 * 7);
        //TODO 将用户信息存入mysql

        return GlrJSONResult.ok(thirdSessionKey);
    }

    @PostMapping("/checkSession")
    public GlrJSONResult checkSession(@RequestBody Map<String, String> session)
    {
        String sessionKey = session.get("sessionkey");
        if (redis.get(sessionKey) != null)
        {
            redis.expire(sessionKey, 3600 * 24 * 7);
            return GlrJSONResult.build(200, "NotTimeOut", null);
        } else
        {
            return GlrJSONResult.build(200, "TimeOut", null);
        }

    }
}
