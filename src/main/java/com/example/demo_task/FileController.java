package com.example.demo_task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/demo")
public class FileController {

    @Autowired
    private Sample sample;

    @Autowired
    Environment environment;
    @PostMapping("/zip")
    public String getZipFile() throws IOException {
        List<InputStream> inputStreams= sample.generateZipFile();
        sample.processDirectory(new File("C://Users/Ningaraj/Desktop/demo"),inputStreams);
        return "successful";
    }
}
