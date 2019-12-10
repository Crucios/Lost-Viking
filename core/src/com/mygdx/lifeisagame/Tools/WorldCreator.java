package com.mygdx.lifeisagame.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.lifeisagame.Sprites.Ground;
import com.mygdx.lifeisagame.Sprites.Wall;

public class WorldCreator {
	public WorldCreator(World world,TiledMap map) {
		BodyDef bdef = new BodyDef();
		PolygonShape shape = new PolygonShape();
		FixtureDef fdef = new FixtureDef();
		Body body;
		
		//Set for Wall
		for(MapObject object : map.getLayers().get("wall-object").getObjects().getByType(RectangleMapObject.class)) {
			new Wall(world, map ,object);
		}

	}
}
