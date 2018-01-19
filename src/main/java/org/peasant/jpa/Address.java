/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.jpa;

import java.io.Serializable;

/**
 *
 * @author raymond
 */
@javax.persistence.Embeddable
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    protected String province;
    protected String city;
    protected String district;
    protected String address;
    protected String zipCode;

    /**
     * 邮政编码
     *
     * @return
     */
    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     *
     * @return 省份
     */
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    /**
     *
     * @return 城市
     */
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    /**
     *
     * @return 区县
     */
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    /**
     *
     * @return 详细地址
     */
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
