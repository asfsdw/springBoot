package com.example.demo9.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class PDSService {
  @Autowired
  HttpServletRequest request;

  public void writeFile(MultipartFile sFile, String sFileName, String part) throws IOException {
    String realPath = request.getSession().getServletContext().getRealPath("/work/"+part+"/");

    FileOutputStream fos = new FileOutputStream(realPath+sFileName);
    /*
    // 한 번에 전부 넘기지 않고 bytes를 지정해줄 때(2048, 4096 등).
    byte[] bytes = new byte[2048];
    while(sFile.getInputStream().read(bytes) != -1) {
      fos.write(bytes, 0, sFile.getInputStream().read(bytes));
    }
    */
    fos.write(sFile.getBytes());

    fos.flush();
    fos.close();
  }
}
