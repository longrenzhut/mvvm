package com.zhongcai.common.widget.optaddress;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "water_address")
public class AddrItemModel {

    private int id;
    private int pid;
    private String name;
    private String mergename;
    private int level;
    private String pinyin;
    private String lng;
    private String lat;
    private int is_all_divide;
    private String create_time;
    private String update_time;
    private int delete_tag;


    @Transient //：添加此标记后不会生成数据库表的列
    private int selected = 0;


    @Generated(hash = 402124392)
    public AddrItemModel(int id, int pid, String name, String mergename, int level,
            String pinyin, String lng, String lat, int is_all_divide,
            String create_time, String update_time, int delete_tag) {
        this.id = id;
        this.pid = pid;
        this.name = name;
        this.mergename = mergename;
        this.level = level;
        this.pinyin = pinyin;
        this.lng = lng;
        this.lat = lat;
        this.is_all_divide = is_all_divide;
        this.create_time = create_time;
        this.update_time = update_time;
        this.delete_tag = delete_tag;
    }


    @Generated(hash = 1409058154)
    public AddrItemModel() {
    }


    public int getId() {
        return this.id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public int getPid() {
        return this.pid;
    }


    public void setPid(int pid) {
        this.pid = pid;
    }


    public String getName() {
        return this.name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getMergename() {
        return this.mergename;
    }


    public void setMergename(String mergename) {
        this.mergename = mergename;
    }


    public int getLevel() {
        return this.level;
    }


    public void setLevel(int level) {
        this.level = level;
    }


    public String getPinyin() {
        return this.pinyin;
    }


    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }


    public String getLng() {
        return this.lng;
    }


    public void setLng(String lng) {
        this.lng = lng;
    }


    public String getLat() {
        return this.lat;
    }


    public void setLat(String lat) {
        this.lat = lat;
    }


    public int getIs_all_divide() {
        return this.is_all_divide;
    }


    public void setIs_all_divide(int is_all_divide) {
        this.is_all_divide = is_all_divide;
    }


    public String getCreate_time() {
        return this.create_time;
    }


    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }


    public String getUpdate_time() {
        return this.update_time;
    }


    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }


    public int getDelete_tag() {
        return this.delete_tag;
    }


    public void setDelete_tag(int delete_tag) {
        this.delete_tag = delete_tag;
    }



    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }
}
