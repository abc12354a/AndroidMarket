package com.example.abc12.navigationview;

/**
 * Created by abc12 on 2017/12/13.
 */
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

public class MyUser extends BmobUser {
    private String nickname;
    private Integer level;
    public Integer getLevel() {
        return level;
    }
    public String getNickname() {
        return nickname;
    }
    public void setLevel(Integer level) {
        this.level = level;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
