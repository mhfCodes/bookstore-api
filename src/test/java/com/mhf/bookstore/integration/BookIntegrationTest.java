package com.mhf.bookstore.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mhf.bookstore.dao.book.IBookRepository;
import com.mhf.bookstore.dto.book.BookDto;
import com.mhf.bookstore.model.book.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IBookRepository iBookRepository;

    private String baseUrl;

    @BeforeEach
    public void setUp() {
        baseUrl = "http://localhost:" + port + "/api/books";
        iBookRepository.deleteAll();
    }

    @Test
    public void testCreateBook() {

        BookDto newBook = new BookDto(null, "Integration Book", "Author", 20.0, Status.AVAILABLE);
        ResponseEntity<BookDto> response = testRestTemplate.postForEntity(baseUrl, newBook, BookDto.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        BookDto created = response.getBody();
        assertThat(created).isNotNull();
        assertThat(created.getId()).isNotNull();
        assertThat(created.getStatus()).isEqualTo(Status.AVAILABLE);
        assertThat(iBookRepository.count()).isEqualTo(1);

    }

    @Test
    public void testGetBookById() {

        BookDto book = createBookInDb("Get Book", Status.AVAILABLE);
        ResponseEntity<BookDto> response = testRestTemplate.getForEntity(baseUrl + "/" + book.getId(), BookDto.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getTitle()).isEqualTo("Get Book");

    }

    // Helper method to create a book directly in DB
    private BookDto createBookInDb(String title, Status status) {
        BookDto book = new BookDto(null, title, "Author", 20.0, status);
        ResponseEntity<BookDto> response = testRestTemplate.postForEntity(baseUrl, book, BookDto.class);
        return response.getBody();
    }

}
