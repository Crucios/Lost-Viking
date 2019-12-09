package com.mygdx.lifeisagame.Sprites;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;

public class Ground extends InteractiveTileObject{
	public Ground(World world, TiledMap map, MapObject object) {
		super(world, map, object, false);
		fixture.setUserData(this);
	}

	@Override
	public void onHit() {
	}	
}
