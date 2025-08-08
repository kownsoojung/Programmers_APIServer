package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.example.vo.CustomerVo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class App {
	
	static private final String basePath = "./data/input"; // 프로그래머스에서는 경로가 다른가보다.. ./이 안먹힘
    static ObjectMapper mapper = new ObjectMapper();
    static Gson gson = new Gson();
    public static void main(String[] args) {
        System.out.println("Hello world");
        
        try {
            String userList = readJsonFile("/customers.json");
            CustomerVo[] voList = gson.fromJson(userList, CustomerVo[].class);
            System.out.println(voList.length);

            /**
             * 
             *  1. 전체  고객 수 파일 만들기
             */
            // 파일 경로 지정
            String path1 = "./data/output/problem_1.json";
            File file1 = new File(path1);

            // 디렉토리가 없으면 생성
            file1.getParentFile().mkdirs();
            
            HashMap<String, Integer> map1 = new HashMap<>();
            map1.put("total", voList.length);
            mapper.writerWithDefaultPrettyPrinter().writeValue(file1, map1);

            
            /**
             * 
             *  2. status가 dormant인 데이터만 가져오기
             */
            List<Integer> list1 = new ArrayList<>();
            for (CustomerVo customerVo : voList) {
                if (customerVo.getStatus().equals("dormant")) {
                    list1.add(customerVo.getCustomer_id());
                }
            } 

            Collections.sort(list1);
             // 파일 경로 지정
            String path2 = "./data/output/problem_2.json";
            File file2 = new File(path2);
            mapper.writerWithDefaultPrettyPrinter().writeValue(file2, list1);

            System.out.println("end");
        }
        catch( Exception e) {
            System.err.println(e);
        }
        
    }

    
	
	static public String readJsonFile(String filename) throws IOException {
		Path path = Paths.get(basePath + filename);
		
        String content = new String(Files.readAllBytes(path));
        return content;
    }
}
