package word.counter.web.resources;

import word.counter.file.WordProcessor;
import word.counter.web.resources.pojo.CounterResults;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.*;

/**
 * Created by prageeth.g on 29/1/2016.
 */
@Path("count")
public class WordCounter {

    @POST
    @Consumes({MediaType.APPLICATION_OCTET_STREAM})
    @Produces({MediaType.APPLICATION_JSON})
    public CounterResults getWordCount(@HeaderParam("Transfer-Encoding")String transferEncoding,   InputStream attachmentInputStream) throws Exception {

        WordProcessor wordProcessor = new WordProcessor();
        CounterResults counterResults = wordProcessor.generateWordCounterResuts("1", attachmentInputStream);
        return counterResults;
    }
}
