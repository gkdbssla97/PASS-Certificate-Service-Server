package com.example.miniProj2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CertificationController {

    @PostMapping("/v1/certification/notice")
    public TestDto requestCertification(@RequestBody Map<String, Object> requestData) {
        requestData.forEach((key, value) -> {
            System.out.println("key -> " + key);
            System.out.println("value -> " + value);
        });
        TestDto testDto = new TestDto("hayoon", "27");

        return testDto;

    }
    static class TestDto{
        private String name;
        private String age;

        public TestDto(String name, String age) {
            this.name = name;
            this.age = age;
        }
    }
}
