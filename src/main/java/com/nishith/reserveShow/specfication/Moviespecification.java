package com.nishith.reserveShow.specfication;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.nishith.reserveShow.model.Movie;

public class Moviespecification {
 public static Specification<Movie> name(String name)
 {
	 return(root,query,cb) ->cb.equal(root.get("name"),name);
 }
 public static Specification<Movie> date(LocalDate date)
 {
	 return (root,query,cb) ->cb.equal(root.get("date"), date);
 }
 public static Specification<Movie> byid(int id)
 {
	 return (root,query,cb) ->cb.equal(root.get("movieid") , id);
			 
 }
}
