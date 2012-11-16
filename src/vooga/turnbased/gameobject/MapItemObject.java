package vooga.turnbased.gameobject;

import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;

import vooga.turnbased.gamecore.GameManager;

public class MapItemObject extends MapObject{

	public MapItemObject(int id, GameManager.GameEvent event, Point location, Image mapImage) {
		super(id, event, location, mapImage);
		
	}

	@Override
	public void update(int delayTime) {
	}
	
	public void interact(MapObject target) {
		if (target instanceof MapPlayerObject) {
			setVisible(false);
		}
	}
}
