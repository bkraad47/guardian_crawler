/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isentia.guardiancrawler;

/**
 *
 * @author bulkg (Badruddin Kamal - Raad)
 */

import com.mongodb.BasicDBObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import java.util.Arrays;
import com.mongodb.MongoCredential;
import com.mongodb.client.FindIterable;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Element;


// Crawls and scrapes data
public class Crawler extends Thread {
        
        // Define page to work on
        String URL = "";
        
        public void setURL(String URL){
            this.URL = URL;
        }
        
        // Page scrapper task
        public void pageScrapper() {
            
            // Open MongoDB instance
            MongoClient mongoClient = null;
            List<Crawler> spiderlings = new ArrayList<Crawler>();
                
                try {
                    MongoCredential credential = MongoCredential.createCredential("", "guardiancrawl", "".toCharArray());
                    mongoClient = new MongoClient(new ServerAddress("ds153719.mlab.com", 53719),Arrays.asList(credential));
                    MongoDatabase database = mongoClient.getDatabase("guardiancrawl");
                    Document document = Jsoup.connect(URL).get();
                    
                    
                    // See if URL is an article 
                    Elements articleBody = document.select("article");
                    if(articleBody.size()>=1){
                        
                        // If the URL is an article extract relevant information
                        
                        MongoCollection<BasicDBObject> collection = database.getCollection("guardian", BasicDBObject.class);

                        // Get article text
                        BasicDBObject dbobject = new BasicDBObject();
                        for (Element body : articleBody) {
                            dbobject.put("text", body.text());
                        }; 

                        // Get article headline
                        Elements articleTopic = document.select("h1.content__headline");
                        for (Element topic : articleTopic) {
                            dbobject.put("headline",topic.text());
                        };
                        
                        // Get URL
                        dbobject.put("url",URL);
                        
                        // Get author
                        Elements articleAuthor = document.select("p.byline");
                        for (Element author : articleAuthor) {
                            dbobject.put("author",author.text());
                        };

                        // Get published date information
                        Elements articleDate = document.select("p.content__dateline");
                        for (Element date : articleDate) {
                            dbobject.put("date", date.text());
                        };

                        // Store to DB 
                        collection.insertOne(dbobject);
                        
                    }

                    MongoCollection<BasicDBObject> links = database.getCollection("links", BasicDBObject.class);
                    
                    Elements otherLinks = document.select("a[href^=\"https://www.theguardian.com/\"]");
                    for (Element page : otherLinks) {
                        
                        // Check if page link has been visited already
                        FindIterable<BasicDBObject> iterable = links.find(new BasicDBObject("link", page.attr("abs:href")));
                        
                        // If new link spawn thread crawler
                        if( iterable.first() == null ){
                            
                            links.insertOne(new BasicDBObject("link", page.attr("abs:href")));
                            Crawler c = new Crawler();
                            c.setURL(page.attr("abs:href"));
                            spiderlings.add(c);
                        }
                        
                    };
                    
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                } finally {
                    mongoClient.close();
                }
                
                // Avoid thread blocking to MongoDB resource
                for(Crawler c :spiderlings ){
                    c.run();
                }
            
    }
        
        public void run(){  
            System.out.println("Spawning spider for : " + URL);
            
            // Complete thread tasks
            pageScrapper();
            
            System.out.println("Swatting spider for : " + URL);
        }
}

