package com.example.studycrud.dao;


import com.example.studycrud.dto.MainDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MainDAO {
     List<MainDTO> findAllMenu();
}
