package drawing.commands;

import drawing.Canvas;

/**
 * Create a new line. Use the method execute to run the command.
 * 
 * @author paude
 *
 */
public class LineCommand implements ICommand {
	protected Canvas canvas;
	protected int x1;
	protected int y1;
	protected int x2;
	protected int y2;

	public LineCommand(Canvas canvas, int x1, int y1, int x2, int y2) {
		this.canvas = canvas;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	@Override
	public void execute() {
		this.canvas.createLine(this.x1,this.y1,this.x2,this.y2);
		System.out.println(this.canvas.toString());
	}
}
