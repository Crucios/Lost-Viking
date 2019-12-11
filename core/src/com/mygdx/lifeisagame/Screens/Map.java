package com.mygdx.lifeisagame.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.LostViking.LostViking;

public class Map extends Sprite{
	private TextureRegion Map;
	private Vector2 position;
	private boolean rotate;
	
	private float elapsed;
	
	private boolean disposed;
	
	Map(Vector2 position, boolean rotate){
		super(new AtlasRegion(new TextureAtlas("Maps/Map.pack").findRegion("map")));
		this.rotate = rotate;
		disposed = false;
		elapsed = 0;
		
		Map = new TextureRegion(new TextureRegion(getTexture(), 0, 0, 547, 968));
		
		if(rotate) 
			Map.flip(false, true);
		
		this.position = position;
		this.position.x /= LostViking.PPM;
		this.position.y /= LostViking.PPM;
		setSize(10f, 20f);
		
		setRegion(Map);
	}
	
	public void update(float dt) {
		elapsed += dt;
		position = new Vector2(position.x, position.y - dt);
		setPosition(position.x, position.y);
		
		if(position.y < 0 - getHeight() + 2f) {
			disposed = true;
		}
		
//		Gdx.app.log("Map Position", position.y + " ");
	}

	public boolean isDisposed() {
		return disposed;
	}

	public void setDisposed(boolean disposed) {
		this.disposed = disposed;
	}

	public boolean isRotate() {
		return rotate;
	}

	public void setRotate(boolean rotate) {
		this.rotate = rotate;
	}
	
	//Public Access Method
	
}
