package org.example;

import org.example.frames.BookViewFrame;
import org.example.helpers.Koneksi;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Koneksi.getConnection();

        BookViewFrame frame = new BookViewFrame();
        frame.setVisible(true);
    }
}