package drawing;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import drawing.commands.BucketFillCommand;
import drawing.commands.CreateCommand;
import drawing.commands.ICommand;
import drawing.commands.LineCommand;
import drawing.commands.RectanglecCommand;

/**
 * Carries out operations on canvas
 * 
 * @author paude
 *
 */
public class Drawing {
	private static final String NO_CANVAS_MESSAGE = "ERROR: NO canvas exists. Create a canvas first!";
	private static final String INVALID_COMMAND_MESSAGE = "Invalid Command!";
	private static final String POINT_NOT_IN_CANVAS = "Either or all of supplied points do NOT lie in the canvas!";
	private static final String LINE_CHAR_NOT_ALLOWED = "Line chart 'x' is NOT allowed to fill as a color";
	private static final char LINE_CHAR = 'x';

	private ICommand command;
	private Canvas canvas;

	public Drawing() {
		this.canvas = new Canvas();
	}

	/**
	 * Creates a canvas with specified width and height
	 * 
	 * @param width
	 *            Width of canvas
	 * @param height
	 *            Height of canvas
	 */
	public void create(int width, int height) {
		this.command = new CreateCommand(this.canvas, width, height);
		this.command.execute();
	}

	/**
	 * Creates a line between (x1,y1) and (x2,y2)
	 * 
	 * @param x1
	 *            x1 of (x1,y1)
	 * @param y1
	 *            y1 of (x1,y1)
	 * @param x2
	 *            x2 of (x2,y2)
	 * @param y2
	 *            y2 of (x2,y2)
	 */
	public void line(int x1, int y1, int x2, int y2) {
		this.command = new LineCommand(canvas, x1, y1, x2, y2);
		this.command.execute();
	}

	/**
	 * Creates a rectangle having (x1,y1) as top-left and (x2,y2) as
	 * bottom-right points
	 * 
	 * @param x1
	 *            x1 of (x1,y1)
	 * @param y1
	 *            y1 of (x1,y1)
	 * @param x2
	 *            x2 of (x2,y2)
	 * @param y2
	 *            y2 of (x2,y2)
	 */
	public void rect(int x1, int y1, int x2, int y2) {
		this.command = new RectanglecCommand(this.canvas, x1, y1, x2, y2);
		this.command.execute();
	}

	/**
	 * Carries out bucket fill within the reach of the point (x,y) with defined
	 * color c.
	 * 
	 * @param x
	 *            x of (x,y)
	 * @param y
	 *            y of (x,y)
	 * @param c
	 *            color
	 */
	public void fill(int x, int y, char c) {
		this.command = new BucketFillCommand(this.canvas, x, y, c);
		this.command.execute();
	}

	/**
	 * Checks if canvas(i.e. array of characters) has been created.
	 * @return true if create, false otherwise.
	 */
	public boolean isCanvasCreated() {
		return this.canvas.isCreated();
	}

	/**
	 * Validates the input command pattern
	 * 
	 * @param input
	 *            Input command
	 * @return true if valid, false otherwise
	 */
	public boolean validateInput(String input) {
		String patterns[] = new String[] { "C(\\s\\d+){2}\\s*", "[LR]{1}(\\s\\d+){4}\\s*", "B(\\s\\d+){2}\\s.\\s*",
				"Q\\s*" };
		for (String pattern : patterns) {
			if (input.matches(pattern))
				return true;
		}
		return false;
	}

	/**
	 * Displays command list
	 */
	public void displayHelpMessage() {
		System.out.println("Commands:");
		System.out.println("C w h         : To create a new canvas");
		System.out.println(
				"L x1 y1 x2 y2 : To create a line from (x1,y1) to (x2,y2). Draws horizontal and vertical lines only");
		System.out.println(
				"R x1 y1 x2 y2 : To create a rectangle with top left point(x1,y2) and button right point(x2,y2)");
		System.out.println("B x y c       : To fill the entire are connecto to (x,y) with color 'c'");
		System.out.println("Q             : Quits the program");
	}

	public static void main(String... args) throws IOException {
		Drawing drawing = new Drawing();
		boolean quitted = false;
		do {
			System.out.print("Enter command: ");
			String inputValue = new BufferedReader(new InputStreamReader(System.in)).readLine();
			if (!drawing.validateInput(inputValue)) {
				System.out.println(INVALID_COMMAND_MESSAGE);
				drawing.displayHelpMessage();
				continue;
			}
			String[] splitted = inputValue.split(" ");
			if (splitted[0].equals("Q")) {
				// Quitting
				quitted = true;
			} else if (splitted[0].equals("C")) {
				// Create a canvas
				int width = Integer.parseInt(splitted[1]);
				int height = Integer.parseInt(splitted[2]);
				drawing.create(width + 2, height + 2);

			} else if (splitted[0].equals("L")) {
				// Create Line
				int x1 = Integer.parseInt(splitted[1]);
				int y1 = Integer.parseInt(splitted[2]);
				int x2 = Integer.parseInt(splitted[3]);
				int y2 = Integer.parseInt(splitted[4]);

				// Validation
				if (!drawing.isCanvasCreated()) {
					System.out.println(NO_CANVAS_MESSAGE);
					continue;
				}
				if (!drawing.canvas.isPointInCanvas(new Point(x1, y1))
						|| !drawing.canvas.isPointInCanvas(new Point(x2, y2))) {
					System.out.println(POINT_NOT_IN_CANVAS);
					continue;
				}

				drawing.line(x1, y1, x2, y2);
			} else if (splitted[0].equals("R")) {
				// Rectangle
				int x1 = Integer.parseInt(splitted[1]);
				int y1 = Integer.parseInt(splitted[2]);
				int x2 = Integer.parseInt(splitted[3]);
				int y2 = Integer.parseInt(splitted[4]);

				// Validation
				if (!drawing.isCanvasCreated()) {
					System.out.println(NO_CANVAS_MESSAGE);
					continue;
				}

				if (!drawing.canvas.isPointInCanvas(new Point(x1, y1))
						|| !drawing.canvas.isPointInCanvas(new Point(x2, y2))) {
					System.out.println(POINT_NOT_IN_CANVAS);
					continue;
				}

				drawing.rect(x1, y1, x2, y2);

			} else if (splitted[0].equals("B")) {
				// Bucket
				int x = Integer.parseInt(splitted[1]);
				int y = Integer.parseInt(splitted[2]);
				char c = splitted[3].charAt(0);
				if (c == LINE_CHAR) {
					System.out.println(LINE_CHAR_NOT_ALLOWED);
					continue;
				}
				// Validation
				if (!drawing.isCanvasCreated()) {
					System.out.println(NO_CANVAS_MESSAGE);
					continue;
				}
				if (!drawing.canvas.isPointInCanvas(new Point(x, y))) {
					System.out.println(POINT_NOT_IN_CANVAS);
					continue;
				}
				drawing.fill(x, y, c);

			} else {
				drawing.displayHelpMessage();
			}
		} while (!quitted);

		System.out.println("Program Exited...\n Thank You!");
	}
}
