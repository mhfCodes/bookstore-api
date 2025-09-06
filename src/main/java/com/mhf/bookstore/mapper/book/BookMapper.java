package com.mhf.bookstore.mapper.book;

import com.mhf.bookstore.dto.book.BookDto;
import com.mhf.bookstore.model.book.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDto toDto(Book entity);

    Book toEntity(BookDto dto);

}
