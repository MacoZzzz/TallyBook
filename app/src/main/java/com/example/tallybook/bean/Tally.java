package com.example.tallybook.bean;

import java.io.Serializable;

/**
 * 记账记录
 */
public class Tally implements Serializable {
    private Integer id;
    private Integer typeId;//类型（0收入1支出）
    private String money;//金额
    private String remark;//备注
    private String date;//时间

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Tally(Integer id, Integer typeId, String money, String remark, String date) {
        this.id = id;
        this.typeId = typeId;
        this.money = money;
        this.remark = remark;
        this.date = date;
    }
}
