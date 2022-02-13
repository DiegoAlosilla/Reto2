package com.bootcamp.blogs.services.impl;

import com.bootcamp.blogs.entities.Blog;
import com.bootcamp.blogs.repositories.BlogRepository;
import com.bootcamp.blogs.services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BlogServiceImpl implements BlogService {


    @Autowired
    private BlogRepository blogRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Blog> findAll() throws Exception {
        return blogRepository.findAll();
    }

    @Transactional
    @Override
    public Blog save(Blog blog) throws Exception {

        var countBlogAuthor = blogRepository.findAll().stream().filter(o -> o.getAuthor().getId() == blog.getAuthor().getId()).count();

        if(countBlogAuthor < 3){
            return blogRepository.save(blog);
        }else{
            return null;
        }


    }

    @Transactional
    @Override
    public Blog update(Blog blog) throws Exception {
        return blogRepository.save(blog);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Blog> findById(Integer id) throws Exception {
        return blogRepository.findById(id);
    }

    @Transactional
    @Override
    public void deleteById(Integer id) throws Exception {
        blogRepository.deleteById(id);
    }
    @Transactional
    @Override
    public void deleteAll() throws Exception {
        blogRepository.deleteAll();
    }
}
