/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isentia.guardiancrawler;

/**
 *
 * @author bulkg (Badruddin Kamal -Raad )
 */
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.Arrays;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class CrawlerJob implements Job
{
	public void execute(JobExecutionContext context)
	throws JobExecutionException {
                MongoClient mongoClient = null;
                try {
                   // Get connection
                    MongoCredential credential = MongoCredential.createCredential("", "guardiancrawl", "".toCharArray());
                    mongoClient = new MongoClient(new ServerAddress("ds153719.mlab.com", 53719),Arrays.asList(credential));
                    MongoDatabase database = mongoClient.getDatabase("guardiancrawl");
                    
                    // Clear old collections
                    MongoCollection<BasicDBObject> collection = database.getCollection("guardian", BasicDBObject.class);
                    collection.drop();
                    MongoCollection<BasicDBObject> links = database.getCollection("links", BasicDBObject.class);
                    links.drop();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                } finally {
                    mongoClient.close();
                }
                // Start Crawler
		Crawler c = new Crawler();
                c.setURL("https://www.theguardian.com/au");
                c.run();

	}

}