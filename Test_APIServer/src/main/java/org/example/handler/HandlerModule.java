package org.example.handler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.example.utils.UserException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
public abstract class HandlerModule implements HttpHandler{
	Gson gson = new Gson();
	Map<String, String> params = new HashMap<>();
	@Override
	public void handle(HttpExchange he) throws IOException {
		
		URI requestURI = he.getRequestURI();
        String query = requestURI.getQuery();

        if (query != null) {
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                String[] kv = pair.split("=");
                if (kv.length == 2) {
                    params.put(kv[0], kv[1]);
                }
            }
        }
        
		
		Headers headers = he.getResponseHeaders();
		
		headers.set("Content-Type", "application/json");
		String response = "";
		
		try {
            response = gson.toJson(this.response());
            he.sendResponseHeaders(200, response.length());
        }
		catch (UserException e) {
			JsonObject result = new JsonObject();
            result.addProperty("message", e.getMessage());
            response = gson.toJson(result);
        	he.sendResponseHeaders(400, response.length());
		}
        catch (Exception e){
        	JsonObject result = new JsonObject();
            result.addProperty("message", e.getMessage());
            response = gson.toJson(result);
        	he.sendResponseHeaders(500, response.length());
        
        }
        finally {
            OutputStream os = he.getResponseBody();
            os.write(response.getBytes());
            os.close();

        }
	}

	
	public abstract <T> T response() throws Exception;

}
