package com.example.Compiler.Services;

import com.example.Compiler.Classes.Problem;
import com.example.Compiler.Clients.NodeClient;
import com.example.Compiler.Models.CodeSubmission;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.api.model.HostConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class RunService {

    private final DockerClient dockerClient;

    @Autowired
    public RunService(DockerClient dockerClient) {
        this.dockerClient = dockerClient;
    }

    @Autowired
    private NodeClient nodeClient;

    public String compileAndRun(CodeSubmission submission) {
        // Get the driver code
        String driverCode = getProblemData(submission.getProblemId()).getDriverCodePy();

        String language = submission.getLanguage().toLowerCase();
        String code = submission.getCode();

        String imageId;
        String codeFileName;
        String driverFileName;

        // Set Docker image and file name based on language
        switch (language) {
            case "python":
                imageId = "python-compiler";
                codeFileName = "Solution.py";
                driverFileName = "Driver.py";
                break;
            case "cpp":
                imageId = "cpp-compiler";
                codeFileName = "main.cpp";
                driverFileName = "Driver.cpp";
                break;
            case "java":
                imageId = "java-compiler";
                codeFileName = "Main.java";
                driverFileName = "Driver.java";
                break;
            default:
                throw new IllegalArgumentException("Unsupported language: " + language);
        }

        File codeFile = null;
        File driverFile = null;
        try {
            // Create a temporary directory for this submission
            Path tempDir = createSubmissionDirectory();

            // Save code and driver files to the same directory
            codeFile = saveToFile(tempDir, codeFileName, code);
            driverFile = saveToFile(tempDir, driverFileName, driverCode);

            // Create HostConfig with binds for code and driver files
            HostConfig hostConfig = HostConfig.newHostConfig()
                    .withBinds(
                            Bind.parse(codeFile.getAbsolutePath() + ":/app/" + codeFileName),
                            Bind.parse(driverFile.getAbsolutePath() + ":/app/" + driverFileName)
                    );

            // Create and start the container
            CreateContainerResponse container = dockerClient.createContainerCmd(imageId)
                    .withHostConfig(hostConfig)
                    .exec();

            dockerClient.startContainerCmd(container.getId()).exec();

            // Get logs (output from container execution)


            // Stop and remove container
            dockerClient.stopContainerCmd(container.getId()).exec();
            String result = getContainerLogs(dockerClient, container.getId());
            dockerClient.removeContainerCmd(container.getId()).exec();

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return "Error executing code: " + e.getMessage();
        }
    }

    private Path createSubmissionDirectory() throws IOException {
        Path baseDir = Paths.get(System.getProperty("user.home"), "docker_code_submissions");
        Files.createDirectories(baseDir); // Ensure the base directory exists
        return Files.createTempDirectory(baseDir, "code_submission");
    }

    private File saveToFile(Path directory, String fileName, String content) throws IOException {
        Path filePath = directory.resolve(fileName);
        System.out.println("file path : " + filePath);
        File file = filePath.toFile();
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
        } catch (Exception e) {
            System.out.printf("Error occurred: " + e.toString());
        }
        return file;
    }

    private String getContainerLogs(DockerClient dockerClient, String containerId) {
        StringBuilder logBuilder = new StringBuilder();
        try {
            dockerClient.logContainerCmd(containerId)
                    .withStdOut(true)
                    .withStdErr(true)
                    .exec(new ResultCallback.Adapter<Frame>() {
                        @Override
                        public void onNext(Frame frame) {
                            logBuilder.append(new String(frame.getPayload()));
                        }
                    }).awaitCompletion();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "Log retrieval interrupted.";
        }
        return logBuilder.toString();
    }

    private Problem getProblemData(String id) {
        Problem data = nodeClient.getProblemById(id);
        return data;
    }
}

