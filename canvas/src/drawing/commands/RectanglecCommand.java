package drawing.commands;

import drawing.Canvas;
/**
 * Command to create a rectangle. The method execute runs the command
 * @author paude
 *
 */
public class RectanglecCommand extends LineCommand{

	public RectanglecCommand(Canvas canvas, int x1, int y1, int x2, int y2) {
		super(canvas, x1, y1, x2, y2);	
	}

	@Override
	public void execute() {
		this.canvas.createRectangle(x1, y1, x2, y2);
		System.out.println(this.canvas.toString());
	}	

}
