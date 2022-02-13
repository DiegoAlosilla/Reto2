package com.bootcamp.blogs.services.impl;

import com.bootcamp.blogs.entities.Author;
import com.bootcamp.blogs.repositories.AuthorRepository;
import com.bootcamp.blogs.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Author> findAll() throws Exception {
        return authorRepository.findAll();
    }

    @Transactional
    @Override
    public Author save(Author author) throws Exception {
        Date date = new Date();

        var difference_In_Time = Math.abs(date.getTime() - author.getBirthDate().getTime());
        var years = (difference_In_Time / (1000l * 60 * 60 * 24 * 365));

        if (years>17){
            return authorRepository.save(author);
        }else{
            return null;
        }
    }

    @Transactional
    @Override
    public Author update(Author author) throws Exception {
        return authorRepository.save(author);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Author> findById(Integer id) throws Exception {
        return authorRepository.findById(id);
    }

    @Transactional
    @Override
    public void deleteById(Integer id) throws Exception {
        authorRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteAll() throws Exception {
        authorRepository.deleteAll();
    }
}
