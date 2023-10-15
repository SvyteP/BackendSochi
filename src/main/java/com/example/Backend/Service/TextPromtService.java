package com.example.Backend.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class TextPromtService {
    @Value("${upload.path}")
    private  String pathVideo;

    @Value("${promt.file}")
    private String promtFileName;

    public String textPromt(String promt){
        try {
        File promtFile = new File(pathVideo);
        FileOutputStream fos = new FileOutputStream(pathVideo + promtFileName);




        if(!promtFile.exists()){
            promtFile.mkdir();
        }

        fos.write(promt.getBytes());
        fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Ok";
    }

}
