package ratrace;

import java.awt.Image;
import java.awt.Rectangle;

public class Tile {
	final int LENGTH = 20;
	
	public Image tileImage;
	
	private int tileX, tileY;
	private char tileType;
	private Mouse mouse = Main.getMouse();
	private Rectangle r;

	public Tile(int x, int y, char typeC){
		tileX = x * LENGTH;
		tileY = y * LENGTH;
		tileType = typeC;
		r = new Rectangle();
		// get Image for tile
		switch (tileType) {
		case 'x':
			tileImage = Main.getTileblock();
			break;
		case ',':
			tileType = ' ';
			break;
		default:
			break;
		}
	}

//	Tobe removed later
//	public void checkCollision(Rectangle rbound){
//		if (rbound.intersects(r) && (tileType != ' ')) {
////			mouse.setIsLose(true);
//			Main.updateCollision();
//		}
//	}
	public boolean isCollision(Rectangle rbound){
		r.setBounds(tileX, tileY, LENGTH, LENGTH);
		if (rbound.intersects(r) && (tileType != ' ')) {
			return true;
		} else {
			return false;
		}
	}	
	public void update() {
//		r.setBounds(tileX, tileY, LENGTH, LENGTH);
//		checkCollision(mouse.rect);
	}
	public Image getTileImage() {
		return tileImage;
	}

	public int getTileX() {
		return tileX;
	}

	public int getTileY() {
		return tileY;
	}

	public char getTileType() {
		return tileType;
	}
	
}
