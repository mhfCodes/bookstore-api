package com.mhf.bookstore.dao;

import com.mhf.bookstore.dao.book.IBookRepository;
import com.mhf.bookstore.model.book.Book;
import com.mhf.bookstore.model.book.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private IBookRepository iBookRepository;

    private Book book1;
    private Book book2;

    @BeforeEach
    public void setup() {

        book1 = new Book(null, "The Best Book", "Mammad", 24.0, Status.AVAILABLE);
        book2 = new Book(null, "Java Programming 101", "John Doe", 30.0, Status.DISCONTINUED);

        iBookRepository.save(book1);
        iBookRepository.save(book2);
    }

    @Test
    public void testSaveBook() {
        Book book = new Book(null, "Spring Boot for Beginners", "Jane Smith", 19.99, Status.AVAILABLE);
        Book savedBook = iBookRepository.save(book);
        assertNotNull(savedBook.getId());
        assertEquals("Spring Boot for Beginners", savedBook.getTitle());
    }

    @Test
    public void testFindById() {
        Optional<Book> foundBook = iBookRepository.findById(book1.getId());
        assertTrue(foundBook.isPresent());
        assertEquals(book1.getTitle(), foundBook.get().getTitle());
    }

    @Test
    public void testFindAll() {
        List<Book> books = iBookRepository.findAll();
        assertEquals(2, books.size());
    }

    @Test
    public void testDelete() {
        iBookRepository.delete(book2);
        Optional<Book> deletedBook = iBookRepository.findById(book2.getId());
        assertFalse(deletedBook.isPresent());
    }
    
}
