package com.example.Backend.Service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {


        void init();

        void store(MultipartFile file);

        Stream<Path> loadAll();

        Path load(String filename);

        Resource loadAsResource(String filename) throws MalformedURLException;

        void deleteAll();


}
