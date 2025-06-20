package org.example.io;


import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * FileStream 类封装类实现读取 src/main/resources 目录下的文件内容
 */
public class FileStream {

    /**
     * 逐字符读取指定文件的内容。
     * 使用 InputStreamReader 以 UTF-8 编码读取文件，避免多字节字符（如中文）的乱码问题。
     *
     * @param filename 文件名, 目标文件存放在 src/main/resources 目录下
     */
    public static void readByChar(String filename) {
        try {
            // 加载项目资源文件 src/main/resources, (从 classpath 中加载)
            URL resourceURL = FileStream.class.getClassLoader().getResource(filename);
            if (resourceURL == null) {
                System.out.println("资源不存在: "  + filename);
                return;
            }

            // 将资源文件 URL 转换为本地路径 String
            String inputPath = Paths.get(resourceURL.toURI()).toString();

            // try-with-resources 结构实现资源的自动释放
            try (InputStream in = new FileInputStream(inputPath)) {
                // 使用 InputStreamReader 单个字节读取，避免中文 UTF-8（3字节编码）导致的乱码问题
                InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8);
                int ch;
                while ((ch = reader.read()) != -1) {
                    System.out.print((char) ch);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 逐行读取指定文件的内容。
     * 使用 BufferedReader 和 InputStreamReader 以 UTF-8 编码读取文件，支持高效逐行处理。
     *
     * @param filename 文件名，位于 src/main/resources 目录下。
     */
    public static void readByLine(String filename) {
        try {
            // 加载项目资源文件 src/main/resources, (从 classpath 中加载)
            URL resourceURL = FileStream.class.getClassLoader().getResource(filename);
            if (resourceURL == null) {
                System.out.println("资源不存在: "  + filename);
                return;
            }

            // 将资源文件 URL 转换为本地路径 String
            String inputPath = Paths.get(resourceURL.toURI()).toString();

            // try-with-resources 结构实现资源的自动释放
            try (InputStream in = new FileInputStream(inputPath)) {
                // 使用 BufferReader line 读取
                BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 写入内容到指定文件中，支持覆写或追加模式。
     *
     * @param filename 文件路径，例如 "output/output.txt".
     *                 值得注意的是构建的内容在 "target/classes/" 目录下
     * @param content  要写入的字符串内容
     * @param append   是否追加模式：true 为追加，false 为覆写
     */
    public static void writeToFile(String filename, String content, boolean append) {
        try {
            // 获取资源根路径（即 resources 跟目录）
            URL rootURL = FileStream.class.getClassLoader().getResource(".");
            if (rootURL == null) {
                System.out.println("无法定位资源目录！");
                return;
            }

            // 构造输出路径：资源目录 + 相对路径
            Path outputPath = Paths.get(rootURL.toURI()).resolve(filename);

            // 确保父目录存在
            Files.createDirectories(outputPath.getParent());

            // 使用 try-with-resources 写入内容
            try (BufferedWriter writer =
                         new BufferedWriter(
                            new OutputStreamWriter(
                                new FileOutputStream(outputPath.toFile(), append),StandardCharsets.UTF_8))) {
                writer.write(content);
                writer.newLine(); // 换行
                System.out.println("写入成功: " + outputPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println(">>> 逐字符(char)读取");
        readByChar("input/input.txt");

        System.out.println("\n\n>>> 逐行(line)读取");
        readByLine("input/input.txt");

        System.out.println("\n>>> 写入文件");
        writeToFile("output/output.txt", "Hello, World!", true);
    }
}
