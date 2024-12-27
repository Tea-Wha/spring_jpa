package com.kh.spring_jpa241217.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.util.Arrays;

@Service
@Slf4j
@RequiredArgsConstructor
public class CodingTestService {
    public String executeCode(String code) {
        try {
            File sourceFile = new File(code);
            compileSourceFile(sourceFile);

        } catch (Exception e) {
            log.error("Failed to compile/execute source code", e);
        }
        return "";
    }

    private void compileSourceFile(File sourceFile) throws Exception {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> compilationUnits =
                fileManager.getJavaFileObjectsFromFiles(Arrays.asList(sourceFile));

        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, null, null, compilationUnits);
        boolean success = task.call();
        fileManager.close();

        if (!success) {
            throw new RuntimeException("Compilation failed");
        }
    }
}
