package com.wzwl.parking.common;

import com.alibaba.fastjson.JSON;

/**
 * @ClassName ResultEntity
 * @Description 封装返回结果类
 * @Author yangwu
 * @Date 2020/10/22 18:07
 * @Version 1.0
 */
public class ResultEntity {

    /**返回码**/
    private Integer code;

    /**返回信息**/
    private String msg;

    /**返回信息说明**/
    private String msgInfo;

    /**数据**/
    private Object data;

    public boolean isSuccess(){
        return code==0;
    }

    public ResultEntity() {
    }

    public ResultEntity(ResultEnum resultEnum) {
        this.code = resultEnum.code;
        this.msg = resultEnum.msg;
    }

    public ResultEntity(ResultEnum resultEnum,String msgInfo) {
        this.code = resultEnum.code;
        this.msg = resultEnum.msg;
        this.msgInfo = msgInfo;
    }

    public Integer getCode() {
        return code;
    }

    public ResultEntity setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ResultEntity setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getData() {
        return data;
    }

    public ResultEntity setData(Object data) {
        this.data = data;
        return this;
    }

    public String getMsgInfo() {
        return msgInfo;
    }

    public void setMsgInfo(String msgInfo) {
        this.msgInfo = msgInfo;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }



}
