package dev.appsody.starter;

import static javax.ws.rs.core.MediaType.TEXT_HTML;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

import javax.inject.Inject;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import java.util.Optional;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.NoSuchElementException;

import javax.net.ssl.HttpsURLConnection;



@ApplicationScoped
@Path("/")
public class StarterResource {

    @Inject @ConfigProperty(name="BINDING_DEMO_MICROSERVICE_A_URL") Optional<String> serviceA_URL;

    @GET
    @Produces(TEXT_HTML)
    public String info() {
        return "Microservice B";
    }

    @GET
    @Path("/service")
    @Produces(TEXT_PLAIN)
    public String service() {
        try {
        return "Calling serviceA at " + serviceA_URL.get() + ". Result: " + sendGet(serviceA_URL.get());
        } catch (NoSuchElementException e) {
            return "Could not find the service provider.";
        }
    }

    	// HTTP GET request
	private String sendGet(String url)  {
    
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("GET");


            int responseCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();
        } catch (Exception e) {            
            return "Got an exception: " + e.getMessage();
        }
	}

}
