package com.gsh.javaservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
    * @Title: Movie
    * @Package com.gsh.javaservice.model
    * @Description:  电影实体类
    * @author gsh
    * @date 2018/7/25 16:19
    */
@Document(collection="movie")
public class Movie  implements Serializable {
    @Id
    private String id;
    private String serialNumber;
    private String movieName;
    private String introduce;
    private double star;
    private String evaluate;
    private String describe;

    public Movie() {
    }

    public Movie(String id, String serialNumber, String movieName, String introduce, double star, String evaluate, String describe) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.movieName = movieName;
        this.introduce = introduce;
        this.star = star;
        this.evaluate = evaluate;
        this.describe = describe;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public double getStar() {
        return star;
    }

    public void setStar(double star) {
        this.star = star;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id='" + id + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", movieName='" + movieName + '\'' +
                ", introduce='" + introduce + '\'' +
                ", star=" + star +
                ", evaluate='" + evaluate + '\'' +
                ", describe='" + describe + '\'' +
                '}';
    }
}
