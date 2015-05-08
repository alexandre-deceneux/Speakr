package com.csulb.speakr.actionlistdata;

/**
 * Created by Alex on 07/05/2015.
 */
public class GsonActionList {

    private GsonAction []   list;
    private String          name;

    public String       getName(){
        return name;
    }

    public GsonAction[] getList(){
        return list;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setList(GsonAction [] list){
        this.list = list;
    }

}
