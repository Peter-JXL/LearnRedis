package com.peterjxl.service;

import com.peterjxl.domain.Province;

import java.util.List;

public interface ProvinceService {
    List<Province> findAll();

    String findAllJson();

}
