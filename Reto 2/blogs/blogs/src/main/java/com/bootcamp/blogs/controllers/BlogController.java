package com.bootcamp.blogs.controllers;


import com.bootcamp.blogs.entities.Blog;
import com.bootcamp.blogs.services.BlogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("Blogs")
@Api(value = "REST información de blog")
public class BlogController {

    @Autowired
    private BlogService service;

    @ApiOperation("Listado de Blog")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Blog>> findAll(){

        try {
            List<Blog> Blogs = new ArrayList<Blog>();
            Blogs = service.findAll();
            return  new ResponseEntity<List<Blog>>(Blogs, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<List<Blog>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation("Guardar Blog")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveBlog(@Valid @RequestBody Blog blog){
        try {
            Blog _blog = new Blog();
            _blog = service.save(blog);

            if(_blog == null){
                return  ResponseEntity.badRequest().body("Un autor puede tener máximo 03 blogs.");
            }

            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(_blog.getId()).toUri();
            return ResponseEntity.created(location).build();
        }catch (Exception e){
            return new ResponseEntity<Blog>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation("Obetener Blog por id")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Blog> getBlogById(@PathVariable("id") Integer id){
        try{
            Optional<Blog> _Blog = service.findById(id);
            if (!_Blog.isPresent()) {
                return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<Blog>(_Blog.get(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<Blog>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @ApiOperation("Actualizar Blog")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Blog> update(@Valid @RequestBody Blog _Blog){
        try {
            service.update(_Blog);
            return new ResponseEntity<Blog>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<Blog>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation("Eliminar Blog")
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteBlog(@PathVariable("id") Integer id){
        try {
            Optional<Blog> _Blog = service.findById(id);

            if(!_Blog.isPresent()){
                return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
            }else{
                service.deleteById(id);
                return new ResponseEntity<String>("Se elimino con exito al Blog", HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation("Eliminar todos los Blogs")
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteAllBlog(){
        try {
            service.deleteAll();
            return  new ResponseEntity<String>("Se elimino todos los Bloges", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
