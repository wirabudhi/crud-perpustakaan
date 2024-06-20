package org.example.frames;

import org.example.access.BookDao;
import org.example.helpers.Koneksi;
import org.example.model.Book;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.util.List;

public class BookViewFrame extends JFrame{
    private JPanel mainPanel;
    private JTextField cariBukuTF;
    private JButton cariButton;
    private JTable viewTable;
    private JButton tambahButton;
    private JButton ubahButton;
    private JButton hapusButton;
    private JButton batalButton;
    private JButton tutupButton;

    private BookDao bookDao;

    public BookViewFrame()
    {
        Connection connection = Koneksi.getConnection();
        bookDao = new BookDao(connection);
        ubahButton.addActionListener(e -> {
            int row = viewTable.getSelectedRow();
            if(row < 0)
            {
                JOptionPane.showMessageDialog(null, "Silahkan pilih data yang akan diupdate", "Validasi Pilih Data", JOptionPane.ERROR_MESSAGE);
            }

            TableModel tableModel = viewTable.getModel();
            int id = Integer.parseInt(tableModel.getValueAt(row, 0).toString());

            System.out.println("ID yang dipilih: " + id);
//            JOptionPane.showMessageDialog(null, "ID yang dipilih: " + id);

            BookInputFrame frame = new BookInputFrame();
            frame.setId(id);
            frame.fieldInput();
            frame.setVisible(true);
        });

        tambahButton.addActionListener(e -> {
            BookInputFrame frame = new BookInputFrame();
            frame.setVisible(true);
        });

        hapusButton.addActionListener(e -> {
            int row = viewTable.getSelectedRow();
            if(row < 0)
            {
                JOptionPane.showMessageDialog(null, "Silahkan pilih data yang akan dihapus", "Validasi Pilih Data", JOptionPane.ERROR_MESSAGE);
            }

            int confirm = JOptionPane.showConfirmDialog(null, "Apakah anda yakin akan menghapus data ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);

            if(confirm == JOptionPane.YES_OPTION)
            {
                int id = Integer.parseInt(viewTable.getValueAt(row, 0).toString());

                try {
                    bookDao.deleteBook(id);
                    JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
                    fieldTable();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        cariButton.addActionListener(e -> {
           Connection c = Koneksi.getConnection();
           String keyword = "%" + cariBukuTF.getText() + "%";
           String searchSQL = "SELECT * FROM buku WHERE judul like ?";

           try {
               PreparedStatement ps = c.prepareStatement(searchSQL);
               ps.setString(1, keyword);
               ResultSet rs = ps.executeQuery();

               DefaultTableModel model = (DefaultTableModel) viewTable.getModel();
               model.setRowCount(0);
               Object[] row = new Object[4];

               while (rs.next()) {
                   row[0] = rs.getInt("id");
                   row[1] = rs.getString("judul");
                   row[2] = rs.getString("pengarang");
                   row[3] = rs.getString("penerbit");
                   model.addRow(row);
               }
           } catch (SQLException ex) {
               throw new RuntimeException(ex);
           }
        });

        batalButton.addActionListener(e -> {
            fieldTable();
        });

        tutupButton.addActionListener(e -> {
            dispose();
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                fieldTable();
            }
        });

        fieldTable();
        init();
    }

    public void init()
    {
        setContentPane(mainPanel);
        setTitle("Data Buku");
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void fieldTable()
    {
        try {
            List<Book> books = bookDao.getAllBooks();
            String[] header = {"ID", "Judul", "Pengarang", "Penerbit"};
            DefaultTableModel model = new DefaultTableModel(header, 0);
            viewTable.setModel(model);

            for (Book book : books) {
                Object[] row = {book.getId(), book.getJudul(), book.getPengarang(), book.getPenerbit()};
                model.addRow(row);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
