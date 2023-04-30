package com.peterjxl.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.peterjxl.dao.ProvinceDao;
import com.peterjxl.dao.impl.ProvinceDaoImpl;
import com.peterjxl.domain.Province;
import com.peterjxl.service.ProvinceService;
import com.peterjxl.util.JedisPoolUtils;
import redis.clients.jedis.Jedis;

import java.util.List;

public class ProvinceServiceImpl implements ProvinceService {

    private ProvinceDao dao = new ProvinceDaoImpl();
    @Override
    public List<Province> findAll() {
        return dao.findAll();
    }

    @Override
    public String findAllJson() {
        Jedis jedis = JedisPoolUtils.getJedis();
        String province_json = jedis.get("province");
        if(null == province_json || 0 == province_json.length()){
            System.out.println("redis中没有数据，查询数据库");
            List<Province> list = dao.findAll();
            ObjectMapper mapper = new ObjectMapper();
            try {
                province_json = mapper.writeValueAsString(list);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            jedis.set("province",province_json);
            jedis.close();
        }else {
            System.out.println("redis中有数据，查询缓存");
        }

        return province_json;
    }
}
