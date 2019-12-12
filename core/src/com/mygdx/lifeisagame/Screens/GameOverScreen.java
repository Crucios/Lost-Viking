package com.mygdx.lifeisagame.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.LostViking.LostViking;
import com.mygdx.lifeisagame.Player.Player;

public class GameOverScreen implements Screen {
	private static final int BUTTON_WIDTH=200;
	private static final int BUTTON_HEIGHT=100;
	private static final int SCORETEXT_Y=750;
	private static final int RETRY_BUTTON_Y=500;
	private static final int EXIT_BUTTON_Y=400;
	private Viewport viewPort;
	private Stage stage;
	private Player player;
	private Music music;
	LostViking game;
	
	Texture scoreText;
	Texture retryButtonActive;
	Texture retryButtonInactive;
	Texture exitButtonActive;
	Texture exitButtonInactive;
	Texture backgroundGameOver;
	public GameOverScreen(LostViking game)
	{
		viewPort = new FitViewport(LostViking.WIDTH,LostViking.HEIGHT,new OrthographicCamera());
        stage = new Stage(viewPort, ((LostViking) game).batch);
		this.game=game;
		
		scoreText=new Texture("GameOver/scoreText.png");
		backgroundGameOver=new Texture("GameOver/gameover.png");
		retryButtonActive= new Texture("Gameover/activeretry.png");
		retryButtonInactive=new Texture("Gameover/inactiveretry.png");
		exitButtonActive= new Texture("Gameover/activeexit.png");
		exitButtonInactive=new Texture("Gameover/inactiveexit.png");
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
		int a=(LostViking.WIDTH-BUTTON_WIDTH)/2;
		stage.draw();
		game.batch.begin();
		game.batch.draw(backgroundGameOver,0,0,720,1280);
		game.batch.draw(scoreText,a,SCORETEXT_Y,BUTTON_WIDTH,BUTTON_HEIGHT);
		System.out.println("X : "+Gdx.input.getX());
		System.out.println("Y : "+Gdx.input.getY());
		//Retry
		if(Gdx.input.getX()<=450&&Gdx.input.getX()>=265&&Gdx.input.getY()<=620&&Gdx.input.getY()>=580)
		{
			game.batch.draw(retryButtonActive, a,RETRY_BUTTON_Y,BUTTON_WIDTH,BUTTON_HEIGHT);
			if (Gdx.input.justTouched()) {
            	music.stop();
                this.dispose();
                World tempWorldGame = new World(new Vector2(0,0),true);
                GameScreen screenGame = new GameScreen(game, tempWorldGame, new Player(tempWorldGame, new Vector2(500, 100)), "Maps/Map.tmx");
                game.setScreen(screenGame);
            }
		}
		else
		{
			game.batch.draw(retryButtonInactive, a,RETRY_BUTTON_Y,BUTTON_WIDTH,BUTTON_HEIGHT);
		}
		
		//Exit
		if(Gdx.input.getX()<=430&&Gdx.input.getX()>=285&&Gdx.input.getY()<=695&&Gdx.input.getY()>=655)
		{
			game.batch.draw(exitButtonActive, a,EXIT_BUTTON_Y,BUTTON_WIDTH,BUTTON_HEIGHT);
			if (Gdx.input.justTouched()) {
				music.stop();
				Gdx.app.exit();
            }
		}
		else
		{
			game.batch.draw(exitButtonInactive, a,EXIT_BUTTON_Y,BUTTON_WIDTH,BUTTON_HEIGHT);
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
