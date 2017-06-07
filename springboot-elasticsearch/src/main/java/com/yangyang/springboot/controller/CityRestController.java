package com.yangyang.springboot.controller;


import com.yangyang.springboot.domain.City;
import com.yangyang.springboot.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 城市 Controller 实现 Restful HTTP 服务
 */
@RestController
public class CityRestController {

    @Autowired
    private CityService cityService;

    @RequestMapping(value = "/api/city", method = RequestMethod.POST)
    public Long createCity(@RequestBody City city) {
        return cityService.saveCity(city);
    }


    @RequestMapping(value = "/api/city/{id}", method = RequestMethod.GET)
    public City createCity(@PathVariable Long id) {
        return cityService.findCityById(id);
    }

    @RequestMapping(value = "/api/city/search", method = RequestMethod.GET)
    public List<City> searchCity(@RequestParam(value = "pageNumber",required = false,defaultValue = "1") Integer pageNumber,
                                 @RequestParam(value = "pageSize", required = false,defaultValue = "10") Integer pageSize,
                                 @RequestParam(value = "searchContent",required = false) String searchContent) {
        return cityService.searchCity(pageNumber,pageSize,searchContent);
    }
}
