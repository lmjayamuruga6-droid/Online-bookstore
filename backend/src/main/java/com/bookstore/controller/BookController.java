package com.bookstore.controller;
import com.bookstore.model.Book;
import com.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/books") @CrossOrigin(origins = "http://localhost:3000")
public class BookController {
    @Autowired private BookService bookService;

    @GetMapping
    public ResponseEntity<List<Book>> getAll() { return ResponseEntity.ok(bookService.getAllBooks()); }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getOne(@PathVariable Long id) { return ResponseEntity.ok(bookService.getBookById(id)); }
}