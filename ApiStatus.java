package com.ideatransport.ideacabs.partner.db;

import helpers.Utils;

/**
 * Created by Harshith on 28-10-2017.
 */

public class ApiStatus {
    private String msg;
    private Boolean status;

    public ApiStatus(String msg, Boolean success) {
        this.msg = msg;
        this.status = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public static ApiStatus failedStatus()
    {
        return new ApiStatus("Failed",false);
    }
    public static ApiStatus failedStatus(Throwable t)
    {
        return new ApiStatus(Utils.getNetworkErrorText(t),false);
    }

    public static ApiStatus failedStatus(String message) {
        return new ApiStatus(message,false);
    }
    public static ApiStatus successStatus(String message) {
        return new ApiStatus(message,true);
    }

}
