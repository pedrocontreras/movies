package server;


import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import db.MovieSQLite;

public class RunServer {
   
    
    public static void main(String[] args) {

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        String baseDir = "/Applications/tomcat/webapps";

        try {
            MovieSQLite.loadToMemory();
            tomcat.addWebapp(null, "", baseDir + "/ROOT");
            tomcat.addWebapp(null, "/manager", baseDir + "/manager");
            tomcat.addWebapp(null, "/movies", baseDir + "/movies");
            tomcat.addWebapp(null, "/movie_data", baseDir + "/movie_data");
            tomcat.start();
            tomcat.getServer().await();

        } catch (LifecycleException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    
}
