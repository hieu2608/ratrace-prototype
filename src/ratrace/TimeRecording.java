package ratrace;

import java.awt.Graphics;

public class TimeRecording {
	
	private int Count;
	private boolean IsTurnLeft = false;
	private boolean IsTurnRight = false;
	
	public TimeRecording(int i, boolean IsKeyLeft, boolean IsKeyRight){
		Count = i;
		IsTurnLeft = IsKeyLeft;
		IsTurnRight = IsKeyRight;
	}
	public int getCount() {
		return Count;
	}

	public boolean getIsTurnLeft() {
		return IsTurnLeft;
	}
	
	public boolean getIsTurnRight() {
		return IsTurnRight;
	}
	
}
