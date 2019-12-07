package com.mygdx.lifeisagame;

import com.badlogic.gdx.files.*;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.*;

public class LifeIsAGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture babiesTexture;
	Sprite babies;
	@Override
	public void create () {
		batch = new SpriteBatch();
		FileHandle babiesFileHandle = Gdx.files.internal("Player/Babies.png"); 
		babiesTexture= new Texture(babiesFileHandle);
		babies=new Sprite(babiesTexture,0,0,32,64);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(babies,0,0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
