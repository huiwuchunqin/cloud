package com.baizhitong.resource.model.demo;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 测试模型 目前只有该类，涉及到的字段类型进行过测试，其他字段没有进行过相关测试
 * 
 * @author cuidc 2015/12/01
 */
@Entity
@Table(name = "demo")
public class Demo implements Serializable {
    private static final long                                       serialVersionUID = -7503794864489170322L;

    /** 资源ID */
    private @Id @Column(insertable = false,
                        updatable = false) Long                     id;

    private @Id Integer                                             id2;

    /** 资源编码 */
    private String                                                  code;
    /** 资源名称 */
    private String                                                  name;
    /** 修改时间 */
    private Timestamp                                               updateTime;

    // private Date date;

    private Long                                                    long0;

    private Integer                                                 integer0;

    private Double                                                  double0;

    private Float                                                   float0;

    private Status                                                  status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @author xuaihua
     */
    public enum Status {
        enabled, disabled
    }

    public Double getDouble0() {
        return double0;
    }

    public void setDouble0(Double double0) {
        this.double0 = double0;
    }

    public Integer getInteger0() {
        return integer0;
    }

    public void setInteger0(Integer integer0) {
        this.integer0 = integer0;
    }

    public Float getFloat0() {
        return float0;
    }

    public void setFloat0(Float float0) {
        this.float0 = float0;
    }

    public Long getLong0() {
        return long0;
    }

    public void setLong0(Long long0) {
        this.long0 = long0;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getId2() {
        return id2;
    }

    public void setId2(Integer id2) {
        this.id2 = id2;
    }

}
