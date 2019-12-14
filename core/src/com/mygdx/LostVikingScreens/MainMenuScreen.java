package com.mygdx.LostVikingScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.LostViking.LostViking;
import com.mygdx.LostViking.Player.Player;

public class MainMenuScreen implements Screen {

	private static final int EXIT_BUTTON_WIDTH=200;
	private static final int EXIT_BUTTON_HEIGHT=100;
	private static final int PLAY_BUTTON_WIDTH=200;
	private static final int PLAY_BUTTON_HEIGHT=100;
	private static final int PLAY_BUTTON_Y=500;
	private static final int EXIT_BUTTON_Y=400;
	private Viewport viewPort;
	private Stage stage;
	private Player player;
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
		stage.draw();
		game.batch.begin();
		
		game.batch.draw(backgroundMainMenu,0,0,720,1280);
		int a =(LostViking.WIDTH-PLAY_BUTTON_WIDTH)/2;
		int b =(LostViking.WIDTH-EXIT_BUTTON_WIDTH)/2;
		//Play
		if(Gdx.input.getX() <= 440 && Gdx.input.getX() >= 270 && Gdx.input.getY()<=620&&Gdx.input.getY()>=580)
		{
			game.batch.draw(playButtonActive, a,PLAY_BUTTON_Y,PLAY_BUTTON_WIDTH,PLAY_BUTTON_HEIGHT);
			if (Gdx.input.justTouched()) {
            	music.stop();
                this.dispose();
                World tempWorld = new World(new Vector2(0, 0),true);
                GameScreen screenGame = new GameScreen(game, tempWorld, new Player(tempWorld, new Vector2(500, 100)), "Maps/Map.tmx");
                game.setScreen(screenGame);
            }
		}
		else
		{
			game.batch.draw(playButtonInactive, a,PLAY_BUTTON_Y,PLAY_BUTTON_WIDTH,PLAY_BUTTON_HEIGHT);
		}
		
		//Exit
		if(Gdx.input.getX()<=430&&Gdx.input.getX()>=285&&Gdx.input.getY()<=690&&Gdx.input.getY()>=655)
		{
			game.batch.draw(exitButtonActive, b,EXIT_BUTTON_Y,EXIT_BUTTON_WIDTH,EXIT_BUTTON_HEIGHT);
			if (Gdx.input.justTouched()) {
            	music.stop();
                Gdx.app.exit();
            }
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
