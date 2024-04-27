package com.daitem.web.testController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/v1/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("테스트 코드입니다.");
    }

    @GetMapping("/api/v1/test2")
    public ResponseEntity<String> test2() {
        return ResponseEntity.ok("테스트 코드입니다. 이건 뜨니 미친놈아");
    }
}
