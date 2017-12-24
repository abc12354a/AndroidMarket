package com.example.abc12.navigationview;

import android.support.annotation.Nullable;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by abc12 on 2017/12/11.
 */

public class item extends DataSupport{
    private String name;
    private String price;
    private String imageid;
    private Integer count;
    item(String name,String price,String imageid) {
        this.name = name;
        this.price = price;
        this.imageid = imageid;
    }
    item(){}

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    public String getImageid(){
        return this.imageid;
    }

    public void setImageid(String imageid) {
        this.imageid = imageid;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
