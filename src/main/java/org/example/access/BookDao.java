package org.example.access;

import org.example.model.Book;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDao {
    private final Connection connection;

    public BookDao(Connection connection) {
        this.connection = connection;
    }

    public void addBook(Book book) throws SQLException {
        String query = "INSERT INTO buku (judul, pengarang, penerbit) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, book.getJudul());
            stmt.setString(2, book.getPengarang());
            stmt.setString(3, book.getPenerbit());
            stmt.executeUpdate();
        }
    }

    public Book getBook(int id) throws SQLException {
        String query = "SELECT * FROM buku WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Book(rs.getInt("id"), rs.getString("judul"), rs.getString("pengarang"), rs.getString("penerbit"));
                }
            }
        }
        return null;
    }

    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM buku";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                books.add(new Book(rs.getInt("id"), rs.getString("judul"), rs.getString("pengarang"), rs.getString("penerbit")));
            }
        }
        return books;
    }

    public void updateBook(Book book, int id) throws SQLException {
        String query = "UPDATE buku SET judul = ?, pengarang = ?, penerbit = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, book.getJudul());
            stmt.setString(2, book.getPengarang());
            stmt.setString(3, book.getPenerbit());
            stmt.setInt(4, id);
            stmt.executeUpdate();
        }
    }

    public void deleteBook(int id) throws SQLException {
        String query = "DELETE FROM buku WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}