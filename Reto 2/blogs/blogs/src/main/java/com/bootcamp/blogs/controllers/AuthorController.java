package com.bootcamp.blogs.controllers;


import com.bootcamp.blogs.entities.Author;
import com.bootcamp.blogs.services.AuthorService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.swagger.annotations.Api;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.swing.text.html.Option;
import javax.validation.Valid;


@RestController
@RequestMapping("authors")
@Api (value = "REST información de Author")
public class AuthorController {
    @Autowired
    private AuthorService service;

    @ApiOperation("Listado de Autores")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Author>> findAll(){

        try {
            List<Author> authors = new ArrayList<Author>();
            authors = service.findAll();
            return  new ResponseEntity<List<Author>>(authors, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<List<Author>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation("Guardar Autores")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveAuthor(@Valid @RequestBody Author author){
        try {
            Author _author = new Author();
            _author = service.save(author);
            if (_author == null) {
                return  ResponseEntity.badRequest().body("Solo pueden tener blogs los autores mayores de 18 años.");
            }
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(_author.getId()).toUri();
            return ResponseEntity.created(location).build();
        }catch (Exception e){
            return new ResponseEntity<Author>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation("Obtener Author por id")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Author> getAuthorById(@PathVariable("id") Integer id){
        try{
            Optional<Author> author = service.findById(id);
            if (!author.isPresent()) {
                return new ResponseEntity<Author>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<Author>(author.get(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<Author>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @ApiOperation("Actualizar author")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Author> update(@Valid @RequestBody Author author){
        try {
            service.update(author);
            return new ResponseEntity<Author>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<Author>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation("Eliminar author")
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteAuthor(@PathVariable("id") Integer id){
        try {
            Optional<Author> author = service.findById(id);

            if(!author.isPresent()){
                return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
            }else{
                service.deleteById(id);
                return new ResponseEntity<String>("Se elimino con exito al author", HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation("Eliminar todos los authores")
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteAllAuthor(){
        try {
            service.deleteAll();
            return  new ResponseEntity<String>("Se elimino todos los authores", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





}
