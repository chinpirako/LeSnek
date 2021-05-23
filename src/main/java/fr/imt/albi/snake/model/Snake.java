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
		return this.rightDirection;
	}

	public void setRightDirection(boolean rightDirection) {
		this.rightDirection = rightDirection;
	}

	public boolean isLeftDirection() {
		return this.leftDirection;
	}

	public void setLeftDirection(boolean leftDirection) {
		this.leftDirection = leftDirection;
	}

	public boolean isUpDirection() {
		return this.upDirection;
	}

	public void setUpDirection(boolean upDirection) {
		this.upDirection = upDirection;
	}

	public boolean isDownDirection() {
		return this.downDirection;
	}

	public void setDownDirection(boolean downDirection) {
		this.downDirection = downDirection;
	}

	public int getBubullesSerpents() {
		return this.bubullesSerpents;
	}

	public void setBubullesSerpents(int bubullesSerpents) {
		this.bubullesSerpents = bubullesSerpents;
	}

	public int[] getPositionSerpentX() {
		return this.positionSerpentX;
	}

	public int[] getPositionSerpentY() {
		return this.positionSerpentY;
	}
}
