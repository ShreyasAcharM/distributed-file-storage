package com.example.distributed_file_storage;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloController {

    @GetMapping("/")
    public String home() {
        return "Hello, the DFS server is running";
    }
}
