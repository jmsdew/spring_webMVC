package com.ohgiraffers.pos.menu.controller;

import com.ohgiraffers.pos.menu.dto.MenuDTO;
import com.ohgiraffers.pos.menu.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/menus/*")
@SessionAttributes("id")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("menus")   /*select 진행순서 2번 -- service로 보냄 */
    public ModelAndView selectAllMenu(ModelAndView mv){
        List<MenuDTO> menus = menuService.selectAllMenu();          // 쿼리에서는 문제 없으나 그 이후 에러
        // service 에서 담아온 쿼리문 값을 menus 에 넣어줌  진행순서 4번
        if (Objects.isNull(menus)){
            System.out.println("exception 대체");
        }
        mv.addObject("menus",menus);   // view로 보낼 내용 설정
        mv.setViewName("menus/allMenus");          // view 경로 설정

        return mv;  // 보냄
    }

    @GetMapping("regist")
    public void insert(){}
    @PostMapping("regist")  /*regist 진행순서 2번 */
    public ModelAndView insertMenu(ModelAndView mv,MenuDTO menuDTO){
        int regist = menuService.regist(menuDTO);  // service로 보내 결과값 받아옴
        if (regist >= 0){  
            mv.setViewName("/menus/returnMessage");  // view 경로 설정
        }

        return mv;
    }

    @GetMapping("update")
    public void update(){}

    @PostMapping("update")
    public ModelAndView updateMenu(ModelAndView mv, MenuDTO menuDTO){
        int update = menuService.update(menuDTO);
        if (update >= 0){
            mv.setViewName("/menus/returnMessage");
        }

        return mv;
    }

    @GetMapping("delete")
    public void delete(){}

    @PostMapping("delete")
    public ModelAndView deleteMenu(ModelAndView mv, MenuDTO menuDTO){
        int delete = menuService.delete(menuDTO);
        if (delete >= 0){
            mv.setViewName("/menus/returnMessage");
        }

        return mv;
    }



}
