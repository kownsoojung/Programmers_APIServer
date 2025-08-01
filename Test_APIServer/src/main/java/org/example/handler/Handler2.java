package org.example.handler;

import java.io.IOException;
import java.io.OutputStream;

import org.example.utils.JsonFileReader;
import org.example.vo.UserVo;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.*;

public class Handler2 implements HttpHandler {
    
    Gson gson = new Gson();
    JsonFileReader util = new JsonFileReader();
    @Override
	public void handle(HttpExchange exchange) throws IOException {
		
		
        exchange.getResponseHeaders().set("Content-Type", "application/json");
       
		String response = "";
		JsonObject result = new JsonObject();
		int status = 200;

		try {
            String userList = util.readJsonFile("/user.json");
            
            UserVo[] voList = gson.fromJson(userList, UserVo[].class);
            
            int sum = 0;
            for (UserVo vo : voList) {
                sum+= vo.getPostCount();
            }
            result.addProperty("sum", sum);
        }
        catch (Exception e){
            System.out.println("fail");
        }
        finally {
        	response = gson.toJson(result);
        	exchange.sendResponseHeaders(status, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();

            System.out.println(response);

        }
	}
	
}
