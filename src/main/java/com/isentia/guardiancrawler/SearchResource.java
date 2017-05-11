/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isentia.guardiancrawler;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import java.util.Arrays;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import java.util.List;



/**
 * REST Web Service
 *
 * @author bulkg (Badruddin Kamal - Raad)
 */
@Path("search")
public class SearchResource {
    
    
    // Search MongoDB
    public List<Result> dbSearch(String type, String query){
            MongoClient mongoClient = null;
            List<Result> results = new ArrayList<Result>();
            try {
                   // Get connection
                    MongoCredential credential = MongoCredential.createCredential("", "guardiancrawl", "".toCharArray());
                    mongoClient = new MongoClient(new ServerAddress("ds153719.mlab.com", 53719),Arrays.asList(credential));
                    MongoDatabase database = mongoClient.getDatabase("guardiancrawl");
                    MongoCollection<BasicDBObject> collection = database.getCollection("guardian", BasicDBObject.class);
                    
                    // Create search index for search type
                    if(type.compareTo("headline") == 0){
                        collection.createIndex(Indexes.text("headline"));
                    } else if(type.compareTo("author") == 0){
                        collection.createIndex(Indexes.text("author"));
                    } else if(type.compareTo("date") == 0){
                        collection.createIndex(Indexes.text("date"));
                    } else {
                        collection.createIndex(Indexes.text("text"));
                    }
                    
                    // Build response object List
                    FindIterable<BasicDBObject> iterable = collection.find(Filters.text(query));
                    for(BasicDBObject obj : iterable){
                            Result resObj = new Result();
                            resObj.setAuthor(obj.getString("author"));
                            resObj.setDate(obj.getString("date"));
                            resObj.setText(obj.getString("text"));
                            resObj.setHeadline(obj.getString("headline"));
                            resObj.setUrl(obj.getString("url"));
                            results.add(resObj);
                    }
                    
            } catch (Exception e) {
                    System.err.println(e.getMessage());
            } finally {
                    mongoClient.close();
            }
            return results;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    
    //Return the search results
    public Response getSearchResult(@QueryParam("type") String type,@QueryParam("query") String query) {
         
        String resp = new Gson().toJson(dbSearch(type,query));
        
        return Response.ok(resp).build();
    }
}
