package drawing;

import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Queue representation to store any data of type. <br>
 * I have implemented with list data type.
 * 
 * @author paude
 *
 */
class MyQueue<T> extends AbstractQueue<T> {
	private List<T> points;

	public MyQueue() {
		this.points = new ArrayList<>();
	}
	
	/**
	 * Checks if queue is empty. No elements means empty queue
	 */
	public boolean isEmpty() {
		return this.size() == 0;
	}

	/**
	 * Adds(enqueues) point to the 
	 * @param point
	 * @return
	 */
	public boolean enqueue(T point){
		return this.offer(point);
	}
	
	@Override
	public boolean offer(T point) {
		this.points.add(point);
		return true;
	}

	@Override
	public T peek() {
		if (this.isEmpty())
			return null;
		return this.points.get(0);
	}

	public T dequeue(){
		return this.poll();
	}
	
	@Override
	public T poll() {
		if (this.isEmpty())
			return null;
		return this.points.remove(0);
	}

	@Override
	public Iterator<T> iterator() {
		return this.points.iterator();
	}

	@Override
	public int size() {
		return this.points.size();
	}

}
