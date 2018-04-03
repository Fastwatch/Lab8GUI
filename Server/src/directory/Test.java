/**
 * Simple HTTP handler for testing ChronoTimer
 */
package directory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Test {

    // a shared area where we get the POST data and then use it in the other handler
    static boolean gotMessageFlag = false;
    static MainDirectory md;

    public static void main(String[] args) throws Exception {

        // set up a simple HTTP server on our local host
        HttpServer server = HttpServer.create(new InetSocketAddress(8001), 0);

        // create a context to get the request to display the results
        server.createContext("/displayresults", new DisplayHandler());

        // create a context to get the request for the POST
        server.createContext("/sendresults",new PostHandler());
        
     // create a context to get the request to display the results
        server.createContext("/displayresults/directory", new DirectoryHandler());

        // create a context to get the request for the POST
        server.createContext("/displayresults/style.css",new StyleHandler());
        
        server.setExecutor(null); // creates a default executor
        md = new MainDirectory();
        // get it going
        System.out.println("Starting Server...");
        server.start();
    }

    static class DisplayHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {

            String response = "Begin of response\n";
			Gson g = new Gson();
			// set up the header
            System.out.println(response);
			try {
				System.out.println(response);
				ArrayList<Employee> fromJson = g.fromJson(md.toString(),
						new TypeToken<Collection<Employee>>() {
						}.getType());

				System.out.println(response);
				for (Employee e : fromJson) {
					response += e + "\n";
				}
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
			}
            response += "End of response\n";
            System.out.println(response);
            // write out the response
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
    static class DirectoryHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
        }
    }
    static class StyleHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {

        }
    }

    static class PostHandler implements HttpHandler {
        public void handle(HttpExchange transmission) throws IOException {

            // set up a stream to read the body of the request
            InputStream inputStr = transmission.getRequestBody();

            // set up a stream to write out the body of the response
            OutputStream outputStream = transmission.getResponseBody();

            // string to hold the result of reading in the request
            StringBuilder sb = new StringBuilder();

            // read the characters from the request byte by byte and build up the sharedResponse
            int nextChar = inputStr.read();
            while (nextChar > -1) {
                sb=sb.append((char)nextChar);
                nextChar=inputStr.read();
            }

            // respond to the POST with ROGER
            String postResponse = "ROGER JSON RECEIVED";
            
            JsonObject j = new JsonParser().parse(sb.toString()).getAsJsonObject();
            if(j.has("print")){
            	md.print();
            }else if (j.has("clr")){
            	md.clr();
            }else if (j.has("add")){
            	md.add(j.get("add").toString());
            }
            
            // assume that stuff works all the time
            transmission.sendResponseHeaders(300, postResponse.length());

            // write it and return it
            outputStream.write(postResponse.getBytes());

            outputStream.close();
        }
    }

}