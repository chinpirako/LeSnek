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

    private long currentScore = 0;

    public Board() {
        this.initBoard();
    }

    private void initBoard() {
        this.addKeyListener(new TAdapter());
        this.setBackground(Color.black);
        this.setFocusable(true);

        this.setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        this.loadImages();
        this.initGame();
    }

    private void loadImages() {
        ImageIcon iid = new ImageIcon("src/main/resources/dot.png");
        this.queueSerpent = iid.getImage();

        ImageIcon iia = new ImageIcon("src/main/resources/apple.png");
        this.apple = iia.getImage();

        ImageIcon iih = new ImageIcon("src/main/resources/head.png");
        this.teteSerpent = iih.getImage();
    }

    private void initGame() {
        this.serpent = new Snake();
        for (int z = 0; z < this.serpent.getBubullesSerpents(); z++) {
            this.serpent.getPositionSerpentX()[z] = 50 - z * 10;
            this.serpent.getPositionSerpentY()[z] = 50;
        }

        this.locateApple();

        this.timer = new Timer(DELAY, this);
        this.timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.doDrawing(g);
    }

    private void doDrawing(Graphics g) {
        if (this.inGame) {
            this.updateScore(g);
            g.drawImage(this.apple, this.positionBouffeX, this.positionBouffeY, this);
            for (int z = 0; z < this.serpent.getBubullesSerpents(); z++) {
                int[] positionSerpentX = this.serpent.getPositionSerpentX();
                int[] positionSerpentY = this.serpent.getPositionSerpentY();
                if (z == 0) {
                    g.drawImage(this.teteSerpent, positionSerpentX[z], positionSerpentY[z], this);
                } else {
                    g.drawImage(this.queueSerpent, positionSerpentX[z], positionSerpentY[z], this);
                }
            }
            Toolkit.getDefaultToolkit().sync();
        } else {
            this.gameOver(g);
        }
    }

    /**
     * Pour le fun, on va mettre l'affichage de score ;)
     * @param g
     */
    private void updateScore(Graphics g) {
        String message = "Score: " + this.currentScore;
        Font small = new Font("Helvetica", Font.BOLD, 16);
        FontMetrics metrics = this.getFontMetrics(small);
        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(message, (B_WIDTH - metrics.stringWidth(message)) / 2, 16);
    }

    private void gameOver(Graphics g) {
        String youLostMessage = "T'as perdu lol";
        Font youLostFont = new Font("Helvetica", Font.BOLD, 28);

        String finalScoreMessage = "Score final : " + this.currentScore;
        Font finalScoreFont = new Font("Helvetica", Font.BOLD, 16);

        FontMetrics youLostMetrics = this.getFontMetrics(youLostFont);
        FontMetrics finalScoreMetrics = this.getFontMetrics(finalScoreFont);

        g.setColor(Color.white);

        g.setFont(youLostFont);
        g.drawString(youLostMessage, (B_WIDTH - youLostMetrics.stringWidth(youLostMessage)) / 2, B_HEIGHT / 2);

        g.setFont(finalScoreFont);
        g.drawString(finalScoreMessage, (B_WIDTH - finalScoreMetrics.stringWidth(youLostMessage)) / 2, 20 + B_HEIGHT / 2);
    }

    /**
     * M�thode pour v�rifier si le serpent mange une pomme.
     */
    private void checkApple() {
        int positionTeteX = this.serpent.getPositionSerpentX()[0];
        int positionTeteY = this.serpent.getPositionSerpentY()[0];
        if (this.positionBouffeX == positionTeteX && positionTeteY == this.positionBouffeY) {
            this.serpent.setBubullesSerpents(this.serpent.getBubullesSerpents() + 1); // Il gagne un peu en taille...
            this.currentScore += SCORE_INCREMENT;
            this.locateApple(); // ... et la pomme change de lieu :)
        }
    }

    /**
     * G�re le mouvement du serpent lors qu'il se dirige dans une direction.
     */
    private void move() {
        // Ici, on g�re le d�placement de tout le corps sauf sa t�te.
        // Chaque partie du corps prend la place de la partie devant elle � chaque fois.
        // On ne d�place pas la t�te dans cette boucle, �a vient juste apr�s
        for (int z = this.serpent.getBubullesSerpents(); z > 0; z--) {
            this.serpent.getPositionSerpentX()[z] = this.serpent.getPositionSerpentX()[z - 1];
            this.serpent.getPositionSerpentY()[z] = this.serpent.getPositionSerpentY()[z - 1];
        }

        // Ici, on d�place la t�te en fonction de la direction qu'on donne au serpent.
        // Pour g�rer le cas des murs : si on atteint une bordure du terrain de jeu (soit en 0 sur x/y, soit en B_WIDTH/B_HEIGHT),
        // on met la valeur max/min en fonction du cas.
        if (this.serpent.isLeftDirection()) {
            /* La syntaxe en ? : est �quivalente � un if. Ca peut se r��crire de cette mani�re :
            if (this.serpent.getPositionSerpentX()[0] - DOT_SIZE < 0) {
                this.serpent.getPositionSerpentX()[0] = B_WIDTH;
            } else {
                this.serpent.getPositionSerpentX()[0] -= DOT_SIZE;
            }
            Le ? : s'appelle l'op�rateur ternaire. Y a la m�me en Python juste avec un ordre diff�rent :
                position = B_WIDTH if (position - DOT_SIZE == 0) else...

                Autrement, cette condition est l� pour dire que si on atteint le bord � gauche, ben le serpent va se retrouver
                � droite.
            */
            this.serpent.getPositionSerpentX()[0] = this.serpent.getPositionSerpentX()[0] - DOT_SIZE < 0 ? B_WIDTH - DOT_SIZE
                    : this.serpent.getPositionSerpentX()[0] - DOT_SIZE;
        }

        /* Ici, l'inverse : si on atteint la bordure � droite, on renvoie la t�te toute � gauche. */
        if (this.serpent.isRightDirection()) {
            this.serpent.getPositionSerpentX()[0] = (this.serpent.getPositionSerpentX()[0] + DOT_SIZE) % B_WIDTH;
        }

        /* Les deux conditions d'apr�s sont pour les m�mes choses, mais avec le haut/bas. */
        if (this.serpent.isUpDirection()) {
            this.serpent.getPositionSerpentY()[0] = this.serpent.getPositionSerpentY()[0] - DOT_SIZE < 0 ? B_HEIGHT - DOT_SIZE
                    : this.serpent.getPositionSerpentY()[0] - DOT_SIZE;
        }

        if (this.serpent.isDownDirection()) {
            this.serpent.getPositionSerpentY()[0] = (this.serpent.getPositionSerpentY()[0] + DOT_SIZE) % B_HEIGHT;
        }
    }

    /**
     * M�thode qui permet de v�rifier si le serpent ne se bouffe pas lui-m�me.
     */
    private void checkCollision() {
        int bubullesSerpents = this.serpent.getBubullesSerpents();
        int[] positionSerpentX = this.serpent.getPositionSerpentX();
        int[] positionSerpentY = this.serpent.getPositionSerpentY();
        for (int z = bubullesSerpents; z > 0; z--) {
            // S'il s'auto-bouffe, on arr�te le jeu
            if (z > 4 && positionSerpentX[0] == positionSerpentX[z] && positionSerpentY[0] == positionSerpentY[z]) {
                this.inGame = false;
                break; // Le break signifie juste qu'on sort de la boucle pr�matur�ment, comme �a il v�rifie pas pour les autres parties du corps.
            }
        }

        // Et si on a perdu... Plus besoin de faire avancer le jeu
        if (!this.inGame) {
            this.timer.stop();
        }
    }

    /**
     * M�thode pour repositionner la pomme une fois qu'elle a �t� mang�e.
     */
    private void locateApple() {
        /* Dans mon cas, rand_pos = 60. Comme dot_size = 10 et que le random renvoie un truc entre 0 et 1,
           �a veut dire que 0 =< r =< 600 et qu'on couvre toute la zone.
           Y a pas de r�ponse "exacte" ici pour l'impl�mentation de cet al�atoire, tant que vous la placez n'importe o�
           de fa�on ranom, �a va. :)
         */
        int r = (int) (Math.random() * RAND_POS) * DOT_SIZE;
        this.positionBouffeX = r;
        r = (int) (Math.random() * RAND_POS) * DOT_SIZE;
        this.positionBouffeY = r;
    }

    @Override
    /**
     * M�thode qui est appel�e d�s qu'on appuie sur une touche.
     */
    public void actionPerformed(ActionEvent e) {
        if (this.inGame) {
            this.checkApple();
            this.checkCollision();
            this.move();
        }
        this.repaint();
    }

    /**
     * Ca, c'est une classe qui se charge d'��couter les "events". Ici, on h�rite
     * de "KeyAdapter", du coup on �coute tous les events du clavier.
     */
    private class TAdapter extends KeyAdapter {
        /**
         * Et plus pr�cis�ment, quand on appuie sur une touche.
         */
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            /* Ici, quand on appuie sur une touche, on va n�cessairement avoir le serpent dont la t�te "pointe" dans cette
            direction, du coup on en d�duit facilement ce qu'il se passe ici. :)
             */
            if (key == KeyEvent.VK_LEFT && !serpent.isRightDirection()) {
                serpent.setLeftDirection(true);
                serpent.setUpDirection(false);
                serpent.setDownDirection(false);
            }

            if (key == KeyEvent.VK_RIGHT && !serpent.isLeftDirection()) {
                serpent.setRightDirection(true);
                serpent.setUpDirection(false);
                serpent.setDownDirection(false);
            }

            if (key == KeyEvent.VK_UP && !serpent.isDownDirection()) {
                serpent.setUpDirection(true);
                serpent.setLeftDirection(false);
                serpent.setRightDirection(false);
            }

            if (key == KeyEvent.VK_DOWN && !serpent.isUpDirection()) {
                serpent.setDownDirection(true);
                serpent.setLeftDirection(false);
                serpent.setRightDirection(false);
            }
        }
    }
}
