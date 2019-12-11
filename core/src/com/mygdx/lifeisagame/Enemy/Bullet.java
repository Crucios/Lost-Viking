package com.mygdx.lifeisagame.Enemy;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.lifeisagame.Player.Player;


public class Bullet extends Sprite {
    public World world;
    public Body b2body;
    protected Player player;

    private Vector2 position;
    private Vector2 originPosition;
    
    private Animation fireAnimation;
    
    private int damage;

    private boolean toRight;
    private boolean hasDestroyed;
    private boolean setToDestroy;
    
    private float range;
    protected float stateTimer;
    
    public EnemyFire(World world, Vector2 position, Player player, float range) {
    	super(new AtlasRegion(new TextureAtlas("Resources/Monster/Monsters.pack").findRegion("Monsters")));
        this.world = world;
        this.position = position;
        originPosition = new Vector2(position);
       
        this.player = player;
        this.range = range;
        toRight = false;
        damage = 12;
        hasDestroyed = false;
        setToDestroy = false;
        stateTimer = 0;
        
        defineEnemyBullet();
        
        //Generate Animation
        generateAnimation();
    }
    
    public void generateAnimation() {
    	setBounds(0,0,25 / GazeintoAbyss.PPM,33 / GazeintoAbyss.PPM);
		
		Array<TextureRegion> frames = new Array<TextureRegion>();
		for(int i=0;i<3;i++) {
			frames.add(new TextureRegion(getTexture(), 293 + i*29, 33 ,29, 28));
		}
		fireAnimation = new Animation(0.1f, frames);
		frames.clear();
    }

    
    public TextureRegion getFrame(float dt) {

		TextureRegion region;
		region = (TextureRegion) fireAnimation.getKeyFrame(stateTimer, true);
		setSize(0.5f, 0.5f);
		
		if(!toRight && !region.isFlipX()) {
			region.flip(true, false);
		}
		else if(toRight && region.isFlipX()) {
			region.flip(true, false);
		}
		
		stateTimer = stateTimer + dt;
		
		return region;
    }
    
    public void update(float dt) {
        if(toRight) {
            b2body.setLinearVelocity(6f, 0);
        }
        else {
            b2body.setLinearVelocity(-6f,0);
        }
        
        setPosition(new Vector2(b2body.getPosition().x, b2body.getPosition().y));
        setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
        
        
        if(position.x * GazeintoAbyss.PPM >= originPosition.x + range && toRight)
        	setToDestroy = true;
        else if(position.x * GazeintoAbyss.PPM <= originPosition.x - range && !toRight)
        	setToDestroy = true;
        
        if(setToDestroy && !hasDestroyed) {
        	hasDestroyed = true;
        	world.destroyBody(b2body);
        }
        
        System.out.println("Range: " + range);
    }
    public void defineHitBox(int x, int y) {
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(x / GazeintoAbyss.PPM, y / GazeintoAbyss.PPM);

        fdef.shape = shape;
        fdef.friction = 0.0f;
        fdef.isSensor = true;
        b2body.createFixture(fdef);
        b2body.createFixture(fdef).setUserData(this);
    }

    public void defineEnemyBullet() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(position.x / GazeintoAbyss.PPM,position.y / GazeintoAbyss.PPM);
        bdef.type = BodyDef.BodyType.KinematicBody;
        b2body = world.createBody(bdef);

        defineHitBox(3,1);
    }
    
    public void setToRight(boolean toRight) {
        this.toRight = toRight;
    }

    public void onHit() {
    	if(player.isFreetoHit()) {
    		GazeintoAbyss.manager.get("Resources/Sound/player-hit.ogg",Sound.class).play();
    		player.setHitPoint(player.getHitPoint() - damage);
    		player.setHasHit(true);
    		setToDestroy = true;
    	}
    }

    public void setPosition(Vector2 positions) {
        this.position = positions;
    }

    public Vector2 getPosition() {
        return position;
    }
	public boolean isHasDestroyed() {
		return hasDestroyed;
	}
	public void setHasDestroyed(boolean hasDestroyed) {
		this.hasDestroyed = hasDestroyed;
	}

}
