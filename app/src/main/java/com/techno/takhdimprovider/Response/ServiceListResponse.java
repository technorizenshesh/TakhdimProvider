package com.techno.takhdimprovider.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.techno.takhdimprovider.Result.ServiceListResult;

import java.util.List;

public class ServiceListResponse {

    @SerializedName("result")
    @Expose
    private List<ServiceListResult> result = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;

    public List<ServiceListResult> getResult() {
        return result;
    }

    public void setResult(List<ServiceListResult> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
