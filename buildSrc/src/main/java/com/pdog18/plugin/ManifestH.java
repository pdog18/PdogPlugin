package com.pdog18.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class ManifestH implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        final File manifest = getManifestFile(project);

        StringBuilder content = getFileContent(manifest);
        try {
            StringBuffer buffer = new StringBuffer();
            String line; // 用来保存每行读取的内容
            BufferedReader bufferreader = new BufferedReader(new InputStreamReader(new FileInputStream(manifest)));
            line = bufferreader.readLine(); // 读取第一行
            while (line != null) { // 如果 line 为空说明读完了
                buffer.append(line); // 将读到的内容添加到 buffer 中
//                buffer.append("\n"); // 添加换行符
                line = bufferreader.readLine(); // 读取下一行
            }
//		 将读到 buffer 中的内容写出来
            System.out.print(buffer);
            bufferreader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private StringBuilder getFileContent(File manifest) {
        StringBuilder builder = new StringBuilder();

        try {
            String line; // 用来保存每行读取的内容
            BufferedReader bufferreader = new BufferedReader(new InputStreamReader(new FileInputStream(manifest)));
            line = bufferreader.readLine(); // 读取第一行
            while (line != null) { // 如果 line 为空说明读完了
                builder.append(line); // 将读到的内容添加到 builder 中
//                buffer.append("\n"); // 添加换行符
                line = bufferreader.readLine(); // 读取下一行
            }
//		 将读到 buffer 中的内容写出来
            System.out.print(builder);
            bufferreader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder;
    }

    private File getManifestFile(Project project) {
        return new File(project.getProjectDir(), "src/main/AndroidManifest.xml");
    }
}
