package drawing.commands;

import drawing.Canvas;

/**
 * This is create command responsible for creating a canvas. Use the method to run the command.
 * 
 * @author paude
 *
 */
public class CreateCommand implements ICommand {
	private Canvas canvas;
	private int width;
	private int height;
	public CreateCommand(Canvas canvas,int width, int height) {
		this.width=width;
		this.height=height;
		this.canvas=canvas;
	}

	@Override
	public void execute() {
		this.canvas.create(this.width, this.height);
		System.out.println(this.canvas.toString());
	}
}
