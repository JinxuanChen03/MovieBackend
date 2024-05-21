package com.bjtu.movie.controller;


import com.bjtu.movie.entity.Movie;
import com.bjtu.movie.model.Result;
import com.bjtu.movie.service.impl.MovieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 所有影片信息 前端控制器
 * </p>
 *
 * @author Jinxuan Chen
 * @since 2024-05-20
 */
@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieServiceImpl movieService;

    /**
     * 添加一部电影
     * @param movie
     * @return
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    public ResponseEntity<Result> addNewMovie(@RequestBody Movie movie){
        movieService.addNewMovie(movie);
        return new ResponseEntity<>(Result.success(), HttpStatus.OK);
    }

    /**
     * 更新电影信息
     * @param id
     * @param movie
     * @return
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    public ResponseEntity<Result> updateAMovieInfo(@PathVariable Integer id, @RequestBody Movie movie){
        movie.setId(id);
        movieService.updateAMovieInfo(movie);
        return new ResponseEntity<>(Result.success(), HttpStatus.OK);
    }

    /**
     * 获取全部电影，分页
     * @param pageSize
     * @param currentPage
     * @return
     */
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    public ResponseEntity<Result> getAllMovies(
            @RequestParam(required = false,defaultValue = "1") Integer currentPage,
            @RequestParam(required = false,defaultValue = "2") Integer pageSize){
        return new ResponseEntity<>(Result.success(movieService.getAllMovies(currentPage,pageSize)), HttpStatus.OK);
    }

    /**
     * 根据id获取一部电影
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    public ResponseEntity<Result> getAMovieByID(@PathVariable Integer id){
        return new ResponseEntity<>(Result.success(movieService.getAMovieByID(id)), HttpStatus.OK);
    }

    /**
     * 根据imdb_id获取一部电影
     * @param id
     * @return
     */
    @GetMapping("/imdb/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    public ResponseEntity<Result> getAMovieByImdbID(@PathVariable String id){
        return new ResponseEntity<>(Result.success(movieService.getAMovieByImdbID(id)), HttpStatus.OK);
    }

    /**
     * 根据索引获取电影，分页
     * @param currentPage
     * @param pageSize
     * @param sort
     * @param order
     * @param genres
     * @param startYear
     * @param endYear
     * @param ratingFrom
     * @param ratingTo
     * @param votesFrom
     * @param votesTo
     * @param keywords
     * @return
     */
    @GetMapping("/index")
    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<Result> getMoviesByIndex(
            @RequestParam(required = false,defaultValue = "1") Integer currentPage,
            @RequestParam(required = false,defaultValue = "2") Integer pageSize,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false, defaultValue = "desc") String order,
            @RequestParam(required = false) List<String> genres,
            @RequestParam(required = false) String startYear,
            @RequestParam(required = false) String endYear,
            @RequestParam(required = false) Double ratingFrom,
            @RequestParam(required = false) Double ratingTo,
            @RequestParam(required = false) Integer votesFrom,
            @RequestParam(required = false) Integer votesTo,
            @RequestParam(required = false) List<String> keywords){
        return new ResponseEntity<>(Result.success(
                movieService.getMoviesByIndex(
                        currentPage, pageSize, sort, order, genres, startYear, endYear, ratingFrom, ratingTo, votesFrom, votesTo, keywords)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    public ResponseEntity<Result> deleteAMovie(@PathVariable Integer id){
        movieService.deleteAMovie(id);
        return new ResponseEntity<>(Result.success(), HttpStatus.OK);
    }
}
