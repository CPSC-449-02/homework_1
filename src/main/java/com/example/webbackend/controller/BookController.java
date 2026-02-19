package com.example.webbackend.controller;


import com.example.webbackend.enitity.Book;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class BookController {

    private List<Book> books = new ArrayList<>();

    private Long id = 1L;

    public BookController() {
        books.add(new Book(id++, "Java","Author 1", 20.0));
        books.add(new Book(id++, "Go","Author 2", 25.0));
        books.add(new Book(id++, "Python","Author 3", 30.0));
        books.add(new Book(id++, "Lua","Author 4", 32.0));
        books.add(new Book(id++, "C++","Author 5", 35.0));
        books.add(new Book(id++, "JavaScript","Author 6", 23.0));
        books.add(new Book(id++, "Sveltekit","Author 7", 55.0));
        books.add(new Book(id++, "React","Author 8", 50.0));
        books.add(new Book(id++, "Rust","Author 9", 10.0));
        books.add(new Book(id++, "Assembly","Author 10", 89.0));
        books.add(new Book(id++, "Haskell","Author 11", 20.0));
        books.add(new Book(id++, "Prolog","Author 12", 33.0));
        books.add(new Book(id++, "HTML","Author 13", 18.0));

    }

    // Get all books - /api/books
    @GetMapping("/books")
    public List<Book> getBooks() {
        return books;
    }

    // Get book by id
    @GetMapping("/books/{id}")
    public Book getBook(@PathVariable Long id) {
        return books.stream().filter(book -> book.getId().equals(id)).
                findFirst().
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
    }

    // Create book
    @PostMapping("/books")
    public Book createBook(@RequestBody Book book) {
        books.add(book);
        return book;
    }

    // PUT endpoint (update book)
    @PutMapping("/books/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book newData) {
        Book existing = books.stream().filter(book -> book.getId().equals(id)).
                findFirst().
                 orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));

        existing.setTitle(newData.getTitle());
        existing.setAuthor(newData.getAuthor());
        existing.setPrice(newData.getPrice());

        return existing;
    }

    // PATCH endpoint (partial update)
    @PatchMapping("/books/{id}")
    public Book patchBook(@PathVariable Long id, @RequestBody Book newData) {
        Book existing = books.stream().filter(book -> book.getId().equals(id)).
                findFirst().
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));

        if (newData.getTitle() != null ) existing.setTitle(newData.getTitle());
        if (newData.getAuthor() != null) existing.setAuthor((newData.getAuthor()));
        if (newData.getPrice() != null) existing.setPrice(newData.getPrice());

        return existing;
    }

    // DELETE endpoint (remove book)
    @DeleteMapping("/books/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable Long id) {
        Book existing = books.stream().filter(book -> book.getId().equals(id)).
                findFirst().
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
        books.remove(existing);
    }

    // GET endpoint with pagination
    @GetMapping("/books/page")
    public List<Book> getBooksPagination(
            @RequestParam(required = false) int from,
            @RequestParam(required = false) int to) {

        return books.stream()
                .skip(from).
                limit(to - from + 1).
                collect(Collectors.toList());
    }


    // Advanced GET endpoint with filtering, sorting, and pagination combined in the valid order
    @GetMapping("/books/search")
    public List<Book> searchByTitle(
            @RequestParam(required = false, defaultValue = "") String title,
            @RequestParam(required = false, defaultValue = "0") int from,
            @RequestParam(required = false, defaultValue = "10") int to,
            @RequestParam(required = false, defaultValue = "title") String sortBy
    ) {

        Comparator<Book> comparator;

        if (sortBy.equalsIgnoreCase("author")) {
            comparator = Comparator.comparing(Book::getAuthor);
        } else if (sortBy.equalsIgnoreCase("price")) {
            comparator = Comparator.comparing(Book::getPrice);
        }
        else {
            comparator = Comparator.comparing(Book::getTitle);
        }

        if(title.isEmpty()) {
            return books.stream().sorted(comparator).skip(from).limit(to).
                    collect(Collectors.toList());
        }

        return books.stream().
                filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase())).
                sorted(comparator).skip(from).limit(to - from + 1).
                collect(Collectors.toList());
    }




}
