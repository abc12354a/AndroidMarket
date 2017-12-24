package com.example.abc12.navigationview;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by abc12 on 2017/12/22.
 */

public class item_net extends BmobObject {
    private BmobFile PicFile;
    private String name;
    private String describe;
    private Double price;

    public BmobFile getPicFile() {
        return PicFile;
    }

    public void setPicFile(BmobFile picFile) {
        PicFile = picFile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
