package com.mhf.bookstore.service.book;

import com.mhf.bookstore.dto.book.BookDto;

import java.util.List;

public interface IBookService {

    BookDto createBook(BookDto bookDto);

    BookDto getBookById(Long id);

    List<BookDto> listAllBooks();

    BookDto updateBook(Long id, BookDto bookDto);

    void deleteBook(Long id);

    BookDto markAsDiscontinued(Long id);
}
