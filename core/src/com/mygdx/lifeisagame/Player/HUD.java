package com.mygdx.lifeisagame.Player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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
	
	HUD(SpriteBatch sb, Player player){
		this.player = player;
		table = new Table();
		
	}
	
	public void addHUD() {
		table.bottom();
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		stage.dispose();
	}

}
