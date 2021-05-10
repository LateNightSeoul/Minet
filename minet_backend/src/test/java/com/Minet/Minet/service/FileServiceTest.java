package com.Minet.Minet.service;

import org.junit.Test;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.*;

public class FileServiceTest {

    @Test
    public void 파일이름변경테스트(){
        File file = new File("nav.mp3");
        System.out.println(file.getAbsoluteFile());
        String artistName = "nav";

        String fileName = StringUtils.cleanPath(artistName + "/" + file.getAbsolutePath());
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);

        System.out.println(fileName + " " + fileExtension);
    }

}