package com.example.projectdevops.controllers;

import com.example.projectdevops.models.Author;
import com.example.projectdevops.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/author")
@CrossOrigin(origins = "*")
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @GetMapping("/all")
    public List<Author> getAuthors(){
        List<Author> authors = this.authorService.getAllAuthors();
        return authors;
    }

    @GetMapping("/{authorId}")
    public Author getAuthorById(@PathVariable("authorId") Long authorId)
    {
        Author author = this.authorService.getAuthorById(authorId);
        return author;
    }

    @PostMapping("/create")
    public Author createNewAuthor(@RequestBody Author newAuthor)
    {
        Author author = this.authorService.createAuthor(newAuthor);
        return author;
    }
}
