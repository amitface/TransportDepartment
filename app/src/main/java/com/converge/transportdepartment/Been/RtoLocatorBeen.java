package com.converge.transportdepartment.Been;

import java.util.List;

/**
 * Created by converge on 21/7/16.
 */
public class RtoLocatorBeen {

    private String title, genre, year;

    public RtoLocatorBeen() {
    }

    public RtoLocatorBeen(String title) {
        this.title = title;
//        this.genre = genre;
//        this.year = year;
    }

    public RtoLocatorBeen(List<String> strings) {
        for (String S :strings) {
            this.title = S;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

//    public String getYear() {
//        return year;
//    }
//
//    public void setYear(String year) {
//        this.year = year;
//    }
//
//    public String getGenre() {
//        return genre;
//    }
//
//    public void setGenre(String genre) {
//        this.genre = genre;
//    }
}