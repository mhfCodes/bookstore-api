package com.mhf.bookstore.mapper.book;

import com.mhf.bookstore.dto.book.BookDto;
import com.mhf.bookstore.model.book.Book;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDto toDto(Book entity);

    List<BookDto> toDtoList(List<Book> entityList);

    Book toEntity(BookDto dto);

}
