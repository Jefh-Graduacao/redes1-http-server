package com.jfbueno.httpserver.config;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;

public class Site {
    private String id;
    private String name;
    private String location;
    private String[] default_pages;

    private File webRoot;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public Iterator<String> getDefaultPages() {
        return Arrays.asList(default_pages).iterator();
    }

    public File getWebRoot() {
        if(webRoot == null) {
            this.webRoot = new File(this.location);
        }

        return this.webRoot;
    }

    public File getPage(String pageName) {
        if(pageName.endsWith("/") && pageName.length() > 1)
            pageName = pageName.substring(0, pageName.length() - 1);

        pageName = pageName.replace(getId(), "");

        if(pageName.equals("/")) return getDefaultPage();
        return new File(getWebRoot(), pageName);
    }

    public File getDefaultPage() {
        File responseFile = null;
        Iterator<String> defaultPages = getDefaultPages();

        do {
            String defaultPage = defaultPages.next();
            responseFile = new File(getWebRoot(), defaultPage);
        }
        while(!responseFile.exists() && defaultPages.hasNext());
        
        return responseFile;
    }
}