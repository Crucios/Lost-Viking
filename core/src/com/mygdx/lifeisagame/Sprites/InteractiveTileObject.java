package com.mygdx.lifeisagame.Sprites;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.LostViking.LostViking;

public abstract class InteractiveTileObject {
	protected World world;
	protected TiledMap map;
	protected TiledMapTile tile;
	protected Rectangle bounds;
	protected Body body;
	protected Fixture fixture;
	protected MapObject object;
	
	public InteractiveTileObject(World world, TiledMap map, MapObject object, boolean isSensor) {
		this.world = world;
		this.map = map;
		this.bounds = ((RectangleMapObject) object).getRectangle();;
		this.object = object;
		
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		
		bdef.type = BodyDef.BodyType.StaticBody;
		bdef.position.set((bounds.getX() + bounds.getWidth()/2) / LostViking.PPM,(bounds.getY() + bounds.getHeight()/2) / LostViking.PPM);
		if(isSensor)
			fdef.isSensor = true;
		body = world.createBody(bdef);
		
		shape.setAsBox((bounds.getWidth()/2) / LostViking.PPM, (bounds.getHeight()/2) / LostViking.PPM);
		fdef.shape = shape;
		fdef.filter.categoryBits = 1;
		fdef.filter.maskBits = 1;
		body.createFixture(fdef);
		
		shape.setAsBox(bounds.getWidth() / 2 / LostViking.PPM, bounds.getHeight() / 2 / LostViking.PPM);
		fdef.shape = shape;
		fixture = body.createFixture(fdef);
	}
	
	public abstract void onHit();
}
