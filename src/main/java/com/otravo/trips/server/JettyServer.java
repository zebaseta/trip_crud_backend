package com.otravo.trips.server;

import lombok.Data;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@Data
public class JettyServer {

    private static final String VIEWS_LOCATION = "/webapp";

    private int port = 8080;
    private String contextPath = "/";
    private Class<?> springApplicationContextClass;

    private Server server;

    public JettyServer() {
    }

    public JettyServer(int port, String contextPath, Class<?> springApplicationContextClass) {
        this.port = port;
        this.contextPath = contextPath;
        this.springApplicationContextClass = springApplicationContextClass;
    }

    public void run() throws Exception {
        this.server = new Server(port);

        WebAppContext jettyWebContext = this.buildJettyWebContext();
        this.server.setHandler(jettyWebContext);

        this.server.setStopAtShutdown(true);
        this.server.start();
        this.server.join();
    }

    private WebAppContext buildJettyWebContext() throws Exception {
        WebAppContext jettyWebContext = new WebAppContext();
        // seteo del context path / path url escucha
        jettyWebContext.setContextPath(contextPath);

        // configuracion de tiempo de session
        jettyWebContext.getSessionHandler().setMaxInactiveInterval(30 * 60); //Es en segundos

        // config web resources para viewa/html - thymeleaf
        ClassPathResource classPathResource = new ClassPathResource(VIEWS_LOCATION);
        String resourceBasePath = classPathResource.getURI().toString();
        jettyWebContext.setResourceBase(resourceBasePath);

        // config spring web application context
        this.configSpring(jettyWebContext);

        return jettyWebContext;
    }

    private void configSpring(WebAppContext jettyWebContext) {
        // creo el application context, basado en clase de configuracion
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(this.springApplicationContextClass);

        // crear dispatcher servlet de spring
        DispatcherServlet dispatcherServlet = new DispatcherServlet(applicationContext);
        ServletHolder dispatcherServletHolder = new ServletHolder(dispatcherServlet);
        jettyWebContext.addServlet(dispatcherServletHolder, "/");

        // agregar el application context de spring a la aplicacion web
        ContextLoaderListener contextLoaderListener = new ContextLoaderListener(applicationContext);
        jettyWebContext.addEventListener(contextLoaderListener);
    }
}