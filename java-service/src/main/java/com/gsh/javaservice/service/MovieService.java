package com.gsh.javaservice.service;

import com.gsh.javaservice.model.Movie;

/**
    * @Title: MovieService
    * @Package com.gsh.javaservice.service
    * @Description: 
    * @author gsh
    * @date 2018/7/25 16:39
    */
public interface MovieService {
    void save(Movie movie);
    Movie findByMovieName(String movieName);
}
