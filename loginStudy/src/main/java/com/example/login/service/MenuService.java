package com.example.login.service;

import com.example.login.dto.MenuDTO;
import com.example.login.model.MenuDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class MenuService {

    private MenuDAO menuDAO;

    public MenuService(MenuDAO menuDAO) {
        this.menuDAO = menuDAO;
    }

    public List<MenuDTO> selectAllMenu() {
        List<MenuDTO> menus = menuDAO.selectAllMenu();

        if(Objects.isNull(menus)){
            System.out.println("exeception menus가 없네요 ");
        }
        return menus;
    }
}
