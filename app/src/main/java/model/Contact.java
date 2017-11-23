package model;

/**
 * Created by admin on 11/22/2017.
 */

public class Contact {
    private int ma;
    private String ten;
    private String phone;

    public Contact() {
    }

    public Contact(int ma, String ten, String phone) {
        this.ma = ma;
        this.ten = ten;
        this.phone = phone;
    }

    public int getMa() {
        return ma;
    }

    public void setMa(int ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return this.ma+" "+this.ten+"\n"+this.phone;
    }
}
