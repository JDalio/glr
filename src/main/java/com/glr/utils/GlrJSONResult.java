package com.glr.utils;


/**
 * @Description: 自定义响应数据结构
 * 这个类是提供给门户，ios，安卓，微信商城用的
 * 门户接受此类数据后需要使用本类的方法转换成对于的数据类型格式（类，或者list）
 * 其他自行处理
 * 200：表示成功
 * 500：表示错误，错误信息在msg字段中
 * 501：bean验证错误，不管多少个错误都以map形式返回
 * 502：拦截器拦截到用户token出错
 * 555：异常抛出信息
 */

import org.omg.CORBA.Object;

public class GlrJSONResult
{
    private int status;
    private String msg;
    private Object data;

    public GlrJSONResult(Object data)
    {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    public GlrJSONResult(int status, String msg, Object data)
    {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public static GlrJSONResult build(int status, String msg, Object data)
    {
        return new GlrJSONResult(status, msg, data);
    }

    public static GlrJSONResult ok()
    {
        return new GlrJSONResult(null);
    }

    public static GlrJSONResult ok(Object data)
    {
        return new GlrJSONResult(data);
    }

    public static GlrJSONResult errorMsg(String msg)
    {
        return new GlrJSONResult(500, msg, null);
    }

    public static GlrJSONResult errorMap(Object data)
    {
        return new GlrJSONResult(501, "error", data);
    }

    public static GlrJSONResult errorTokenMsg(String msg)
    {
        return new GlrJSONResult(502, msg, null);
    }

    public static GlrJSONResult errorException(String msg)
    {
        return new GlrJSONResult(555, msg, null);
    }

    public Boolean isOK()
    {
        return this.status == 200;
    }

}
