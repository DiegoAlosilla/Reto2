package com.bootcamp.blogs.services.impl;

import com.bootcamp.blogs.entities.Comment;
import com.bootcamp.blogs.repositories.CommentRepository;
import com.bootcamp.blogs.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService  {

    @Autowired
    private CommentRepository commentRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Comment> findAll() throws Exception {
        return commentRepository.findAll();
    }

    @Transactional
    @Override
    public Comment save(Comment comment) throws Exception {
        return commentRepository.save(comment);
    }

    @Transactional
    @Override
    public Comment update(Comment comment) throws Exception {
        return commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Comment> findById(Integer id) throws Exception {
        return commentRepository.findById(id);
    }

    @Transactional
    @Override
    public void deleteById(Integer id) throws Exception {
        commentRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteAll() throws Exception {
        commentRepository.deleteAll();
    }
}
