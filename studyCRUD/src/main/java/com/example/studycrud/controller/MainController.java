package com.example.studycrud.controller;


import com.example.studycrud.dto.MainDTO;
import com.example.studycrud.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/main/*")
public class MainController {

    @Autowired
    MainService mainService;


    @GetMapping("/main")
    public ModelAndView mainPage(ModelAndView mv){

        //  List<MainDTO> findMenu = mainService.findMenu();
        mv.setViewName("/main/main");
        return mv;
    }

}
