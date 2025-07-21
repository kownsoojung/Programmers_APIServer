package org.example.handler;


import com.google.gson.JsonObject;
public class Handler1 extends HandlerModule {

	@SuppressWarnings("unchecked")
	@Override
	public JsonObject response() {
		
		JsonObject result = new JsonObject();
        result.addProperty("message", "server check");

		return result;
	}
}
