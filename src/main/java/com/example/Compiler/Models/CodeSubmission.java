package com.example.Compiler.Models;

public class CodeSubmission {
    private String code;
    private String language;
    private String problemId;


    // Constructors, Getters, and Setters
    public CodeSubmission() {}

    public CodeSubmission(String code, String language,String problemId) {
        this.code = code;
        this.language = language;
        this.problemId = problemId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getProblemId() {
        return problemId;
    }
    public void setProblemId(String problemId) {
        this.problemId = problemId;
    }
}
