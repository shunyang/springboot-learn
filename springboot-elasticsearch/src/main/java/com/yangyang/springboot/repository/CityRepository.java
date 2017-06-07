package com.yangyang.springboot.repository;

import com.yangyang.springboot.domain.City;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends ElasticsearchRepository<City,Long> {


}
