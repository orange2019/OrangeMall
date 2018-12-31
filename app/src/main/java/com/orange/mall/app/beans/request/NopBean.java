package com.orange.mall.app.beans.request;

import com.google.gson.annotations.SerializedName;

public class NopBean {
    public NopBean() {
      nop = "";
    }

    @SerializedName("nop")
    private String nop = "";
}
