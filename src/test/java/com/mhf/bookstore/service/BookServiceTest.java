package com.mhf.bookstore.service;

import com.mhf.bookstore.dao.book.IBookRepository;
import com.mhf.bookstore.dto.book.BookDto;
import com.mhf.bookstore.exception.ResourceNotFoundException;
import com.mhf.bookstore.mapper.book.BookMapper;
import com.mhf.bookstore.model.book.Book;
import com.mhf.bookstore.model.book.Status;
import com.mhf.bookstore.service.impl.book.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private IBookRepository iBookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book book;
    private BookDto bookDto;

    @BeforeEach
    public void setUp() {
        book = new Book(1L, "The Best Book", "Mammad", 24.0, Status.AVAILABLE);
        bookDto = new BookDto(1L, "The Best Book", "Mammad", 24.0, Status.AVAILABLE);
    }

    @Test
    public void testCreateBook_defaultStatus() {

        Book bookToSave = new Book(null, "New Book", "Author", 20.0, null);
        BookDto bookDtoToSave = new BookDto(null, "New Book", "Author", 20.0, null);
        Book savedBook = new Book(2L, "New Book", "Author", 20.0, Status.AVAILABLE);
        BookDto savedBookDto = new BookDto(2L, "New Book", "Author", 20.0, Status.AVAILABLE);

        when(bookMapper.toEntity(bookDtoToSave)).thenReturn(bookToSave);
        when(iBookRepository.save(bookToSave)).thenReturn(savedBook);
        when(bookMapper.toDto(savedBook)).thenReturn(savedBookDto);

        BookDto result = bookService.createBook(bookDtoToSave);

        assertNotNull(result);
        assertEquals(Status.AVAILABLE, result.getStatus());
        assertEquals("New Book", result.getTitle());

    }

    @Test
    public void testGetBookById_bookExists() {

        when(iBookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto result = bookService.getBookById(1L);

        assertNotNull(result);
        assertEquals("The Best Book", result.getTitle());

    }

    @Test
    public void testGetBookById_bookNotFound() {

        when(iBookRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookService.getBookById(99L));

    }

    @Test
    public void testListAllBooks() {

        List<Book> books = Arrays.asList(book);
        List<BookDto> bookDtos = Arrays.asList(bookDto);

        when(iBookRepository.findAll()).thenReturn(books);
        when(bookMapper.toDtoList(books)).thenReturn(bookDtos);

        List<BookDto> result = bookService.listAllBooks();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("The Best Book", result.get(0).getTitle());

    }

    @Test
    public void testUpdateBook() {

        Book updatedBook = new Book(1L, "Updated Book", "Author", 19.99, Status.AVAILABLE);
        BookDto updatedBookDto = new BookDto(1L, "Updated Book", "Author", 19.99, Status.AVAILABLE);

        when(iBookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(iBookRepository.save(argThat(b -> b.getId().equals(1L)))).thenReturn(updatedBook);
        when(bookMapper.toDto(updatedBook)).thenReturn(updatedBookDto);

        BookDto result = bookService.updateBook(1L, updatedBookDto);

        assertNotNull(result);
        assertEquals("Updated Book", result.getTitle());
        assertEquals(19.99, result.getPrice());

    }

    @Test
    public void testUpdateBook_bookNotFound() {

        BookDto updatedBookDto = new BookDto(1L, "Updated Book", "Author", 19.99, Status.AVAILABLE);

        when(iBookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookService.updateBook(1L, updatedBookDto));

    }

    @Test
    public void testDeleteBook() {

        when(iBookRepository.findById(1L)).thenReturn(Optional.of(book));

        bookService.deleteBook(1L);

        verify(iBookRepository).delete(book);

    }

    @Test
    public void testDeleteBook_bookNotFound() {

        when (iBookRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookService.deleteBook(99L));

    }

    @Test
    public void testMarkAsDiscontinued() {

        Book updatedBook = new Book(1L, "The Best Book", "Mammad", 24.0, Status.DISCONTINUED);
        BookDto updatedBookDto = new BookDto(1L, "The Best Book", "Mammad", 24.0, Status.DISCONTINUED);

        when(iBookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(iBookRepository.save(any(Book.class))).thenReturn(updatedBook);
        when(bookMapper.toDto(updatedBook)).thenReturn(updatedBookDto);

        BookDto result = bookService.markAsDiscontinued(1L);

        assertNotNull(result);
        assertEquals(Status.DISCONTINUED, result.getStatus());

    }

    @Test
    public void testMarkAsDiscontinued_bookNotFound() {

        when(iBookRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookService.markAsDiscontinued(99L));

    }

}
