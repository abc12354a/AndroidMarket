package com.example.abc12.navigationview;

/**
 * Created by abc12 on 2017/12/20.
 */

public class order_item {
    private String id;
    private String name;
    private String kuaidi;

    public order_item(String id,String name,String kuaidi) {
        this.id = id;
        this.name = name;
        this.kuaidi = kuaidi;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKuaidi(String kuaidi) {
        this.kuaidi = kuaidi;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getKuaidi() {
        return kuaidi;
    }
}
