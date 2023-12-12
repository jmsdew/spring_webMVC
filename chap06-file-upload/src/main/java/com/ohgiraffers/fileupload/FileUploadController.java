package com.ohgiraffers.fileupload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller

public class FileUploadController {

    @Autowired
    private ResourceLoader resourceLoader;



/*
    @PostMapping("single-file") // 파일 등록 로직은 보통 서비스에서 함
    public String singleFileUpload(@RequestParam MultipartFile singleFile, String singleFileDescription, Model model) throws IOException {
        System.out.println("single file : " + singleFile);
        System.out.println("원본파일이름" + singleFile.getOriginalFilename());         
        System.out.println("input name 이름" + singleFile.getName());
      //  System.out.println("원본파일객체" + singleFile.getBytes());     // 컴퓨터가 인식하고 있는 실제 주소값      
        System.out.println("원본파일사이즈" + singleFile.getSize());    // 파일 크기. 사이즈가 클 수록 서버에 부하가 걸림 ( 최대사이즈 지정해서 많이 씀)

        // 파일을 저장할 경로 설정    // 실제로는 따로 이미지서버를 이용해서 배포하는 형식으로 FTP서버(파일서버),NODE.js(자바스크립트서버) 등등
*//*        String root = "c:/upload-files";
        String filePath = root + "/single";*//*
        String filePath = resourceLoader.getResource("classpath:/static/img/").getFile().getAbsolutePath();

        File dir = new File(filePath);
        System.out.println(dir.getAbsolutePath());

        if(!dir.exists()){           // 경로가 없으면 디렉토리를 추가 하겠다는 if문
            dir.mkdirs();  // 폴더를 다수 만들어서 s가 붙음 하나만 만들 때 는 dir.mkdir
        }

        String originFileName = singleFile.getOriginalFilename();
        String ext = originFileName.substring(originFileName.lastIndexOf("."));  // dwqdq.jpg 확장자 자름
        String savedName = UUID.randomUUID().toString().replace("-","")+ext;
                            // 중복되지않는 난수 생성                   하이픈 지워버리고 대체    확장자를 붙힘
        System.out.println("singleFileDescription : " + singleFileDescription);

        try {
            System.out.println("filePath =========================" + filePath);
            singleFile.transferTo(new File(filePath + "/" + savedName));
            model.addAttribute("message", "파일 업로드 성공");
            model.addAttribute("img","static/img/" + savedName);
        }catch (IOException e){
            e.printStackTrace();
            model.addAttribute("message" , "파일 업로드 실패");
        }

        return "result";
    }

    @PostMapping("multi-file")
    public String multiFileUpload(@RequestParam List<MultipartFile> multipartFiles,
                                  String multiFileDescription, Model model){
        System.out.println("multiFiles : " + multipartFiles);
        System.out.println("multiFileDescription : " + multiFileDescription);

        String root = "c:/upload-files";
        String filePath = root + "/multi";

        File dir = new File(filePath);

        if (!dir.exists()){
            dir.mkdirs();
        }
        
        List<FileDTO> files = new ArrayList<>();
        try {
            for (MultipartFile file:multipartFiles) {
                String originFileName = file.getOriginalFilename();
                String ext = originFileName.substring(originFileName.lastIndexOf("."));
                String savedName = UUID.randomUUID().toString().replace("-","")+ext;
                files.add(new FileDTO(originFileName,savedName,filePath,multiFileDescription));
                file.transferTo(new File(filePath,"/"+savedName));
                }
            model.addAttribute("message", "다중 파일 업로드 성공");
        }catch (IOException e) {
            e.printStackTrace();

            for (FileDTO file:files) {
                new File(filePath+"/"+file.getSavedName()).delete();
            }
            model.addAttribute("message", "다중 파일 업로드 실패");
        }

        return "result";

    }*/

    @PostMapping("single-file")
    public String singleFileUpload(@RequestParam MultipartFile singleFile, String singleFileDescription, Model model) throws IOException {
        System.out.println("singleFile : " + singleFile);
        System.out.println("singleFileDescription : " + singleFileDescription);

        /* 인텔리제이에서 root폴더를 지정하고 filPath에서 경로를 다르게 잡아서 절대경로로 변경*/
//		String root = "c:/upload-files";
//		String filePath = root + "/single";


        /* 파일을 저장할 경로 설정 */
        Resource resource = resourceLoader.getResource("classpath:static/img/single");
        String filePath = null;

        if (!resource.exists()) {
            // 만약 static 폴더에 파일이 없는 경우 만들어 준다.
            // 초기 해당 디렉토리가 없는 경우 서버 리로드해야 한다.
            // spring에서 resources를 읽어와야 하는데 해당 경로가 없어 이미지를 초기에 보여줄 수 없는 상황이다.
            String root = "src/main/resources/static/img/single";
            File file = new File(root);
            file.mkdirs();

            filePath = file.getAbsolutePath();
        } else {
            filePath = resourceLoader.getResource("classpath:static/img/single").getFile().getAbsolutePath();
        }

        /* 파일명 변경 처리 */
        String originFileName = singleFile.getOriginalFilename();
        String ext = originFileName.substring(originFileName.lastIndexOf("."));
        String savedName = UUID.randomUUID().toString().replace("-", "") + ext;

        /* 파일을 저장 */
        try {
            singleFile.transferTo(new File(filePath + "/" + savedName));
            model.addAttribute("message", "파일 업로드 성공!");
            model.addAttribute("img", "static/img/single/" + savedName);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "파일 업로드 실패!!");
        }

        return "result";
    }

    @PostMapping("multi-file")
    public String multiFileUpload(@RequestParam List<MultipartFile> multiFiles,
                                  String multiFileDescription, Model model) throws IOException {

        System.out.println("multiFiles : " + multiFiles);
        System.out.println("multiFileDescription : " + multiFileDescription);

        /* 파일을 저장할 경로 설정 */
        /* 인텔리제이에서 root폴더를 지정하고 filPath에서 경로를 다르게 잡아서 절대경로로 변경*/
//		String root = "src/main/resources/static";
//		String root = "c:/upload-files";



        /* 파일을 저장할 경로 설정 */
        Resource resource = resourceLoader.getResource("classpath:static/img/multi");
        String filePath = null;

        if (!resource.exists()) {
            // 만약 static 폴더에 파일이 없는 경우 만들어 준다.
            // 초기 해당 디렉토리가 없는 경우 서버 리로드해야 한다.
            // spring에서 resources를 읽어와야 하는데 해당 경로가 없어 이미지를 초기에 보여줄 수 없는 상황이다.
            String root = "src/main/resources/static/img/multi";
            File file = new File(root);
            file.mkdirs();

            filePath = file.getAbsolutePath();
        } else {
            filePath = resourceLoader.getResource("classpath:static/img/multi").getFile().getAbsolutePath();
        }
        System.out.println("multi : " + filePath);

        List<FileDTO> files = new ArrayList<>();
        List<String> saveFiles = new ArrayList<>();
        try {

            for (MultipartFile file : multiFiles) {
                /* 파일명 변경 처리 */
                String originFileName = file.getOriginalFilename();
                String ext = originFileName.substring(originFileName.lastIndexOf("."));
                String savedName = UUID.randomUUID().toString().replace("-", "") + ext;

                /* 파일에 관한 정보 추출 후 보관 */
                files.add(new FileDTO(originFileName, savedName, filePath, multiFileDescription));
                /* 파일을 저장 */
                file.transferTo(new File(filePath + "/" + savedName));
                saveFiles.add("static/img/multi/" + savedName);
            }
            model.addAttribute("message", "파일 업로드 성공!");
            model.addAttribute("imgs", saveFiles);

        } catch (Exception e) {
            e.printStackTrace();

            /* 실패 시 이전에 저장 된 파일 삭제 */
            for (FileDTO file : files) {
                new File(filePath + "/" + file.getSavedName()).delete();
            }

            model.addAttribute("message", "파일 업로드 실패!!");
        }

        return "result";
    }
}
