package vooga.turnbased.gamecore;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import vooga.turnbased.gameobject.MapObject;

public class PathFinder {
	
	private List<Point> myDirections;
	private Map<Point, List<MapObject>> myMap;
	private Point myStart;
	private Point myEnd;
	private Dimension mySize;
	
	public PathFinder(Map<Point, List<MapObject>> map, Point start, Point target, Point myBottomRightCorner) {
		myMap = map;
		myStart = start;
		myEnd = target;
		myDirections = new ArrayList<Point>();
		mySize = new Dimension(myBottomRightCorner.x, myBottomRightCorner.y);
	}
	
	public boolean depthFirstSearch(Point current) {
		if (current.equals(myEnd)) {
			return true; //found the path
		}
		PriorityQueue <Point> myOptions = new PriorityQueue<Point>(2, new Comparator<Point>() {
			public int compare(Point a, Point b) {
				return Double.compare(a.distance(myEnd), b.distance(myEnd));
			}
		});
		if (validateMove(current, MapMode.LEFT)) {
			myOptions.add(MapMode.LEFT);
		}
		if (validateMove(current, MapMode.RIGHT)) {
			myOptions.add(MapMode.RIGHT);
		}
		if (validateMove(current, MapMode.UP)) {
			myOptions.add(MapMode.UP);
		}
		if (validateMove(current, MapMode.DOWN)) {
			myOptions.add(MapMode.DOWN);
		}
		while (!myOptions.isEmpty()) {
			Point p = myOptions.poll();
			p.translate(current.x, current.y);
			if (depthFirstSearch(p)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean validateMove(Point position, Point direction) {
		if ((position.x + direction.x >= mySize.width) || (position.x + direction.x < 0)) {
			return false;
		}
		if ((position.y + direction.y >= mySize.height) || (position.y + direction.y < 0)) {
			return false;
		}
		return true;
	}
}
