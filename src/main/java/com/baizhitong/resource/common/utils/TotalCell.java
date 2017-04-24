package com.baizhitong.resource.common.utils;

public class TotalCell {
    private Integer positionX;
    private Integer total;

    public TotalCell(Integer positionX) {
        this.positionX = positionX;
        this.total = 0;
    };

    public Integer getPositionX() {
        return positionX;
    }

    public void setPositionX(Integer positionX) {
        this.positionX = positionX;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getTotal() {
        return total;
    }

    public void totalAdd(Integer plusNumber) {
        this.total = this.total + plusNumber;
    }

}
