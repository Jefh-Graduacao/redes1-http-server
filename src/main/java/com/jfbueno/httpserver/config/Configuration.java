package com.jfbueno.httpserver.config;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Configuration {
    private int port;
    private Site[] sites;
    private Map<String, Site> sitesMap;
    
    public int getPort() {
        return port;
    }

    public Collection<Site> getAllSites() {
        return sitesMap.values();
    }

    public boolean hasSite(String id) {
        return sitesMap.containsKey(id);
    }

    public Site getSite(String id) {
        return sitesMap.get(id);
    }

    public void init() {
        if(sitesMap != null) return;

        sitesMap = new HashMap<String, Site>();

        for(Site s : sites) {
            sitesMap.put(s.getId(), s);
        }
    }
}