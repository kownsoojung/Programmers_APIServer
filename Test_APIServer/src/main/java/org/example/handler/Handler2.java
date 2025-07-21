package org.example.handler;

import java.io.IOException;

import org.example.utils.JsonFileReader;
import org.example.vo.UserVo;

import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;

public class Handler2 extends HandlerModule {

	JsonFileReader jf = new JsonFileReader();
	
	@SuppressWarnings("unchecked")
	@Override
	public JsonObject response() throws IOException{
		
		JsonObject result = new JsonObject();
        String userList = jf.readJsonFile("data/input/user.json");
        
        UserVo[] voList = gson.fromJson(userList, UserVo[].class);
        
        int sum = 0;
        for (UserVo vo :voList ) {
            sum += vo.getPostCount();
        }
        
        
        
        result.addProperty("sum", sum);
        
        
		return result;
	}


	
	
}
