package fr.imt.albi.snake.main;

import fr.imt.albi.snake.model.Board;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class LeSnekGame extends JFrame {
    public LeSnekGame() {
        initUI();
    }

    private void initUI() {
        add(new Board());
        setResizable(false);
        pack();
        setTitle("Snake");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame ex = new LeSnekGame();
            ex.setVisible(true);
        });
    }
}
