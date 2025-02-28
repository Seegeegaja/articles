package com.my.articles.controller;

import com.my.articles.dto.LoginDto;
import org.aspectj.bridge.IMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ApiTestController {
    @GetMapping("/api")
    public String apiForm() {
        return "test/api";
    }

    // @RequestBody : Json 형식을 -> Object(DTO)
    // Return Type : ResponseEntity -> Object 전송
    @PostMapping("/apiPostTest")
    public ResponseEntity<String> apiPostTest(@RequestBody LoginDto dto) {
        String info = dto.getUserid() + "/" + dto.getPassword();
        System.out.println(info);
        return ResponseEntity.status(HttpStatus.OK).body(info);
    }

    @PostMapping("/getResponse")
    @ResponseBody
    public LoginDto postResponse() {
        return new LoginDto("박고가자", "김칠갑");
    }

    @PostMapping("apiPostArrayTest")
    public ResponseEntity<Map<String, String>> apiPostArrayTest(@RequestBody List<LoginDto> dtos) {
        dtos.stream().forEach(x -> System.out.println(x));
        Map<String, String> userData = new HashMap<>();
        userData.put("id", dtos.get(1).getUserid());
        userData.put("pw", dtos.get(1).getPassword());
        return ResponseEntity.status(HttpStatus.OK).body(userData);
    }

    @DeleteMapping("apiTest")
    @ResponseBody
    public String apiHttpMethodTest(){
        String message = "api Test";
        return message;
    }
    @PatchMapping("apiTest")
    @ResponseBody
    public String apiPathTest(){
        String message = "apiPathTest";
        return message;
    }
    @PutMapping("apiTest")
    @ResponseBody
    public String apiPutTest(){
        String message = "apiPutTest";
        return message;
    }
}
