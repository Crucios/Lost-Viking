package com.mygdx.lifeisagame.Enemy;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.lifeisagame.Player.Player;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class EnemyBase extends Sprite{
	protected World world;
	protected Vector2 position;
	protected Vector2 nowPosition;
	protected float xawal;
    protected float xakhir;
    protected Player player;
    protected int HP;
    protected int damage;
    protected int score;
    public Body b2body;
    
    protected TextureRegion staticEnemy;
    
	protected float stateTimer;
	protected boolean hitbyBullet;
	protected boolean isDead;
	protected boolean hasDestroyed;
	protected ArrayList<Bullet> enemybullet;
	
	public EnemyBase(World world,Vector2 position,float xawal,float xakhir,Player player)
	{
		this.world=world;
		this.position=position;
		this.xawal=xawal;
		this.xakhir=xakhir;
		this.player=player;
		isDead = false;
        hasDestroyed = false;
        nowPosition = new Vector2();
        staticEnemy = new TextureRegion(new TextureRegion(getTexture(), 0, 0, 547, 968));
        DefineEnemy();
        
        enemybullet = new ArrayList<EnemyFire>();
        
        generateAnimation();
	}
	public abstract void generateAnimation();
	public abstract void update(float dt);
	
	public abstract void DefineEnemy();
	public abstract void defineHitBox(int x, int y);
    public abstract void enemyMovement();
    public abstract void onHit();
    public abstract Vector2 getPosition();
    public abstract int getHP();
    public abstract void setHP(int hp);
    public abstract int getDamage();
    public abstract void setDamage(int dmg);
	public abstract int getScore();
	
	public Vector2 getNowPosition() {
		return nowPosition;
	}

	public void setNowPosition(Vector2 nowPosition) {
		this.nowPosition = nowPosition;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public boolean isHasDestroyed() {
		return hasDestroyed;
	}

	public void setHasDestroyed(boolean hasDestroyed) {
		this.hasDestroyed = hasDestroyed;
	}

	public ArrayList<EnemyFire> getEnemybullet() {
		return enemybullet;
	}

	public void setEnemybullet(ArrayList<EnemyFire> enemybullet) {
		this.enemybullet = enemybullet;
	}
}
