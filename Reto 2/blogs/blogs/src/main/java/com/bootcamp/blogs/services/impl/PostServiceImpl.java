package com.bootcamp.blogs.services.impl;

import com.bootcamp.blogs.entities.Post;
import com.bootcamp.blogs.repositories.BlogRepository;
import com.bootcamp.blogs.repositories.PostRepository;
import com.bootcamp.blogs.services.BlogService;
import com.bootcamp.blogs.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService  {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private BlogService  blogService;

    @Transactional(readOnly = true)
    @Override
    public List<Post> findAll() throws Exception {
        return postRepository.findAll();
    }

    @Transactional
    @Override
    public Post save(Post post) throws Exception {
        Date date = new Date();
        //busca post publicados en el dia
        var flagDate = postRepository.findAll().stream().filter(o -> o.getBlog().getId() == post.getBlog().getId()).anyMatch(o -> o.getDate().getTime() == post.getDate().getTime());

        //busca el estado del blog
        var idBlog = post.getBlog().getId();
        var statusBlog = blogService.findById(idBlog).get().getStatus();

        if(statusBlog == "activo" && (flagDate == false)){
            return postRepository.save(post);
        }else {
            return null;
        }
    }

    @Transactional
    @Override
    public Post update(Post post) throws Exception {


            return postRepository.save(post);

    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Post> findById(Integer id) throws Exception {
        return postRepository.findById(id);
    }

    @Transactional
    @Override
    public void deleteById(Integer id) throws Exception {
        postRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteAll() throws Exception {
        postRepository.deleteAll();
    }
}
