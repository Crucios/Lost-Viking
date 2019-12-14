package com.mygdx.lifeisagame.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.lifeisagame.Enemy.Projectiles.BaseProjectiles;
import com.mygdx.lifeisagame.Player.Bullet.BaseBullet;
import com.mygdx.lifeisagame.Sprites.InteractiveTileObject;

public class WorldContactListener implements ContactListener{

	@Override
	public void beginContact(Contact contact) {
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();
		
		if(fixA.getUserData() == "player" || fixB.getUserData() == "player") {
			Fixture body = fixA.getUserData() == "player" ? fixA : fixB;
			Fixture object = body == fixA ? fixB : fixA;
			
			//if object is interactive tile object
			if(object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())){
				((InteractiveTileObject) object.getUserData()).onHit();
			}
		}
	}

	@Override
	public void endContact(Contact contact) {
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		
	}

}

