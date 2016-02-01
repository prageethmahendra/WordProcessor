package word.counter;

import org.glassfish.jersey.client.JerseyClient;
import word.counter.web.resources.pojo.CounterResults;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import java.io.*;

/**
 * Created by prageeth.g on 29/1/2016.
 */
public class IntegrationTestCase {

    private static String TEXT_FILE_PATH = "test.txt";
    private static WebTarget target;

    public static void main(String[] args) {
        if (args != null && args.length >= 1) {
            TEXT_FILE_PATH = args[0];
        }
        JerseyClient c = (JerseyClient) ClientBuilder.newClient();
        target = c.target(RestServiceApplication.BASE_URI);
        FileInputStream fileInputStream = null;

        try {
            fileInputStream = new FileInputStream(new File(TEXT_FILE_PATH));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        CounterResults response = null;
        response = target.path("count/").request(MediaType.APPLICATION_JSON).header("username", "TestClient")
                .post(Entity.entity(fileInputStream, MediaType.APPLICATION_OCTET_STREAM), CounterResults.class);

        System.out.println(response);
    }


}
