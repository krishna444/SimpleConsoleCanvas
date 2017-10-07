package drawing;

import java.awt.Point;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * This class represents canvas and its related operations
 * 
 * @author paude
 *
 */
public class Canvas {
	private static final Logger LOGGER = Logger.getLogger(Canvas.class.getName());
	static {
		try {
			LOGGER.setUseParentHandlers(false);
			LOGGER.addHandler(new FileHandler("canvas.log", true));
		} catch (Exception e) {
			System.err.println("Something went wrong while create a log file!");
		}
	}

	private int width;
	private int height;

	// canvas modal(array of characters)
	private char[][] canvas;

	/**
	 * Create an instance of canvas
	 */
	public Canvas() {
		LOGGER.info("##############################");
		LOGGER.info("Canvas has been initialised");
	}

	public char[][] getCanvas() {
		return this.canvas;
	}

	/**
	 * Create a canvas with speficied height and width
	 * 
	 * @param width
	 *            Width of canvas
	 * @param height
	 *            Height of canvas
	 */
	public void create(int width, int height) {
		LOGGER.info(String.format("Creating canvas of width:%s and height:%s", width, height));
		this.width = width;
		this.height = height;
		this.canvas = new char[height][width];
		this.createCanvsBoundary();
		LOGGER.info("DONE: Canvas has been created");
	}

	public boolean isCreated() {
		return this.canvas != null;
	}

	/**
	 * Create the boundary of the canvas
	 */
	private void createCanvsBoundary() {
		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
				if (i == 0 || i == this.height - 1)
					this.canvas[i][j] = '-';
				else if (j == 0 || j == this.width - 1) {
					this.canvas[i][j] = '|';
				} else {
					this.canvas[i][j] = ' ';
				}
			}
		}
	}

	/**
	 * Create a line from (x1,y1) to (x2,y2)
	 * 
	 * @param x1
	 *            X-coordinate of initial point
	 * @param y1
	 *            Y-coordinate of initial point
	 * @param x2
	 *            X-coordinate of final point
	 * @param y2
	 *            Y-coordinate of final point
	 */
	public void createLine(int x1, int y1, int x2, int y2) {
		LOGGER.info(String.format("Creating line from (%s,%s) to (%s,%s)", x1, y1, x2, y2));
		if (!this.isPointInCanvas(new Point(x1, y1)) || !this.isPointInCanvas(new Point(x2, y2))) {
			LOGGER.severe("Point does not lie in the canvas");
			return;
		}
		if (x1 == x2) {
			// Vertical Line
			LOGGER.info("Creating Vertical Line");

			int start = y1;
			int end = y2;
			if (y2 < y1) {
				start = y2;
				end = y1;
			}
			for (int i = start; i <= end; i++) {
				this.canvas[i][x1] = 'x';
			}
		}

		if (y1 == y2) {
			// Horizontal Line
			LOGGER.info("Creating Horizontal Line");
			int start = x1;
			int end = x2;
			if (x2 < x1) {
				start = x2;
				end = x1;
			}
			for (int i = start; i <= end; i++) {
				this.canvas[y1][i] = 'x';
			}
		}
		LOGGER.info("DONE: creating line");

	}

	/**
	 * Create a rectangle with top left(x1,y1) and bottom right(x2,y2)
	 * 
	 * @param x1
	 *            top left x
	 * @param y1
	 *            top left y
	 * @param x2
	 *            bottom right x
	 * @param y2
	 *            bottom right y
	 */
	public void createRectangle(int x1, int y1, int x2, int y2) {
		LOGGER.info(
				String.format("Create a rectable having top-left (%s,%s) and bottom-right (%s,%s)", x1, y1, x2, y2));

		if (!this.isPointInCanvas(new Point(x1, y1)) || !this.isPointInCanvas(new Point(x2, y2))) {
			LOGGER.severe("Point does not lie in the canvas");
			return;
		}
		// Drawing rectangle is just creating for boundary lines
		this.createLine(x1, y1, x2, y1);
		this.createLine(x2, y1, x2, y2);
		this.createLine(x2, y2, x1, y2);
		this.createLine(x1, y2, x1, y1);
		LOGGER.info("DONE: rectangle creation");
	}

	/**
	 * Carries out bucket fill with color c (Recursive Method)<br>
	 * This method carries out exhaustive recursive search and suffers <br>
	 * when the size of canvas is large.
	 * 
	 * @see {@link #bucketFillQ(int, int, char) bucketFillQ(int int,char)}
	 * 
	 * @param x
	 *            X Co-ordinate
	 * @param y
	 *            Y Co-ordinate
	 * @param c
	 *            Color to be filled
	 */
	public void bucketFillR(int x, int y, char c) {
		if (!this.isValidFillPoint(new Point(x, y), c)) {
			return;
		}
		this.canvas[x][y] = c;
		// fill recursively to all neighbor pixels
		this.bucketFillR(x + 1, y, c);
		this.bucketFillR(x, y + 1, c);
		this.bucketFillR(x - 1, y, c);
		this.bucketFillR(x, y - 1, c);

	}

	/**
	 * Carries out bucket fill with color c (Queue method) <br>
	 * This method is better than {@link #bucketFillR(int, int, char)
	 * bucketFillR(int, int ,char)} method.<br>
	 * <br>
	 * 
	 * This method carries out valid neighborhood points and enqueues for
	 * coloring.
	 * 
	 * @param x
	 *            X co-ordinate of point
	 * @param y
	 *            Y co-ordinate of point
	 * @param c
	 *            Color to be filled
	 */
	public void bucketFillQ(int x, int y, char c) {
		LOGGER.info(String.format("Starting filling bucket from (%s,%s) with color:%s", x, y, c));
		Point initialPoint = new Point(x, y);
		if (!this.isValidFillPoint(initialPoint, c)) {
			return; // Initial point invalid, no need to go further
		}
		MyQueue<Point> queue = new MyQueue<Point>();
		queue.enqueue(new Point(x, y));

		while (!queue.isEmpty()) {
			Point p = queue.dequeue();

			if (!this.isValidFillPoint(p, c)) {
				continue;
			}
			this.canvas[p.y][p.x] = c;

			Point leftNeighbor = new Point(p.x - 1, p.y);
			Point rightNeighbor = new Point(p.x + 1, p.y);
			Point topNeighbor = new Point(p.x, p.y - 1);
			Point bottomNeighbor = new Point(p.x, p.y + 1);
			if (this.isValidFillPoint(leftNeighbor, c)) {
				queue.enqueue(leftNeighbor);
			}
			if (this.isValidFillPoint(topNeighbor, c)) {
				queue.enqueue(topNeighbor);
			}

			if (this.isValidFillPoint(rightNeighbor, c)) {
				queue.enqueue(rightNeighbor);
			}

			if (this.isValidFillPoint(bottomNeighbor, c)) {
				queue.enqueue(bottomNeighbor);
			}
		}

		LOGGER.info("DONE: filling bucket");
	}

	/**
	 * Checks if a point lies in the canvas area (excluding boundary)
	 * 
	 * @param p
	 *            Point
	 * @return true if lies inthe canvas, false otherwise
	 */
	public boolean isPointInCanvas(Point p) {
		return p.x > 0 && p.x < this.width - 1 && p.y > 0 && p.y < this.height - 1;
	}

	/**
	 * This method checks if the point p is valid point to fill color c
	 * 
	 * @param p
	 *            Point
	 * @param c
	 *            Color to be field
	 * @return true if valid, false otherwise
	 */
	private boolean isValidFillPoint(Point p, char c) {
		return this.isPointInCanvas(p) && this.canvas[p.y][p.x] != 'x' && this.canvas[p.y][p.x] != c;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		for (char[] row : this.canvas) {
			for (char elem : row) {
				stringBuilder.append(elem);
			}
			stringBuilder.append("\n");
		}
		return stringBuilder.toString();
	}

}
