package com.example.studycrud.service;


import com.example.studycrud.dao.MainDAO;
import com.example.studycrud.dto.MainDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainService {

    @Autowired
    MainDAO mainDAO;
    public List<MainDTO> findMenu() {

        List<MainDTO> findAllMenu = mainDAO.findAllMenu();

        return findAllMenu;

    }
}
