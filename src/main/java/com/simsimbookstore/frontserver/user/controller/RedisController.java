package com.simsimbookstore.frontserver.user.controller;

import com.simsimbookstore.frontserver.user.service.RedisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/redis")
@RestController
public class RedisController {

    public final RedisService redisService;

    public RedisController(RedisService redisService) {
        this.redisService = redisService;
    }

    @PostMapping
    public ResponseEntity saveData(){
        redisService.saveData("test",1234);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity getData(){
        Object data = redisService.getData("test");
        return ResponseEntity.ok(data);
    }
}
