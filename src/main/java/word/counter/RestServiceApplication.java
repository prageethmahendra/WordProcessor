package word.counter;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

/**
 * This is the Rest Service Application class which will initiate the service using a main method
 * NOTE : no need to deploy to a web server as this is a POC
 */
public class RestServiceApplication {
    public static final String BASE_URI = "http://localhost:8080/wordcounter/";

    /**
     * Server start method
     * @return
     */
    public static HttpServer startServer() {
        final ResourceConfig rc = new ResourceConfig().packages("word.counter");
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.stop();
    }
}

