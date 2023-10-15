package com.example.Backend.Controller;

import com.example.Backend.Service.DownloadService;
import com.example.Backend.Service.PythonService;
import com.example.Backend.Service.StorageService;
import com.example.Backend.Service.TextPromtService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Controller
@RequestMapping
public class MainController {


    @Value("${upload.path}")
    private  String pathVideo;

    @Value("${promt.file}")
    private String promtFileName;
    @Value("${result.path}")
    private String pathResult;

    @Autowired
    private PythonService pythonService;
    @Autowired
    private TextPromtService textPromtService;
    @Autowired
    private DownloadService downloadService;
    @Autowired
    private StorageService storageService;



    @GetMapping
    public String startPage(){
        return  "index";
    }

    @PostMapping
    public String add(@RequestParam(value = "file",required = true) MultipartFile[] files,
                      @RequestParam String promt
    ) throws IOException {
        //загрузка переданных видео
        downloadService.downloadVideo(files);
        //Обработка текстового промта
        textPromtService.textPromt(promt);
        //Запуск pyML
        pythonService.startMl();


        return "index";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) throws MalformedURLException {

        Resource file = storageService.loadAsResource(filename);

        if (file == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "inline; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("/image")
    public ResponseEntity<byte[]> getImage() throws IOException {
        // Замените на путь к вашей картинке

        Path imageFilePath = Paths.get(pathResult);
        byte[] imageBytes = Files.readAllBytes(imageFilePath);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(imageBytes.length);

        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

}
