package com.gsh.javaservice.controller;

import com.gsh.javaservice.model.Movie;
import com.gsh.javaservice.service.MovieRepository;
import com.gsh.javaservice.service.JavaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class JavaController {
    @Autowired
    JavaService javaService;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    MongoTemplate mongoTemplate;
    @RequestMapping("/java-user")
    public String javaUser() {
        return "{'username': 'java', 'password': 'java'}"  ;
    }
    @RequestMapping("/python-user")
    public String pythonUser() {
        return  javaService.getPython();
    }

    @RequestMapping("/getMovieList")
    public List<Movie> getMovieList(){
        return mongoTemplate.findAll(Movie.class); }
    @RequestMapping("/getMovieByName")
    public Movie getMovieByName(String movieName){
        System.out.println(movieName);
        return movieRepository.findByMovieName(movieName);
    }

}
