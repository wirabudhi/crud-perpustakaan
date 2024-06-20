package org.example.frames;

import org.example.helpers.Koneksi;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.example.access.BookDao;
import org.example.model.Book;

public class BookInputFrame extends JFrame {
    private JPanel inputPanel;
    private JTextField idTF;
    private JTextField judulTF;
    private JTextField pengarangTF;
    private JTextField penerbitTF;
    private JButton simpanButton;
    private JButton batalButton;

    int id;
    private BookDao bookDao;

    public void setId(int id) {
        this.id = id;
    }

    public BookInputFrame() {
        Connection connection = Koneksi.getConnection();
        bookDao = new BookDao(connection);
        init();
        simpanButton.addActionListener(e -> {
            String judul = judulTF.getText();
            String pengarang = pengarangTF.getText();
            String penerbit = penerbitTF.getText();

            Book book = new Book(id, judul, pengarang, penerbit);

            try {
                if (id == 0) {
                    bookDao.addBook(book);
                    JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
                } else {
                    bookDao.updateBook(book, id);
                    JOptionPane.showMessageDialog(null, "Data berhasil diupdate");
                }
                dispose();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        batalButton.addActionListener(e -> {
            dispose();
        });
    }

    public void init() {
        setContentPane(inputPanel);
        setTitle("Input Data Buku");
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void fieldInput() {
        try {
            Book book = bookDao.getBook(id);
            if (book != null) {
                idTF.setText(String.valueOf(book.getId()));
                judulTF.setText(book.getJudul());
                pengarangTF.setText(book.getPengarang());
                penerbitTF.setText(book.getPenerbit());
            } else {
                JOptionPane.showMessageDialog(null, "Data tidak ditemukan");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
