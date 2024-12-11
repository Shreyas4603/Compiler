package com.example.Compiler.Controllers;

import com.example.Compiler.Models.CodeSubmission;
import com.example.Compiler.Services.RunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/compile")
public class CompilerController {

    @Autowired
    private RunService runService;

    @PostMapping
    public ResponseEntity<String> compileCode(@RequestBody CodeSubmission submission) {
        String result;

        try {
            result = runService.compileAndRun(submission);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return ResponseEntity.status(500).body("Error executing code: " + e.getMessage());
        }
    }
}
