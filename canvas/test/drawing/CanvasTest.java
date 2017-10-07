package drawing;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Test class
 * 
 * @author paude
 *
 */
public class CanvasTest {
	private Canvas canvas;

	@Before
	public void init() {
		this.canvas = new Canvas();
	}

	@Test
	public void testCanvas_20x4() {
		// @formatter:off
		final char[][] CREATE_CANVAS=new char[][]{"----------------------".toCharArray(),
												  "|                    |".toCharArray(),														     
				                                  "|                    |".toCharArray(),
				                                  "|                    |".toCharArray(),
				                                  "|                    |".toCharArray(),
				                                  "----------------------".toCharArray()
												};
		 final char[][] CREATE_LINE1=new char[][]{"----------------------".toCharArray(),
						    				      "|                    |".toCharArray(),														     
					                              "|xxxxxx              |".toCharArray(),
					                              "|                    |".toCharArray(),
					                              "|                    |".toCharArray(),
					                              "----------------------".toCharArray()
			                                      }; 
	    final char[][] CREATE_LINE2=new char[][]{"----------------------".toCharArray(),
										       	 "|                    |".toCharArray(),														     
						                         "|xxxxxx              |".toCharArray(),
						                         "|     x              |".toCharArray(),
						                         "|     x              |".toCharArray(),
						                         "----------------------".toCharArray()
				                                    };
	   final char[][] CREATE_RECTANGLE=new char[][]{"----------------------".toCharArray(),
											        "|               xxxxx|".toCharArray(),														     
							                        "|xxxxxx         x   x|".toCharArray(),
							                        "|     x         xxxxx|".toCharArray(),
							                        "|     x              |".toCharArray(),
							                        "----------------------".toCharArray()
					                                };
	 final char[][] CREATE_BUCKETFILL=new char[][]{"----------------------".toCharArray(),
												   "|oooooooooooooooxxxxx|".toCharArray(),														     
								                   "|xxxxxxooooooooox   x|".toCharArray(),
								                   "|     xoooooooooxxxxx|".toCharArray(),
				                                   "|     xoooooooooooooo|".toCharArray(),
				                                   "----------------------".toCharArray()
			                                        };		
		// @formatter:on
		this.canvas.create(20 + 2, 4 + 2); // +2 for canvas border
		Assert.assertArrayEquals(CREATE_CANVAS, this.canvas.getCanvas());
		this.canvas.createLine(1, 2, 6, 2);
		Assert.assertArrayEquals(CREATE_LINE1, this.canvas.getCanvas());
		this.canvas.createLine(6, 3, 6, 4);
		Assert.assertArrayEquals(CREATE_LINE2, this.canvas.getCanvas());
		this.canvas.createRectangle(16, 1, 20, 3);
		Assert.assertArrayEquals(CREATE_RECTANGLE, this.canvas.getCanvas());
		this.canvas.bucketFillQ(10, 3, 'o');
		Assert.assertArrayEquals(CREATE_BUCKETFILL, this.canvas.getCanvas());
	}

	@Test
	public void randomTest() {
		this.canvas.create(10, 7);
		char[][] actual = this.canvas.getCanvas();
		//@formatter:off
		char[][] expected = { 
				"----------".toCharArray(), 
				"|        |".toCharArray(), 
				"|        |".toCharArray(),
				"|        |".toCharArray(), 
				"|        |".toCharArray(), 
				"|        |".toCharArray(),
				"----------".toCharArray() 
		};
		//@formatter:on
		Assert.assertArrayEquals(expected, actual);

		
		//@formatter:off
		char[][] expectedRectangle = { 
						"----------".toCharArray(), 
						"|        |".toCharArray(), 
						"|  xxxxx |".toCharArray(),
						"|  x   x |".toCharArray(), 
						"|  x   x |".toCharArray(), 
						"|  xxxxx |".toCharArray(),
						"----------".toCharArray() 
				};
		char[][] expectedInnerFill = { 
				"----------".toCharArray(), 
				"|        |".toCharArray(), 
				"|  xxxxx |".toCharArray(),
				"|  xCCCx |".toCharArray(), 
				"|  xCCCx |".toCharArray(), 
				"|  xxxxx |".toCharArray(),
				"----------".toCharArray() 
		};
		char[][] expectedOuterFill = { 
				"----------".toCharArray(), 
				"|OOOOOOOO|".toCharArray(), 
				"|OOxxxxxO|".toCharArray(),
				"|OOxCCCxO|".toCharArray(), 
				"|OOxCCCxO|".toCharArray(), 
				"|OOxxxxxO|".toCharArray(),
				"----------".toCharArray() 
		};		
	    //@formatter:on
		this.canvas.createRectangle(3, 2, 7, 5);
		Assert.assertArrayEquals(expectedRectangle, this.canvas.getCanvas());
		this.canvas.bucketFillQ(4,3, 'C');
		Assert.assertArrayEquals(expectedInnerFill, this.canvas.getCanvas());
		//Invalid Fill
		this.canvas.bucketFillQ(3, 3, 'F');
		Assert.assertArrayEquals(expectedInnerFill, this.canvas.getCanvas());
		this.canvas.bucketFillQ(2, 5, 'O');
		Assert.assertArrayEquals(expectedOuterFill, this.canvas.getCanvas());
	}

	public static void main(String... args) {
		Result result = JUnitCore.runClasses(CanvasTest.class);
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}

		System.out.println("Test Result: "+result.wasSuccessful());

	}

}
