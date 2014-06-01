package ratrace;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class Main extends Applet implements Runnable, KeyListener {
//	Constant
	final int SLEEPTIME = 17;
	final static int PhoneWidth = 640;
	final static int PhoneHeight = 960;
	
//	Object
	private Image image, character;
	private URL base;
	private Graphics second;
	private static Image tileblock;
	
//	Variable
	private boolean IsKeyLeft = false;
	private boolean IsKeyRight = false;
	private static String statusText = "Start Game";
	private int statusX = 10;
	private int statusY = 700;
	private static int RecordCount, Count;
	private static Mouse mouse;

	private static boolean IsRecord = false;
	private static boolean IsRun = false;

	//Array
	private static ArrayList<TimeRecording> timearray = new ArrayList<TimeRecording>();
    private static ArrayList<Tile> tilearray = new ArrayList<Tile>();
	
	@Override
	public void init() {
		setSize(PhoneWidth, PhoneHeight);
		setBackground(Color.white);
		setFocusable(true);
		addKeyListener(this);
		Frame frame = (Frame) this.getParent().getParent();
		frame.setTitle("A-Maze-in Rat Race");
		try {
			base = getDocumentBase();
		} catch (Exception e) {
			// TODO: handle exception
		}

		// Image Setups
		character = getImage(base, "data/Mouse.png");
		tileblock = getImage(base, "data/tileblock.png");
		
		// Initialize Map
		try {
			loadMap("data/map.txt");
		} catch (IOException e){
			e.printStackTrace();
		}
		
	}// end init

	private void loadMap(String filename) throws IOException {

		ArrayList lines = new ArrayList();
		int width = 0;
		int height = 0;
		
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		while (true) {
			String line = reader.readLine();
//			no more lines to read
			if (line == null) {
				reader.close();
				break;
			}
			
			if (!line.startsWith("!")) {
				lines.add(line);
				width = Math.max(width, line.length());
			}
		}
		height = lines.size();
		
		for (int j = 0; j < height; j++) {
			String line = (String) lines.get(j);
			for (int i = 0; i < width; i++) {
				if (i < line.length()) {
					char ch = line.charAt(i);
					Tile t = new Tile(i,j,ch);
					tilearray.add(t);
				}
			}
		} //end for
		    
	}

	@Override
	public void start() {
		mouse = new Mouse();
		
		mouse.StartPoint();
		Thread thread = new Thread(this);
		thread.start();
	}// end start

	@Override
	public void stop() {

	}// end stop

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void run() {
		while (true) {
			Count++;
			if (IsRun) {
				checkAction();	
				mouse.update();
			}
			if (mouse.getIsWin() || mouse.getIsLose()) {
				System.out.println("Update: Mouse is Win or Lose");
				restart();
			}
			
//			updateTiles();
			repaint();
			try {
				Thread.sleep(SLEEPTIME);
			}// endtry
			catch (InterruptedException e) {
				e.printStackTrace();
			}// endcatch
		}// end while

	}

//	Tobe removed later
//	private void updateTiles() {
//		for (int i = 0; i < tilearray.size(); i++) {
//			Tile t = tilearray.get(i);
//			t.update();
//			if (mouse.getIsLose()) {
//				break;
//			}
//		}
//		
//	}

	private void checkAction() {
		// Check action based on the recording
			if (RecordCount < timearray.size()) {
	        	TimeRecording trc = timearray.get(RecordCount);
	        	if ( trc.getCount() == Count) {
	        		RecordCount++;
					if (trc.getIsTurnLeft()) {
						System.out.println("Action Turn Left");
						mouse.TurnLeft();
					} else if (trc.getIsTurnRight()){
						System.out.println("Action Turn Right");
						mouse.TurnRight();
					}
				}			
			}
	}

	@Override
	public void update(Graphics g) {
		if (image == null) {
			image = createImage(this.getWidth(), this.getHeight());
			second = image.getGraphics();
		}

		second.setColor(getBackground());
		second.fillRect(0, 0, getWidth(), getHeight());
		second.setColor(getForeground());
		second.drawRect(0, 0, PhoneWidth, PhoneWidth);
		second.setColor(Color.CYAN);
		second.fillRect(mouse.getWinX(), mouse.getWinY() - mouse.getHeight(), mouse.getWidth(), mouse.getHeight());
		paint(second);

		g.drawImage(image, 0, 0, this);
		
		if (mouse.getIsWin()) {
			statusText = "You Win. Press Enter to try again";
		} else if (mouse.getIsLose()) {
			statusText = "You Lose. Press Enter to try again";
		}
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(character, mouse.getCenterX() - (mouse.getWidth() / 2), mouse.getCenterY() - (mouse.getHeight() / 2), this);
//		g.drawRect((int)mouse.rect.getX(), (int)mouse.rect.getY(), (int)mouse.rect.getWidth(), (int)mouse.rect.getHeight());
		g.drawString(statusText, statusX, statusY);
		paintTiles(g);
	}

	private void paintTiles(Graphics g) {
		// TODO Auto-generated method stub
		for (int i = 0; i < tilearray.size(); i++) {
			Tile t = tilearray.get(i);
			g.drawImage(t.getTileImage(), t.getTileX(), t.getTileY(), this);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {

		case KeyEvent.VK_LEFT:
			if (IsKeyLeft == false) {
				IsKeyLeft = true;
				if (IsRecord) {
					TimeRecording trc = new TimeRecording(Count, IsKeyLeft, IsKeyRight);
					timearray.add(trc);
					System.out.println("Recording Turn Left");
				}
			}
			break;

		case KeyEvent.VK_RIGHT:
			if (IsKeyRight == false) {
				IsKeyRight = true;
				if (IsRecord) {
					TimeRecording trc = new TimeRecording(Count, IsKeyLeft, IsKeyRight);
					timearray.add(trc);
					System.out.println("Recording Turn Right");
				}
			}
			break;
			
		case KeyEvent.VK_ENTER:
			Count = 0;
			RecordCount = 0;
			statusText = "";
			if (IsRecord == false) {
//				Start Recording, start Count from 0
				IsRecord = true;
				IsRun = false;	
                System.out.println("Enter pressed: Record Mode");
			} else {
				IsRun = true;
				IsRecord = false;
				mouse.Run();
				System.out.println("Enter pressed: Run Mode");
			}	
//			mouse.Run();
			break;
			
		case KeyEvent.VK_SPACE:
//			mouse.Stop();
			break;
		}// end switch
	}
	
	public static void restart(){
		Count = 0;
		RecordCount = 0;
		timearray.clear();
		IsRecord = false;
		IsRun = false;
		mouse.StartPoint();
		System.out.println("Restart Game");
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			IsKeyLeft = false;
			break;
		case KeyEvent.VK_RIGHT:
			IsKeyRight = false;
			break;
		}// end switch
	}

//	To be removed
//public static void updateCollision() {
//	mouse.setIsLose(true);
//	restart();
//}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	public boolean isIsKeyLeft() {
		return IsKeyLeft;
	}

	public boolean isIsKeyRight() {
		return IsKeyRight;
	}

	public int getCount() {
		return Count;
	}

	public static String getStatusText() {
		return statusText;
	}

	public static void setStatusText(String statusText) {
		Main.statusText = statusText;
	}

	public static Image getTileblock() {
		return tileblock;
	}
	
	public static Mouse getMouse() {
		return mouse;
	}

	public static ArrayList<Tile> getTilearray() {
		return tilearray;
	}

}// end Main
