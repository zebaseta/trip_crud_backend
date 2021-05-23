package com.otravo.trips;

import com.otravo.trips.config.WebContext;
import com.otravo.trips.server.JettyServer;

public class AppMain {

    public static void main(String[] args) throws Exception {
        JettyServer webServer = new JettyServer();
        webServer.setPort(8890);
        webServer.setContextPath("/otravo");
        webServer.setSpringApplicationContextClass(WebContext.class);
        webServer.run();
    }
}