package project;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import project.vo.CustomerVo;

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
        public ResponseEntity<?> login(@RequestParam(value="customer_name", required = false) String customer_name ) {
            try {
                File file = new File(DATA_DIR + "/reservation.json");
                List<CustomerVo> users = objectMapper.readValue(file, new TypeReference<List<CustomerVo>>() {});
                if (customer_name == null || customer_name.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("customerName is required");
                }
                
                if (customer_name.equals("all")) {
                    users.sort(Comparator.comparing(CustomerVo::getCheckIn));
                    return ResponseEntity.ok(users);
                }

                List<CustomerVo> findUser = users.stream().filter(u -> u.getCustomerName().contains(customer_name)).collect(Collectors.toList());
                return ResponseEntity.ok(findUser);
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }
}
