package ratrace;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Mouse {
	final int SPEED = 2;
	final int STOP = 0;
	final int GOUP = 1;
	final int GORIGHT = 2;
	final int GODOWN = 3;
	final int GOLEFT = 4;
	final int DEFAULTDIRECTION = GOUP;
	
	public static Rectangle rect = new Rectangle(0, 0, 0, 0);
	public static boolean IsWin = false;
	public static boolean IsLose = false;
	
	private int Direction, centerX, centerY, speedX, speedY, winX, winY, Score, HighestScore;  
	private int height = 50;
	private int width = 50;
	private ArrayList<Tile> tilearray = Main.getTilearray();
	
	public void update() {
		// Set Speed based on Direction
		switch (Direction) {
		case GOUP:
			speedX = 0;
			speedY = -SPEED;
			break;
		case GORIGHT:
			speedX = SPEED;
			speedY = 0;
			break;
		case GODOWN:
			speedX = 0;
			speedY = SPEED;
			break;
		case GOLEFT:
			speedX = -SPEED;
			speedY = 0;
			break;
		case STOP:
	    	speedX = 0;
	    	speedY = 0;
	    	break;
		}// end switch
		// Set Coordinate
		centerX += speedX;
		centerY += speedY;
		Score += SPEED;
		
		rect.setRect(centerX - (width/2), centerY - (height/2), width, height);
		
//		Check status Crash or Win
		checkstatus();

	}// end update()

	private void checkstatus() {
		// Check Win condition
 		if (winX <= centerX 
		 && centerX <= winX + width 
		 && 0 <= centerY 
		 && centerY <= winY ) {
			IsWin = true;
		// Check out of bound
		} else if (centerX - (width/2) < 0 
				|| centerX + (width/2) > Main.PhoneWidth 
				|| centerY - (height/2) < 0
				|| centerY + (height/2) > Main.PhoneWidth) {
			IsLose = true;
		// Check collision with block in the map
		} else {
			tilearray = Main.getTilearray();
			for (int i = 0; i < tilearray.size(); i++) {
				Tile t = tilearray.get(i);
				if (t.isCollision(rect)) {
					IsLose = true;
				}
			}			
		} 
 		
 		if (IsWin) {
//			StartPoint();
//			Main.restart();
			if (Score > HighestScore) {
				HighestScore = Score;
			}
			Main.setStatusText("You Win! Highest Score: " + HighestScore);
		} else if (IsLose) {
//			StartPoint();
//			Main.restart();
			Main.setStatusText("You Lose! Highest Score: " + HighestScore);
		} else {
				Main.setStatusText("Current Score: " + Score + " Highest Score: " + HighestScore);
		}		
		
	}


	public void TurnLeft() {
		switch (Direction) {
		case GOUP:
			Direction = GOLEFT;
			break;
		case GOLEFT:
			Direction = GODOWN;
			break;
		case GODOWN:
			Direction = GORIGHT;
			break;
		case GORIGHT:
			Direction = GOUP;
			break;
		}// end switch
	}// end TurnLeft

	public void TurnRight() {
		switch (Direction) {
		case GOUP:
			Direction = GORIGHT;
			break;
		case GORIGHT:
			Direction = GODOWN;
			break;
		case GODOWN:
			Direction = GOLEFT;
			break;
		case GOLEFT:
			Direction = GOUP;
			break;
		}// end switch
	}// end TurnRight
	
	public void Run(){
		Direction = DEFAULTDIRECTION;
		IsWin = false;
		IsLose = false;
		Score = 0;
	}
	
	public void Stop(){
		Direction = STOP;
	}
	
	public void StartPoint(){
		Direction = STOP;
		centerX = height / 2;
		centerY = Main.PhoneWidth - (width / 2);
		winX = Main.PhoneWidth - width;
		winY = height;
		IsWin = false;
		IsLose = false;
		rect.setRect(centerX - (width/2), centerY - (height/2), width, height);
	}
	
	public int getWinX() {
		return winX;
	}

	public int getWinY() {
		return winY;
	}

	public boolean getIsWin() {
		return IsWin;
	}

	public boolean getIsLose() {
		return IsLose;
	}

	public void setIsWin(boolean Win) {
		this.IsWin = Win;
	}

	public void setIsLose(boolean Lose) {
		this.IsLose = Lose;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getCenterX() {
		return centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public int getSpeedX() {
		return speedX;
	}

	public int getSpeedY() {
		return speedY;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}

}
