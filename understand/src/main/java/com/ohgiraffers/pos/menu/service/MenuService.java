package com.ohgiraffers.pos.menu.service;

import com.ohgiraffers.pos.menu.dto.MenuDTO;
import com.ohgiraffers.pos.menu.model.MenuDAO;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;

@Service
public class MenuService {

    private MenuDAO menuDAO;

    public MenuService(MenuDAO menuDAO) {
        this.menuDAO = menuDAO;
    }

    public List<MenuDTO> selectAllMenu() {  /*select 진행순서 3번*/
        List<MenuDTO> menus = menuDAO.selectAllMenu();
        if(Objects.isNull(menus)){              // 반환된 결과 없음     쿼리문에서 에러 발생 체크
            System.out.println("exeception menus가 없네요 ");
        }
        // if(menus.size <=0   새로운 db 일수 있음 에러는 아님
        return menus;
    }


    public int regist(MenuDTO menuDTO) {
        int menus = menuDAO.regist(menuDTO);  // insert 쿼리문을 진행시킴 - 결과값 1 반환 진행순서 3번
        if(menus==0){
            System.out.println("exeception menus가 없네요 ");
        }
        return menus;
    }

    public int update(MenuDTO menuDTO) {
        int menus = menuDAO.update(menuDTO);
        if(menus==0){
            System.out.println("exeception menus가 없네요 ");
        }
        return menus;
    }


    public int delete(MenuDTO menuDTO) {
        int menus = menuDAO.delete(menuDTO);
        if(menus==0){
            System.out.println("exeception menus가 없네요 ");
        }
        return menus;
    }
}
