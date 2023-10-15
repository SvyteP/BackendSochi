package com.example.Backend.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

@Service
public class DownloadService {

    @Value("${upload.path}")
    private  String pathVideo;
    @Value("${send.file}")
    private String sendFile;


    public String downloadVideo(MultipartFile []files){
        File send = new File(sendFile);
        try {
            FileOutputStream fos = new FileOutputStream(pathVideo + sendFile);

        for (MultipartFile file:
                files)
        {

            if (!file.isEmpty()){
                try {
                File uploadDir = new File(pathVideo);
                if(!uploadDir.exists()){
                    uploadDir.mkdir();
                }



                    file.transferTo(new File(pathVideo +"/"+file.getOriginalFilename()));
                    fos.write(file.getOriginalFilename().getBytes());
                    fos.write(" ".getBytes());

                }
                catch (IOException e) {
                    e.printStackTrace();
                }


            }

        }
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Ok";
    }
}
