package com.bootcamp.blogs.controllers;

import com.bootcamp.blogs.entities.Post;
import com.bootcamp.blogs.services.PostService;
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
@RequestMapping("Posts")
@Api(value = "REST información de Post")
public class PostController {

    @Autowired
    private PostService service;

    @ApiOperation("Listado de Post")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Post>> findAll(){

        try {
            List<Post> Posts = new ArrayList<Post>();
            Posts = service.findAll();
            return  new ResponseEntity<List<Post>>(Posts, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<List<Post>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation("Guardar post")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> savePost(@Valid @RequestBody Post post){
        try {
            Post _post = new Post();
            _post = service.save(post);

            if(_post ==  null){
                return ResponseEntity.badRequest().body("Solo se puede publicar un post por día.\n Solo se puede registrar posts en blogs en estado activo.");
            }
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(_post.getId()).toUri();
            return ResponseEntity.created(location).build();
        }catch (Exception e){
            return new ResponseEntity<Post>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation("Obetener Post por id")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Post> getPostById(@PathVariable("id") Integer id){
        try{
            Optional<Post> _Post = service.findById(id);
            if (!_Post.isPresent()) {
                return new ResponseEntity<Post>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<Post>(_Post.get(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<Post>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @ApiOperation("Actualizar Post")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Post> update(@Valid @RequestBody Post _Post){
        try {
            service.update(_Post);
            return new ResponseEntity<Post>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<Post>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation("Eliminar Post")
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deletePost(@PathVariable("id") Integer id){
        try {
            Optional<Post> _Post = service.findById(id);

            if(!_Post.isPresent()){
                return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
            }else{
                service.deleteById(id);
                return new ResponseEntity<String>("Se elimino con exito al Post", HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation("Eliminar todos los Posts")
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteAllPost(){
        try {
            service.deleteAll();
            return  new ResponseEntity<String>("Se elimino todos los Postes", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
