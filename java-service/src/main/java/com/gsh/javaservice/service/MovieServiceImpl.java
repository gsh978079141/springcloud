//package com.gsh.javaservice.service;
//
//import com.gsh.javaservice.model.Movie;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.stereotype.Service;
//
///**
//    * @Title: MovieServiceImpl
//    * @Package com.gsh.javaservice.service
//    * @Description:
//    * @author gsh
//    * @date 2018/7/25 16:40
//    */
//@Service
//public class MovieServiceImpl implements  MovieService{
//    @Autowired
//    private MongoTemplate mongoTemplate;
//
//    @Autowired
//    MovieRepository movieRepository;
//
//    @Override
//    public void save(Movie movie) {
//        mongoTemplate.save(movie);
//    }
//
//    @Override
//    public Movie findByMovieName(String movieName) {
//        return movieRepository.findByMovieName(movieName);
//    }
//}
