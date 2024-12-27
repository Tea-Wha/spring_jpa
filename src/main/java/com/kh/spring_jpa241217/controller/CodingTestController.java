package com.kh.spring_jpa241217.controller;

import com.kh.spring_jpa241217.dto.request.SubmitCodeRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/coding-test")
public class CodingTestController {

    @PostMapping("/submit")
    public String handleSubmit(@RequestBody SubmitCodeRequest requestData) {
        log.info("제출된 코드 {} {}", requestData.getLanguage(), requestData.getCode());

        try {
            // 사용자 코드로 파일 생성
            File codeFile = new File("UserSolution.java");
            writeCodeToFile(codeFile, requestData.getCode());

            // Java 코드 컴파일 및 실행
            return executeJavaCode(codeFile);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    // 사용자 코드 파일에 작성하는 메서드
    private void writeCodeToFile(File codeFile, String code) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(codeFile))) {
            writer.write(code);
        }
    }

    // Java 코드 컴파일 및 실행
    private String executeJavaCode(File codeFile) throws IOException {
        // Java 파일 컴파일
        Process compileProcess = new ProcessBuilder("javac", codeFile.getName()).start();
        try {
            compileProcess.waitFor();
        } catch (InterruptedException e) {
            return "Compilation failed: " + e.getMessage();
        }

        // 컴파일된 클래스 실행
        Process runProcess = new ProcessBuilder("java", "UserSolution").start();
        String result = getProcessOutput(runProcess);
        log.info("코드 실행 결과 {} ", result);

        return result;
    }

    // 프로세스의 출력 결과를 읽어오는 메서드
    private String getProcessOutput(Process process) throws IOException {
        // 표준 출력 스트림 읽기
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }

        // 에러 스트림 읽기 (필요시 에러 메시지 확인)
        BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        StringBuilder errorOutput = new StringBuilder();
        while ((line = errorReader.readLine()) != null) {
            errorOutput.append(line).append("\n");
        }

        // 에러가 발생했을 경우 에러 메시지 출력
        if (errorOutput.length() > 0) {
            log.error("Error occurred: {}", errorOutput.toString());
        }

        // 프로세스가 정상적으로 종료되었는지 확인
        try {
            int exitCode = process.waitFor();
            log.info("Process exited with code {}", exitCode);
        } catch (InterruptedException e) {
            log.error("Process interrupted: {}", e.getMessage());
        }

        return output.toString();  // 표준 출력 결과 반환
    }

}
