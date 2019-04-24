package com.jfbueno.httpserver.messages;

import java.util.Iterator;
import java.util.StringTokenizer;
import java.io.File;

import com.jfbueno.httpserver.config.AppConfig;
import com.jfbueno.httpserver.config.Site;
import com.jfbueno.httpserver.helpers.*;

public class Request {
    private static File pagesPath = new File("C:\\Users\\jeferson.bueno\\Downloads\\tmp\\site");

    private String method;
    private String resource;
    private Site owner;

    public Request(String headerLine) {        
        StringTokenizer parse = new StringTokenizer(headerLine);
        this.method = parse.nextToken().toUpperCase();
        this.resource = parse.nextToken().toLowerCase();

        StringTokenizer tokens = new StringTokenizer(this.resource, "/");
        this.owner = AppConfig.getInstance().getConfig().getSite(tokens.nextToken());
    }

    public String getResource() {
        return resource;
    }

    public String getMethod() {
        return method;
    }
    
    public ResponseBuilder createResponse() {        
        File page = this.owner.getPage(resource);

        if(!page.exists()) {
            return new ResponseBuilder().withStatusCode(StatusCode.NotFound)
                                        .addResponseFile(new File("./target/classes/pages/not-found.html"));
        }
        
        return new ResponseBuilder().withStatusCode(StatusCode.Ok).addResponseFile(page);
    }
}