package com.jfbueno.httpserver.messages;

import java.util.StringTokenizer;
import java.io.File;

import com.jfbueno.httpserver.config.AppConfig;
import com.jfbueno.httpserver.config.Configuration;
import com.jfbueno.httpserver.config.Site;
import com.jfbueno.httpserver.helpers.*;

public class Request {
    private String method;
    private String resource;
    private Site owner;

    public Request(String headerLine) {        
        StringTokenizer parse = new StringTokenizer(headerLine);
        this.method = parse.nextToken().toUpperCase();
        this.resource = parse.nextToken().toLowerCase();

        StringTokenizer tokens = new StringTokenizer(this.resource, "/");
        Configuration config = AppConfig.getInstance().getConfig();
        String siteKey;

        if(tokens.countTokens() == 0) {
            siteKey = "root";
        } else {
            siteKey = tokens.nextToken();
        }

        this.owner = config.getSite(config.hasSite(siteKey) ? siteKey : "root");
    }

    public String getResource() {
        return resource;
    }

    public String getMethod() {
        return method;
    }
    
    public ResponseBuilder createResponse() {
        ResponseBuilder notFoundResponse = new ResponseBuilder().withStatusCode(StatusCode.NotFound)
                                                .addResponseFile(new File("./target/classes/pages/not-found.html"));
        
        if(this.owner == null)
            return notFoundResponse;
        
        File page = this.owner.getPage(resource);

        if(!page.exists()) {
            return notFoundResponse;
        }
        
        return new ResponseBuilder().withStatusCode(StatusCode.Ok).addResponseFile(page);
    }
}