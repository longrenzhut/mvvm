package com.zhongcai.common.ui.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class UserModel {
    private String name;
    private String sex;
    @Generated(hash = 242953461)
    public UserModel(String name, String sex) {
        this.name = name;
        this.sex = sex;
    }
    @Generated(hash = 782181818)
    public UserModel() {
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
}
