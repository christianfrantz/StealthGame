package com.stealth.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.stealth.game.Sprites.Player;

/**
 * Created by imont_000 on 11/12/2016.
 */

public class GunBase {
    public int clipSize;
    public int maxBullets;
    public boolean hasLaser;
    public float timeBetweenShots;
    public float shotTimer;
    public boolean canShoot;
    public Bullet bullet;
    //pistol has 1 second, smg has .5 second, sniper has 3 second

    public GunBase(int cs, int mb, boolean hl){
        this.clipSize = cs;
        this.hasLaser = hl;
        this.maxBullets = mb;
        timeBetweenShots = 0.5f;
    }

    public void updateGun(float dt, Player player, World world){
        shotTimer += dt;
        if(shotTimer >= timeBetweenShots){
            canShoot = true;
            if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
                shootBullet(dt, player, world);
                shotTimer = 0;
            }
        }
    }

    public void shootBullet(float dt, Player player, World world){
        if(canShoot == true){
            bullet = new Bullet(player, world);
            bullet.position = player.body.getWorldCenter();
            bullet.isActive = true;
            bullet.update(dt, player);
        }
    }
    public void drawBullets(Player player){

    }
}
