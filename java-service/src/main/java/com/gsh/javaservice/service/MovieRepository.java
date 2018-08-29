package com.gsh.javaservice.service;

import com.gsh.javaservice.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
    * @Title: DoubanService
    * @Package com.gsh.javaservice.service
    * @Description: 豆瓣电影网服务实现接口
    * @author gsh
    * @date 2018/7/25 16:14
    */
@Repository
public interface MovieRepository extends MongoRepository<Movie,String> {
     /**
      *
      * @param movieName
      * @return
      */
     Movie findByMovieName(String movieName);
}
