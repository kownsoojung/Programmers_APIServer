package org.example.handler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class Handler4 implements HttpHandler{

	Gson gson = new Gson();
	Map<String, String> params = new HashMap<>();
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		// 파라미터 
		URI requestURI = exchange.getRequestURI();
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
        
        exchange.getResponseHeaders().set("Content-Type", "application/json");
		String response = "";
		JsonObject result = new JsonObject();
		
		int status = 200;
		
		try {
            if (!params.containsKey("userid")) {
            	status = 400;
				throw new Exception("is Empty");
			}
        	
        }
        catch (Exception e){
            result.addProperty("message", e.getMessage());
        }
        finally {
        	response = gson.toJson(response);
        	exchange.sendResponseHeaders(status, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();

        }
	}

}