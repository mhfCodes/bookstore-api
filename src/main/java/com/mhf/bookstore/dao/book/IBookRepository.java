package com.mhf.bookstore.dao.book;

import com.mhf.bookstore.model.book.Book;
import com.mhf.bookstore.model.book.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBookRepository extends JpaRepository<Book, Long> {

    List<Book> findByTitleContainingIgnoreCase(String title);

    List<Book> findByStatus(Status status);

}
