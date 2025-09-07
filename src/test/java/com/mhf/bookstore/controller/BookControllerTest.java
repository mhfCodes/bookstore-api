package com.mhf.bookstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mhf.bookstore.controller.book.BookController;
import com.mhf.bookstore.dto.book.BookDto;
import com.mhf.bookstore.model.book.Status;
import com.mhf.bookstore.service.book.IBookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IBookService iBookService;

    @Test
    public void testCreateBook_ValidDTO() throws Exception {

        BookDto bookDto = new BookDto("New Book", "Author", 29.99, Status.AVAILABLE);
        BookDto createdBookDto = new BookDto(1L, "New Book", "Author", 29.99, Status.AVAILABLE);

        when(iBookService.createBook(any(BookDto.class))).thenReturn(createdBookDto);

        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(bookDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("New Book"));

    }

    @Test
    public void testCreateBook_InvalidDTO() throws Exception {

        BookDto invalidBookDto = new BookDto("", "", -10.0, null);

        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(invalidBookDto)))
                .andExpect(status().isBadRequest());

    }


}
