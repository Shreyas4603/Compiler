package com.example.Compiler.Classes;
import java.time.LocalDateTime;

public class Problem {
    private String _id;
    private String problemId;
    private String problemStatement;
    private String toughnessLevel;
    private String templateCodePy;
    private String templateCodeJava;
    private String templateCodeCpp;
    private String driverCodePy;
    private String driverCodeJava;
    private String driverCodeCpp;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int __v;

    // Getters only
    public String getId() {
        return _id;
    }

    public String getProblemId() {
        return problemId;
    }

    public String getProblemStatement() {
        return problemStatement;
    }

    public String getToughnessLevel() {
        return toughnessLevel;
    }

    public String getTemplateCodePy() {
        return templateCodePy;
    }

    public String getTemplateCodeJava() {
        return templateCodeJava;
    }

    public String getTemplateCodeCpp() {
        return templateCodeCpp;
    }

    public String getDriverCodePy() {
        return driverCodePy;
    }

    public String getDriverCodeJava() {
        return driverCodeJava;
    }

    public String getDriverCodeCpp() {
        return driverCodeCpp;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public int getVersion() {
        return __v;
    }
}
