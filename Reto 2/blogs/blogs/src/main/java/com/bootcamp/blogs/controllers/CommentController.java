package com.bootcamp.blogs.controllers;


import com.bootcamp.blogs.entities.Comment;
import com.bootcamp.blogs.services.CommentService;
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
@RequestMapping("Comments")
@Api(value = "REST información de Comment")
public class CommentController {


    @Autowired
    private CommentService service;

    @ApiOperation("Listado de Comment")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Comment>> findAll(){

        try {
            List<Comment> Comments = new ArrayList<Comment>();
            Comments = service.findAll();
            return  new ResponseEntity<List<Comment>>(Comments, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<List<Comment>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation("Guardar Autores")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Comment> saveComment(@Valid @RequestBody Comment _Comment){
        try {
            Comment __Comment = new Comment();
            __Comment = service.save(_Comment);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(__Comment.getId()).toUri();
            return ResponseEntity.created(location).build();
        }catch (Exception e){
            return new ResponseEntity<Comment>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation("Obetener Comment por id")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Comment> getCommentById(@PathVariable("id") Integer id){
        try{
            Optional<Comment> _Comment = service.findById(id);
            if (!_Comment.isPresent()) {
                return new ResponseEntity<Comment>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<Comment>(_Comment.get(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<Comment>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @ApiOperation("Actualizar Comment")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Comment> update(@Valid @RequestBody Comment _Comment){
        try {
            service.update(_Comment);
            return new ResponseEntity<Comment>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<Comment>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation("Eliminar Comment")
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteComment(@PathVariable("id") Integer id){
        try {
            Optional<Comment> _Comment = service.findById(id);

            if(!_Comment.isPresent()){
                return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
            }else{
                service.deleteById(id);
                return new ResponseEntity<String>("Se elimino con exito al Comment", HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation("Eliminar todos los Comments")
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteAllComment(){
        try {
            service.deleteAll();
            return  new ResponseEntity<String>("Se elimino todos los Commentes", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
