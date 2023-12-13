package com.example.login.controller;

import com.example.login.dto.MenuDTO;
import com.example.login.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/")
@SessionAttributes("id")
public class MenuController {

    @Autowired
    public MenuService menuService;

    private ResourceLoader resourceLoader;

    public MenuController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @GetMapping("menus")
    public ModelAndView selectAllMenu(ModelAndView mv){
        List<MenuDTO> menus = menuService.selectAllMenu();
        mv.addObject("menus",menus);
        mv.setViewName("menus/allMenus");
        return mv;
    }
    @PostMapping("image/single-file")
    public String singleFileUpload(@RequestParam MultipartFile singleFile, String singleFileDescription, Model model) throws IOException {
        System.out.println("singleFile : " + singleFile);
        System.out.println("singleFileDescription : " + singleFileDescription);
        /* 파일을 저장할 경로 설정 */
        Resource resource = resourceLoader.getResource("classpath:static/img/single");
        String filePath = null;

        if (!resource.exists()) {

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



}
