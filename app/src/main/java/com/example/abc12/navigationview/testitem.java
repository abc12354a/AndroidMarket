package com.example.abc12.navigationview;

/**
 * Created by abc12 on 2017/12/12.
 */
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;

public class testitem extends BmobObject{
    private String name;
    private String price;
    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
