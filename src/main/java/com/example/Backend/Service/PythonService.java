package com.example.Backend.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
@Service
public class PythonService {
    @Value("${pyMl.path}")
    private String pathPyMl;

    @Value("${pyName.file}")
    private String pythonScript;
    @Value("${upload.path}")
    private  String pathVideo;
    @Value("${promt.file}")
    private String promtFile;

    @Value("${send.file}")
    private String sendFile;

    private String textPromt;
    private String textSendFile;

    private BufferedReader reader;
    private BufferedReader readerSend;

    public String startMl(){
        try{
            readerSend =new BufferedReader(new FileReader(pathVideo+sendFile));
            reader = new BufferedReader(new FileReader(pathVideo+promtFile));

            textPromt = reader.readLine();
            textSendFile =readerSend.readLine();

            while (!textPromt.isEmpty()) {
                System.out.println(textPromt);
                // read next line
                textPromt = reader.readLine();
            }
            while (!textSendFile.isEmpty()) {
                System.out.println(textSendFile);
                // read next line
                textSendFile = readerSend.readLine();
            }
            Process process  = new ProcessBuilder("python",pathPyMl+pythonScript,textPromt,textSendFile).start();


            reader.close();

            int exitCode = process.waitFor();// Ожидание завершения скрипта
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader((process.getInputStream())));

            String line;

            while ((line = reader.readLine()) != null){
                System.out.println(line); //Вывод результата
            }

            System.out.println("Выполнение Python-скрипта завершено с кодом: " + exitCode);
        } catch (Exception e) {
            return e.getMessage();
        }
        return "Ok";
    }
}
