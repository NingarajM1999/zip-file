package com.example.demo_task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
public class Sample {
    @Autowired
    private Environment environment;

    public List<InputStream> generateZipFile() throws IOException {
        List<InputStream> inputList = new ArrayList<>();
        File directoryPath = new File(environment.getProperty("download.path1"));
        File[] files = directoryPath.listFiles();

        for (File file : files) {
            if (file.isFile()) {
                try (InputStream input = new FileInputStream(file.getAbsoluteFile())) {
                    inputList.add(input);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (file.isDirectory()) {
                processDirectory(file, inputList);
            }
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ZipOutputStream zipOut = new ZipOutputStream(baos)) {
            for (int i = 0; i < inputList.size(); i++) {
                InputStream input = inputList.get(i);
                String fileName = "ff" + (i + 1) + ".txt";
                zipOut.putNextEntry(new ZipEntry(fileName));

                byte[] bytes = new byte[1024];
                int length;
                try {
                    while ((length = input.read(bytes)) >= 0) {
                        zipOut.write(bytes, 0, length);
                    }
                } catch (Exception exception) {
                    continue;
                }
            }
        }

        byte[] zipBytes = baos.toByteArray();
        try (FileOutputStream fos = new FileOutputStream("output3.zip")) {
            fos.write(zipBytes);
        }

        System.out.println("created");
        return inputList;
    }

    public void processDirectory(File file, List<InputStream> inputList) throws IOException {
        Path directoryPath = Paths.get(file.getAbsolutePath());

        if (file.isFile() && file.exists()) {
            inputList.add(new FileInputStream(file.getAbsoluteFile()));
        } else if (file.isDirectory() && file.exists()) {
            Files.walk(Paths.get(file.getAbsolutePath()))
                    .filter(Files::isRegularFile)
                    .forEach(path -> {
                        if (path.toFile().exists()) {
                            try {
                                System.out.println(path.getFileName());
                                inputList.add(new FileInputStream(path.toFile()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }
    }
}
