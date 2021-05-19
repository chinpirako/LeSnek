package fr.imt.albi.snake.model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import static fr.imt.albi.snake.constants.GameConstants.*;

public class Board extends JPanel implements ActionListener {

    private int positionBouffeX;
    private int positionBouffeY;

    private boolean inGame = true;

    private Timer timer;
    private Image queueSerpent;
    private Image apple;
    private Image teteSerpent;

    private Snake serpent;

    public Board() {
        initBoard();
    }

    private void initBoard() {
        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        loadImages();
        initGame();
    }

    private void loadImages() {
        ImageIcon iid = new ImageIcon("src/main/resources/dot.png");
        queueSerpent = iid.getImage();

        ImageIcon iia = new ImageIcon("src/main/resources/apple.png");
        apple = iia.getImage();

        ImageIcon iih = new ImageIcon("src/main/resources/head.png");
        teteSerpent = iih.getImage();
    }

    private void initGame() {
        this.serpent = new Snake();
        for (int z = 0; z < this.serpent.getBubullesSerpents(); z++) {
            this.serpent.getPositionSerpentX()[z] = 50 - z * 10;
            this.serpent.getPositionSerpentY()[z] = 50;
        }

        locateApple();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    private void doDrawing(Graphics g) {
        if (inGame) {
            g.drawImage(apple, positionBouffeX, positionBouffeY, this);
            for (int z = 0; z < this.serpent.getBubullesSerpents(); z++) {
                int[] positionSerpentX = this.serpent.getPositionSerpentX();
                int[] positionSerpentY = this.serpent.getPositionSerpentY();
                if (z == 0) {
                    g.drawImage(teteSerpent, positionSerpentX[z], positionSerpentY[z], this);
                } else {
                    g.drawImage(queueSerpent, positionSerpentX[z], positionSerpentY[z], this);
                }
            }
            Toolkit.getDefaultToolkit().sync();
        } else {
            gameOver(g);
        }
    }

    private void gameOver(Graphics g) {
        String msg = "T'as perdu lol";
        Font small = new Font("Helvetica", Font.BOLD, 28);
        FontMetrics metr = getFontMetrics(small);
        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
    }

    // TODO : Ici, on vérifie si la tête du serpent a les mêmes coordonnées que la bouffe
    // Si oui, alors il faut incrémenter le nombre de bubulles du serpent, et replacer la bouffe
    private void checkApple() {
    }

    // TODO : Que se passe t'il quand le serpent bouge?
    // i.e. comment évoulent ses coordonnées en fonction de la direction qu'il prend ?
    private void move() {
        for (int z = this.serpent.getBubullesSerpents(); z > 0; z--) {
        }

        if (this.serpent.isLeftDirection()) {
        }

        if (this.serpent.isRightDirection()) {
        }

        if (this.serpent.isUpDirection()) {
        }

        if (this.serpent.isDownDirection()) {
        }
    }

    // TODO : Si y a le temps, faire en sorte que ça ne fasse pas Game Over quand il prend un mur.
    // Ca impliquera qu'il faille modifier les déplacements aussi pour que ça fasse le comportement attendu
    private void checkCollision() {
        int bubullesSerpents = this.serpent.getBubullesSerpents();
        int[] positionSerpentX = this.serpent.getPositionSerpentX();
        int[] positionSerpentY = this.serpent.getPositionSerpentY();
        for (int z = bubullesSerpents; z > 0; z--) {
            // S'il s'auto-bouffe, on arrête le jeu
            if ((z > 4) && (positionSerpentX[0] == positionSerpentX[z]) && (positionSerpentY[0] == positionSerpentY[z])) {
                inGame = false;
            }
        }

        if (positionSerpentY[0] >= B_HEIGHT) {
            inGame = false;
        }

        if (positionSerpentY[0] < 0) {
            inGame = false;
        }

        if (positionSerpentX[0] >= B_WIDTH) {
            inGame = false;
        }

        if (positionSerpentX[0] < 0) {
            inGame = false;
        }

        if (!inGame) {
            timer.stop();
        }
    }

    // TODO : Ici, on veut repositionner la bouffe de façon aléatoire après qu'elle était mangée.
    // Math.random() peut servir :)
    // La position est aussi relative à la taille du point (DOT_SIZE),
    // histoire de pas avoir une pomme qui part hors screen.
    private void locateApple() {
        positionBouffeX = 5 * DOT_SIZE;
        positionBouffeY = 5 * DOT_SIZE;
    }

    @Override
    /**
     * Méthode qui est appelée dès qu'on appuie sur une touche.
     */
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkCollision();
            move();
        }
        repaint();
    }

    /**
     * Ca, c'est une classe qui se charge d'écouter les "events".
     * Ici, on hérite de "KeyAdapter", du coup on écoute tous les events du clavier.
     */
    private class TAdapter extends KeyAdapter {
        /**
         * Et plus précisément, quand on appuie sur une touche.
         */
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            // TODO Qu'est-ce qu'il se passe quand on appuie sur une des touches directionnelles ?
            // Indice : Y a un rapport avec les machins de direction 
            if ((key == KeyEvent.VK_LEFT) && (!serpent.isRightDirection())) {
            }

            if ((key == KeyEvent.VK_RIGHT) && (!serpent.isLeftDirection())) {
            }

            if ((key == KeyEvent.VK_UP) && (!serpent.isDownDirection())) {
            }

            if ((key == KeyEvent.VK_DOWN) && (!serpent.isUpDirection())) {
            }
        }
    }
}
