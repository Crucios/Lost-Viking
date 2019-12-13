package com.mygdx.lifeisagame.Player;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.LostViking.LostViking;

public class HUD implements Disposable{
	//HUD
	Table table;
	Player player;
	public Stage stage;
	
	//Scoring
	private Integer hitpoint;
	private Integer score;
	
	private Image hitpointLabel;
	private Label hitpointCountLabel;
	
	private Image scoreLabel;
	private Label scoreCountLabel;
	
	private ArrayList<Image> status;
	
	//Font
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    private BitmapFont font;
	
    //ViewPort
    private Viewport viewPort;
    
	public HUD(SpriteBatch sb, Player player){
		this.player = player;
		score = this.player.getScore();
		hitpoint = this.player.getHitpoints();
		
		viewPort = new FitViewport(LostViking.V_WIDTH, LostViking.V_HEIGHT, new OrthographicCamera());
		stage = new Stage(viewPort,sb);
		
		table = new Table();
		status = new ArrayList<Image>();
		
		//Font Generator
		fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Font/Ouders.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 50;
        fontParameter.color = Color.WHITE;
        font = fontGenerator.generateFont(fontParameter);
		
		Texture texture = new Texture(Gdx.files.internal("Player/Player.png"));
		hitpointLabel = new Image();
		hitpointLabel.setDrawable(new TextureRegionDrawable(new TextureRegion(texture, 362, 0, 46, 41)));
		hitpointLabel.setScale(3.75f,1.75f);
		
		hitpointCountLabel = new Label(String.format("%d", hitpoint), new Label.LabelStyle(font, Color.WHITE));
		hitpointCountLabel.setFontScaleX(1.75f);
		
		texture = new Texture(Gdx.files.internal("Player/HUD/score_icon.png"));
		scoreLabel = new Image();
		scoreLabel.setDrawable(new TextureRegionDrawable(new TextureRegion(texture, 27, 29, 146, 146)));
		scoreLabel.setScale(1.25f, 0.5f);
		
		scoreCountLabel = new Label(String.format("%d", score), new Label.LabelStyle(font, Color.WHITE));
		scoreCountLabel.setFontScaleX(1.75f);
		
		addHUD();
		
		stage.addActor(table);
	}
	
	public void addHUD() {
		table.bottom();
		table.setFillParent(true);
		table.add(scoreLabel).expandX().padLeft(-500).padTop(-75);
		table.add(scoreCountLabel).expandX().padLeft(-1800).padTop(10);
		
		table.row();
		
		table.add(hitpointLabel).expandX().padLeft(-575).padTop(30).padBottom(50);
		table.add(hitpointCountLabel).expandX().padLeft(-1800).padTop(5).padBottom(50);
	}
	
	public void update(float dt) {
		score = this.player.getScore();
		hitpoint = this.player.getHitpoints();
	}
	
	@Override
	public void dispose() {
		stage.dispose();
	}

}
