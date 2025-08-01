package project;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import project.vo.CustomerVo;
import project.vo.EchoVo;

@SpringBootApplication
public class App {

	public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
	
	@RestController
    public static class ApiController {
        private final String DATA_DIR = "./data/input";
        private final ObjectMapper objectMapper = new ObjectMapper();

        @GetMapping("/api/reservation/search")
        // 여기에 코드를 작성하세요.
        public ResponseEntity<?> login(@RequestParam(required = false) String customerName) {
            try {
                List<CustomerVo> users = objectMapper.readValue(
                        Files.readAllBytes(Paths.get(DATA_DIR, "reservation.json")),
                        new TypeReference<List<CustomerVo>>() {
                        });
                System.out.println(users);

                if (customerName == null || customerName.isEmpty()) {
                    Map<String, String> map = new HashMap<>();
                    map.put("error", "customerName is required");

                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
                }
                if (customerName.equals("all")) {
                    users.sort(Comparator.comparing(CustomerVo::getCheckIn));
                    return ResponseEntity.ok(users);
                }
                else {

                    List<CustomerVo> findUser = users.stream().filter(u -> u.getCustomerName().contains(customerName)).collect(Collectors.toList());

                    return ResponseEntity.ok(findUser);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
        
        @GetMapping("/server-health-check")
        public ResponseEntity<?> serverHealthCheck() {
            // 여기에 코드를 작성하세요.
            Map<String, String> map = new HashMap<>();

            map.put("result", "ok");
            return ResponseEntity.ok(map);
        }

        @PostMapping("/echo")
        public ResponseEntity<?> echo(@RequestBody(required = false) Map<String, Object> body) {
            
        	
        	if (!body.containsKey("name") || !body.containsKey("message")) {
                Map<String, String> map = new HashMap<>();
                map.put("error", "Invalid data format");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(map);
            }

            EchoVo vo = new EchoVo();
            vo.setEchoVo((String) body.get("name"), (String)body.get("message"));

            return ResponseEntity.ok(vo);
        }
    }
}
