package com.mhf.bookstore.service.impl.book;

import com.mhf.bookstore.dao.book.IBookRepository;
import com.mhf.bookstore.dto.book.BookDto;
import com.mhf.bookstore.mapper.book.BookMapper;
import com.mhf.bookstore.model.book.Book;
import com.mhf.bookstore.model.book.Status;
import com.mhf.bookstore.service.book.IBookService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookServiceImpl implements IBookService {

    private IBookRepository iBookRepository;
    private BookMapper bookMapper;

    @Autowired
    public BookServiceImpl(IBookRepository iBookRepository, BookMapper bookMapper) {
        this.iBookRepository = iBookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public BookDto createBook(BookDto bookDto) {
        Book book = bookMapper.toEntity(bookDto);
        // Default status if not provided
        if (book.getStatus() == null)
            book.setStatus(Status.AVAILABLE);
        Book savedEntity = iBookRepository.save(book);
        return bookMapper.toDto(savedEntity);
    }

    @Override
    public BookDto getBookById(Long id) {
        Book book = iBookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book with id " + id + " does not exist"));
        return bookMapper.toDto(book);
    }

    @Override
    public List<BookDto> listAllBooks() {
        return iBookRepository.findAll()
                .stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookDto updateBook(Long id, BookDto bookDto) {
        Book existingBook = iBookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book with id " + id + " does not exist"));

        existingBook.setTitle(bookDto.getTitle());
        existingBook.setAuthor(bookDto.getAuthor());
        existingBook.setPrice(bookDto.getPrice());
        existingBook.setStatus(bookDto.getStatus());

        Book updated = iBookRepository.save(existingBook);
        return bookMapper.toDto(updated);
    }

    @Override
    public void deleteBook(Long id) {
        Book existingBook = iBookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book with id " + id + " does not exist"));
        iBookRepository.delete(existingBook);
    }

    @Override
    public BookDto markAsDiscontinued(Long id) {
        Book existingBook = iBookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book with id " + id + " does not exist"));
        existingBook.setStatus(Status.DISCONTINUED);
        Book update = iBookRepository.save(existingBook);
        return bookMapper.toDto(update);
    }

}
