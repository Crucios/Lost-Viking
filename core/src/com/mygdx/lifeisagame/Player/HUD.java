package com.mygdx.lifeisagame.Player;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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
	
	HUD(SpriteBatch sb, Player player){
		this.player = player;
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
		
		hitpointCountLabel = new Label(String.format("%d", hitpoint), new Label.LabelStyle(font, Color.WHITE));
	}
	
	public void addHUD() {
		table.bottom();
	}
	
	public void update(float dt) {
		
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		stage.dispose();
	}

}
