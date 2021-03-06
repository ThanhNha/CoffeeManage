package com.example.huynhthanhnha.myapplication.form;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by NguyenThanh on 12/11/2015.
 */
public class Officer extends User {
    Date WorkDate;
    Set<Bill> listBill;
    boolean status = false;

    public Officer(String username, String password, String name, String CMND, String sex) {
        super(username, password, name, CMND, sex);
        this.listBill = new HashSet<Bill>();
    }

    public Date getWorkDate() {
        return WorkDate;
    }

    public void setWorkDate(Date workDate) {
        WorkDate = workDate;
    }

    public Set<Bill> getListBill() {
        return listBill;
    }

    public void setListBill(Set<Bill> listBill) {
        this.listBill = listBill;
    }

    public void addBill(Bill bill){
        this.listBill.add(bill);
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
