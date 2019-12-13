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
	
	private Label scoreLabel;
	private Label scoreCountLabel;
	
	private ArrayList<Image> status;
	
	//Font
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    private BitmapFont font;
	
    //ViewPort
    private Viewport viewPort;
    
    private boolean hasGenerated;
    
	public HUD(SpriteBatch sb, Player player){
		this.player = player;
		score = this.player.getScore();
		hitpoint = this.player.getHitpoints();
		hasGenerated = false;
		
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
		
		hitpointCountLabel = new Label(String.format("X   %d", hitpoint), new Label.LabelStyle(font, Color.WHITE));
		hitpointCountLabel.setFontScaleX(1.75f);
		
		scoreLabel = new Label("SCORE", new Label.LabelStyle(font, Color.WHITE));
		scoreLabel.setFontScaleX(1.5f);
		
		scoreCountLabel = new Label(String.format("%d", score), new Label.LabelStyle(font, Color.WHITE));
		scoreCountLabel.setFontScaleX(1.75f);
		
		addHUD();
	}
	
	public void addHUD() {
		table.bottom();
		table.setFillParent(true);
		table.add(scoreLabel).expandX().padBottom(27);
		table.add(scoreCountLabel).expandX().padBottom(27).padLeft(-300);
		
		table.add(hitpointLabel).expandX().padLeft(500);
		table.add(hitpointCountLabel).expandX().padTop(5).padLeft(-30).padBottom(35);
		stage.addActor(table);
	}
	
	public void update(float dt) {
		score = player.getScore();
		hitpoint = player.getHitpoints();
		
		scoreCountLabel.setText(String.format("%d", score));
		hitpointCountLabel.setText(String.format("X   %d", hitpoint));
		
		if(player.isChoosingSkill() && !hasGenerated) {
			boolean check = false;
			
			table = new Table();
			table.top();
			table.setFillParent(true);	
			table.padTop(425);
			
			
			for(int i=0;i<player.getSkill().getNodes().size();i++) {
				if(player.getSkill().getNodes().get(i) != null) {
					Image temp = new Image();
					temp.setDrawable(new TextureRegionDrawable(player.getSkill().getNodes().get(i).getTextureRegion()));
					temp.setScale(2.75f, 1f);
					
					if(i == 0)
						table.add(temp).expandX();
					
					if(i == 1 && player.getSkill().getNodes().size() < 3) {
						table.add(temp).expandX().padLeft(-300);
						table.add(temp).expandX().padLeft(-500);
					}
					else if(i == 1 && player.getSkill().getNodes().size() >= 3) {
						table.padLeft(-200);
						table.add(temp).expandX().padLeft(-400);
					}
						

					if(i == 2)
						table.add(temp).expandX().padLeft(-100);
					
					check = true;
				}
				
			}
			
			if(check) {
				stage.clear();
				stage.addActor(table);
			}
			
			hasGenerated = true;
		}
		
		if(player.isJustChooseSkill()) {
			stage.clear();
			table = new Table();
			addHUD();
			player.setJustChooseSkill(false);
			hasGenerated = false;
		}
	}
	
	@Override
	public void dispose() {
		stage.dispose();
	}

}
