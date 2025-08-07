package project;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import project.vo.ErrorDto;
import project.vo.UserVo1;
import project.vo.UserVo2;

@SpringBootApplication
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
	
	@RestController
	public static class ApiController {
		private final String DATA_DIR = "./data/input";
        private final ObjectMapper objectMapper = new ObjectMapper();
        @GetMapping("/api/gamerecord/users")
        // 여기에 코드를 작성하세요.
        public ResponseEntity<?> getUsers() {
            try {
                List<UserVo1> users = objectMapper.readValue(
                        Files.readAllBytes(Paths.get(DATA_DIR, "records.json")),
                        new TypeReference<>() {
                        });
                
                // 유저 명, tag 명으로 정렬
                Collections.sort(users
                    , Comparator.comparing((UserVo1 vo) -> vo.getUsername())
                                .thenComparing(vo -> vo.getTag()));
                return ResponseEntity.ok(users);
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        @GetMapping("/api/gamerecord/winrate")
        // 여기에 코드를 작성하세요.
        public ResponseEntity<?> getWinrate(@RequestParam(required = false, value = "username") String username, @RequestParam(required = false, value = "tag") String tag) {
            try {
                List<UserVo2> users = objectMapper.readValue(
                        Files.readAllBytes(Paths.get(DATA_DIR, "records.json")),
                        new TypeReference<>() {
                        });
                
                
                // 값이 null 이거나 비어 있을 경우
                if ( username == null || tag == null|| username.isEmpty() || tag.isEmpty()) {
                    return ResponseEntity
                            .status(HttpStatus.UNAUTHORIZED)
                            .body(new ErrorDto("Invalid data format"));
                }

                // 필터를 통하여 조회
                List<UserVo2> findList = users.stream().filter(vo -> vo.getUsername().equals(username) && vo.getTag().equals(tag)).collect(Collectors.toList());
                
                
                // 없을 경우 
                if (findList.size() == 0) {
                    return ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(new ErrorDto("data not found"));
                }

                HashMap<String, Object> result = new HashMap<>();

                UserVo2 vo = findList.get(0);
                double data = (double) vo.getWin() / (vo.getWin() + vo.getLose()) * 100;
                int value = (int) data;
                
                result.put("winrate", value);

                return ResponseEntity.ok(result);
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    
	}
}
