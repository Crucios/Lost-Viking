package com.mygdx.lifeisagame.Player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;

public class HUD implements Disposable{
	//HUD
	Table table;
	Player player;
	public Stage stage;
	
	//Scoring
	private Integer friendship;
	private Integer family;
	private Integer health;
	private Integer happiness;
	private Integer money;
	
	//Label for Friendship
	Label friendshipLabel;
	Label friendshipCountLabel;
	
	//Label for Family
	Label familyLabel;
	Label familyCountLabel;
	
	//Label for Health
	Label healthLabel;
	Label healthCountLabel;
	
	//Label for Happiness
	Label happinessLabel;
	Label happinessCountLabel;
	
	//Label for Money
	Label moneyLabel;
	Label moneyCountLabel;
	
	HUD(SpriteBatch sb, Player player){
		this.player = player;
		friendship = this.player.getFriendship();
		family = this.player.getFamily();
		health = this.player.getHealth();
		happiness = this.player.getHappiness();
		money = this.player.getMoney();
		
		//Health
		healthLabel = new Label("HEALTH",new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		healthCountLabel = new Label(String.format("%d", health), new Label.LabelStyle(new BitmapFont() , Color.WHITE));
		
		//Happiness
		happinessLabel = new Label("HAPPINESS",new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		happinessCountLabel = new Label(String.format("%d", happiness), new Label.LabelStyle(new BitmapFont() , Color.WHITE));
		
		//Money
		moneyLabel = new Label("MONEY", new Label.LabelStyle(new BitmapFont() , Color.WHITE));
		moneyCountLabel = new Label(String.format("%d", money), new Label.LabelStyle(new BitmapFont() , Color.GREEN));
		
		//Family
		familyLabel = new Label("FAMILY", new Label.LabelStyle(new BitmapFont() , Color.WHITE));
		familyCountLabel = new Label(String.format("%d", family), new Label.LabelStyle(new BitmapFont() , Color.WHITE));
		
		//Friendship
		friendshipLabel = new Label("FRIENDSHIP", new Label.LabelStyle(new BitmapFont() , Color.WHITE));
		friendshipCountLabel = new Label(String.format("%d", friendship), new Label.LabelStyle(new BitmapFont() , Color.WHITE));
	}
	
	public void addHUD() {
		table.add(healthLabel).expandX().padTop(30);
		table.add(healthCountLabel).expandX().padTop(30);
		table.add(happinessLabel).expandX().padTop(30);
		table.add(happinessCountLabel).expandX().padTop(30);
		table.add(moneyLabel).expandX().expandX().padTop(30);
		table.add(moneyCountLabel).expandX().padTop(30);
		
		table.row();
		
		table.add(friendshipLabel).expandX();
		table.add(friendshipCountLabel).expandX();
		table.add(familyLabel).expandX();
		table.add(familyCountLabel).expandX();
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
