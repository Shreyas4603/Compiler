package com.example.Compiler.Clients;


import com.example.Compiler.Classes.Problem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "node-service", url = "${node-service.url}")
public interface NodeClient {
    @GetMapping("/api/problem/unique")
    String  greet();

    @GetMapping("/api/problem/{id}")
    Problem getProblemById(@PathVariable("id") String id);

}
