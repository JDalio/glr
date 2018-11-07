package com.glr.model;

public class WxSessionModel
{
    private String session_key;
    private String expires_in;
    private String openid;

    public String getExpires_in()
    {
        return expires_in;
    }

    public void setExpires_in(String expires_in)
    {
        this.expires_in = expires_in;
    }

    public String getSession_key()
    {
        return session_key;
    }

    public void setSession_key(String sessionKey)
    {
        this.session_key = sessionKey;
    }

    public String getOpenid()
    {
        return openid;
    }

    public void setOpenid(String openid)
    {
        this.openid = openid;
    }
}
