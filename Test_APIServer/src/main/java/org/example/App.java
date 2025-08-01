package org.example;

import java.net.InetSocketAddress;

import org.example.handler.Handler1;
import org.example.handler.Handler2;
import org.example.handler.Handler3;

import com.sun.net.httpserver.*;
public class App {
	public static void main(String[] args) throws Exception{
        System.out.println("Hello world");
        
        // httpServer로 설정
        HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0", 5678), 0);
        server.createContext("/", new Handler1());
        server.createContext("/sum", new Handler2());
        server.createContext("/find", new Handler3());
        
        server.setExecutor(null);
        server.start();

    }
}
