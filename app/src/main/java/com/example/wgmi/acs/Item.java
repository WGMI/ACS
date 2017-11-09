package com.example.wgmi.acs;

import android.content.Context;

import java.lang.reflect.Field;

/**
 * Created by WGMI on 03/11/2017.
 */

public class Item {

    String sno,name,specifications,image,category;
    int status,available;
    Context context;

    public Item(){

    }

    public Item(String sno, String name, String specifications, String image, String category, int status, int available) {
        this.sno = sno;
        this.name = name;
        this.specifications = specifications;
        this.image = image;
        this.category = category;
        this.status = status;
        this.available = available;
    }

    public Item(String sno, String name, String specifications, String image, String category, int status, int available, Context context) {
        this.sno = sno;
        this.name = name;
        this.specifications = specifications;
        this.image = image;
        this.category = category;
        this.status = status;
        this.available = available;
        this.context = context;
    }

    public int getDefaultImage(){
        return R.drawable.default_pic;
    }

    public static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public String getSno() {
        return sno;
    }

    public String getName() {
        return name;
    }

    public String getSpecifications() {
        return specifications;
    }

    public String getImage() {

        return image;
    }

    public int getImageID(){
        if(image != "0"){
            int resID = context.getResources().getIdentifier(image, "drawable", context.getPackageName());
            return resID;
        }else{
            return getDefaultImage();
        }
    }

    public String getCategory() {
        return category;
    }

    public int getStatus() {
        return status;
    }

    public int getAvailable() {
        return available;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setAvailable(int available) {
        this.available = available;
    }
}
