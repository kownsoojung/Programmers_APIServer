package org.example.handler;

import org.example.utils.JsonFileReader;
import org.example.vo.UserVo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;

public class Handler3 extends HandlerModule {

	JsonFileReader jf = new JsonFileReader();
	
	@SuppressWarnings("unchecked")
	@Override
	public JsonObject response() throws Exception{
		
		JsonObject result = new JsonObject();
        
        String userList = jf.readJsonFile("data/input/user.json");
        
        UserVo[] voList = gson.fromJson(userList, UserVo[].class);
        try {
        	
        	if (!params.containsKey("userid")) {
				throw new Exception("is Empty");
			}
        	
        	if (!isInteger(params.get("userid"))) {
				throw new Exception("is Not numbers");
			};
			
			for (UserVo vo : voList) {
				if ( vo.getUserId() == Integer.parseInt(params.get("userid")) ) {
					ObjectMapper mapper = new ObjectMapper();
				}
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
        
		return result;
	}
	
	private boolean isInteger(String strValue) {
		try {
	      Integer.parseInt(strValue);
	      return true;
	    } catch (NumberFormatException ex) {
	      return false;
	    }
		
	}
}
