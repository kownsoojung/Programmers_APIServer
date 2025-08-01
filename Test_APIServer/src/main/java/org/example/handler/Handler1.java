package org.example.handler;


import java.io.IOException;
import java.io.OutputStream;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.*;

public class Handler1 implements HttpHandler {
    
    Gson gson = new Gson();
    @Override
	public void handle(HttpExchange exchange) throws IOException {
		
        exchange.getResponseHeaders().set("Content-Type", "application/json");
       
		String response = "";
		JsonObject result = new JsonObject();
		
		int status = 200;
		
		try {
            result.addProperty("message", "server check");
        	
        }
        catch (Exception e){
            
        }
        finally {
        	response = gson.toJson(result);
        	exchange.sendResponseHeaders(status, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
	}
}
