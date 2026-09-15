package com.bookstore;
import com.bookstore.controller.BookController;
import com.bookstore.model.Book;
import com.bookstore.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {
    @Autowired MockMvc mockMvc;
    @MockBean BookService bookService;

    @Test
    void shouldReturnListOfBooks() throws Exception {
        List<Book> books = List.of(
            Book.builder().id(1L).title("Clean Code").author("Robert Martin").price(40.0).stock(10).isbn("123").build(),
            Book.builder().id(2L).title("DDD").author("Eric Evans").price(50.0).stock(5).isbn("124").build()
        );
        when(bookService.getAllBooks()).thenReturn(books);
        mockMvc.perform(get("/api/books"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].title").value("Clean Code"));
    }

    @Test
    void shouldReturnSingleBook() throws Exception {
        Book book = Book.builder().id(1L).title("Clean Code").author("Robert Martin").price(40.0).build();
        when(bookService.getBookById(1L)).thenReturn(book);
        mockMvc.perform(get("/api/books/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("Clean Code"));
    }
}