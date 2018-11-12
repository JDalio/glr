package com.glr.controller;

import com.glr.model.BaseUser;
import com.glr.model.WxSessionModel;
import com.glr.repository.BaseUserRepo;
import com.glr.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class WxLoginController
{
    @Autowired
    private RedisOperator redis;

    @Autowired
    private BaseUserRepo baseUserRepo;

    @PostMapping("/wxLogin")
    public GlrJSONResult wxLogin(@RequestBody Map<String, String> login)
    {
        System.out.println(login.get("gender"));
        System.out.println(Short.valueOf(login.get("gender")));

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

        String openid = model.getOpenid();
        redis.set(thirdSessionKey, openid + " " + model.getSession_key(), 3600 * 24 * 7);

        BaseUser baseUser = new BaseUser();
        baseUser.setOpenid(openid);
        //将用户基本信息存入MySql
        if (!baseUserRepo.findById(openid).isPresent())
        {
            baseUser.setNickName(login.get("nickName"));
            baseUser.setGender(Short.valueOf(login.get("gender")));
            baseUser.setProvince(login.get("province"));
            baseUser.setCity(login.get("city"));
            baseUser.setAvatarUrl(login.get("avatarUrl"));
            baseUser.setRegisterTime(new Date());
            baseUser.setLastLoginTime(new Date());
            baseUserRepo.save(baseUser);
        } else
        {
            baseUser.setLastLoginTime(new Date());
            baseUserRepo.save(baseUser);
        }

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
        //TODO 每次登录将最近登录时间刷入redis,键是openid,值是最新的登录时间
        //TODO 设置定时器 每天3点检查最近登录时间，超过三天则计入mysql并在redis中删除

    }
}
