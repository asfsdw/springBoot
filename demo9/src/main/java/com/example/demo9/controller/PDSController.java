package com.example.demo9.controller;

import com.example.demo9.service.PDSService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/pds")
public class PDSController {
  private final PDSService pdsService;

  @GetMapping("/pdsList")
  public String pdsListGet() {
    return "pds/pdsList";
  }
  @PostMapping("/pdsList")
  public String pdsListPost(MultipartFile sFile) {
    if(sFile.getOriginalFilename() == null) return "redirect:/message/fileUploadNo";

    String oFileName = sFile.getOriginalFilename();
    String sFileName = new SimpleDateFormat("yyMMddHHmmss").format(new Date())+"_"+oFileName;

    try {
      pdsService.writeFile(sFile, sFileName, "uploadTest");
    } catch (IOException e) {return "redirect:/message/fileUploadNo";}

    return "redirect:/message/fileUploadOk";
  }
  @PostMapping("/multiUpload")
  public String multiUploadPost(MultipartHttpServletRequest mFiles) {
    try {
      List<MultipartFile> fileList = mFiles.getFiles("mFile");
      for(MultipartFile file : fileList) {
        String sFileName = new SimpleDateFormat("yyMMddHHmmss").format(new Date())+"_"+file.getOriginalFilename();
        pdsService.writeFile(file, sFileName, "uploadTest");
      }
    } catch (IOException e) {return "redirect:/message/fileUploadNo";}
    return "redirect:/message/fileUploadOk";
  }
  @GetMapping("/fileList")
  public String fileListGet(HttpServletRequest request, Model model) {
    String realPath = request.getSession().getServletContext().getRealPath("/work/uploadTest/");
    String[] files = new File(realPath).list();

    model.addAttribute("files", files);
    model.addAttribute("fileLength", files==null?0:files.length);
    return "pds/fileList";
  }
  // 파일 삭제처리(선택파일)
  @ResponseBody
  @PostMapping("/fileSelectDelete")
  public int fileSelectDeleteGet(HttpServletRequest request, String delItems) {
    String realPath = request.getServletContext().getRealPath("/work/uploadTest/");
    delItems = delItems.substring(0, delItems.length()-1);
    String[] fileNames = delItems.split("/");

    try {
      for(String fileName : fileNames) {
        String realPathFile = realPath + fileName;
        new File(realPathFile).delete();
      }
    } catch (Exception e) {return 0;}
    return 1;
  }
}
