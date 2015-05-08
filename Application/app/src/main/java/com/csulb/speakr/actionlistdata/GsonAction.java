package com.csulb.speakr.actionlistdata;

/**
 * Created by Alex on 07/05/2015.
 */
public class GsonAction {

    private String      id;
    private String      name;
    private String      description;
    private String []   params;

    public String       getId(){
        return id;
    }

    public String       getName(){
        return name;
    }

    public String       getDescription(){
        return description;
    }

    public String []    getParams(){
        return params;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setParams(String [] params){
        this.params = params;
    }

}
