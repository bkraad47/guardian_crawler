/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isentia.guardiancrawler;

/**
 *
 * @author bulkg (Badruddin Kamal- Raad)
 */
public class Result {
    // Result object
    public String headline="";
    public String text="";
    public String date="";
    public String author="";
    public String url="";
    
    public void setHeadline(String headline){
        this.headline = headline;
    }
    
    public void setAuthor(String author){
        this.author = author;
    }
    
    public void setText(String text){
        this.text = text;
    }
    
    public void setDate(String date){
        this.date = date;
    }
    
    public void setUrl(String url){
        this.url = url;
    }
    
    public String getHeadline(){
        return this.headline;
    }
    
    public String getAuthor(){
        return this.author;
    }
    
    public String getText(){
        return this.text;
    }
    
    public String getDate(){
        return this.date;
    }
    
    public String getUrl(){
        return this.url;
    }
}
