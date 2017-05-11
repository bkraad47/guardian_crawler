/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isentia.guardiancrawler;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author bulkg (Badruddin Kamal -Raad)
 */


public class Config implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        
        // Testing Resource simple test to check resource
        System.out.print("Starting test...");
        SearchResource sr = new SearchResource();
        
        if(sr.dbSearch("headline", "brexit") != null){
            System.out.print("Test Successful");
            
            System.out.print("Starting app...");
        
            try{
               new CrawlerTrigger().start();
            }catch(Exception e){
               System.err.println(e.getMessage());
            }
        } else {
            System.out.print("Test Failed. Scheduler not deployed.");
        }
        
         //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
         //To change body of generated methods, choose Tools | Templates.
    }

}