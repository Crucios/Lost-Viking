package com.mygdx.lifeisagame.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.LostViking.LostViking;

public class MainMenuScreen implements Screen {

	private static final int EXIT_BUTTON_WIDTH=200;
	private static final int EXIT_BUTTON_HEIGHT=100;
	private static final int PLAY_BUTTON_WIDTH=200;
	private static final int PLAY_BUTTON_HEIGHT=100;
	private static final int PLAY_BUTTON_Y=500;
	private static final int EXIT_BUTTON_Y=350;
	private Viewport viewPort;
	private Stage stage;
	private Music music;
	LostViking game;
	Texture playButtonActive;
	Texture playButtonInactive;
	Texture exitButtonActive;
	Texture exitButtonInactive;
	Texture backgroundMainMenu;
	
	public MainMenuScreen(LostViking game)
	{
		viewPort = new FitViewport(LostViking.WIDTH,LostViking.HEIGHT,new OrthographicCamera());
        stage = new Stage(viewPort, ((LostViking) game).batch);
		this.game=game;
		playButtonActive=new Texture("MainMenu/activeplay.png");
		playButtonInactive=new Texture("MainMenu/inactiveplay.png");
		exitButtonActive=new Texture("MainMenu/activeexit.png");
		exitButtonInactive=new Texture("MainMenu/inactiveexit.png");
		backgroundMainMenu=new Texture("MainMenu/main menu.png");
		music = LostViking.manager.get("MainMenu/backsound.mp3",Music.class);
  		music.setLooping(true);
  		music.play();
	}
	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
		
		game.batch.draw(backgroundMainMenu,0,0);
		int a =(LostViking.WIDTH-PLAY_BUTTON_WIDTH)/2;
		int b =(LostViking.WIDTH-EXIT_BUTTON_WIDTH)/2;
		System.out.println("X: "+Gdx.input.getX());
		System.out.println("Y: "+Gdx.input.getY());
		if(Gdx.input.getX()<=440&&Gdx.input.getX()>=270&&Gdx.input.getY()<=530&&Gdx.input.getY()>=480)
		{
			game.batch.draw(playButtonActive, a,PLAY_BUTTON_Y,PLAY_BUTTON_WIDTH,PLAY_BUTTON_HEIGHT);
		}
		else
		{
			game.batch.draw(playButtonInactive, a,PLAY_BUTTON_Y,PLAY_BUTTON_WIDTH,PLAY_BUTTON_HEIGHT);
		}
		
		if(Gdx.input.getX()<=430&&Gdx.input.getX()>=285&&Gdx.input.getY()<=675&&Gdx.input.getY()>=630)
		{
			game.batch.draw(exitButtonActive, b,EXIT_BUTTON_Y,EXIT_BUTTON_WIDTH,EXIT_BUTTON_HEIGHT);
		}
		else
		{
			game.batch.draw(exitButtonInactive, b,EXIT_BUTTON_Y,EXIT_BUTTON_WIDTH,EXIT_BUTTON_HEIGHT);
		}
		game.batch.end();
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		
	}
	
}
