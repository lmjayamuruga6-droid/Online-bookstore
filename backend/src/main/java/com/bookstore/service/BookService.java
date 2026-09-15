package com.bookstore.service;
import com.bookstore.exception.BookNotFoundException;
import com.bookstore.model.Book;
import com.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookService {
    @Autowired private BookRepository bookRepo;

    public List<Book> getAllBooks() { return bookRepo.findAll(); }

    public Book getBookById(Long id) {
        return bookRepo.findById(id)
            .orElseThrow(() -> new BookNotFoundException("Book with id " + id + " not found"));
    }
}