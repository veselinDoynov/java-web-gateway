package web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.function.ServerRequest;
import web.entity.Student;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/student")
public class StudentController {

    public String API_ENDPOINT = "api/v1/student";

    @Autowired
    private Environment env;

    @PostMapping
    public JsonNode create(@RequestBody final Student student) throws JSONException, JsonProcessingException {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject studentObject = new JSONObject();
        studentObject.put("firstName", student.getFirstName());
        studentObject.put("lastName", student.getLastName());
        studentObject.put("email", student.getEmail());

        HttpEntity<String> request =
                new HttpEntity<String>(studentObject.toString(), headers);

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readTree(restTemplate.postForObject(env.getProperty("external_api") + API_ENDPOINT, request, String.class));

    }
}
