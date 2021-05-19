package fr.imt.albi.snake.model;

import static fr.imt.albi.snake.constants.GameConstants.ALL_DOTS;

public class Snake {
    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private int bubullesSerpents = 3;
    private final int positionSerpentX[] = new int[ALL_DOTS];
    private final int positionSerpentY[] = new int[ALL_DOTS];

    public boolean isRightDirection() {
        return rightDirection;
    }

    public void setRightDirection(boolean rightDirection) {
        this.rightDirection = rightDirection;
    }

    public boolean isLeftDirection() {
        return leftDirection;
    }

    public void setLeftDirection(boolean leftDirection) {
        this.leftDirection = leftDirection;
    }

    public boolean isUpDirection() {
        return upDirection;
    }

    public void setUpDirection(boolean upDirection) {
        this.upDirection = upDirection;
    }

    public boolean isDownDirection() {
        return downDirection;
    }

    public void setDownDirection(boolean downDirection) {
        this.downDirection = downDirection;
    }

    public int getBubullesSerpents() {
        return bubullesSerpents;
    }

    public void setBubullesSerpents(int bubullesSerpents) {
        this.bubullesSerpents = bubullesSerpents;
    }

    public int[] getPositionSerpentX() {
        return positionSerpentX;
    }

    public int[] getPositionSerpentY() {
        return positionSerpentY;
    }
}
