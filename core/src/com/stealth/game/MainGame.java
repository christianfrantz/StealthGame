package com.stealth.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.stealth.game.PlayServices.PlayServices;
import com.stealth.game.Screens.PickClassScreen;
import com.stealth.game.Screens.PlayScreen;
import com.stealth.game.Screens.StartScreen;

public class MainGame extends Game {
	public SpriteBatch batch;
	public static final int vWidth = 1920;
	public static final int vHeight = 1080;
	public static final float PPM = 100;
	public static PlayServices playServices;

	public MainGame(PlayServices playServices){
		this.playServices = playServices;
	}

	public MainGame()
	{

	}
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new StartScreen(this));
	}

	public void SetPlayScreen(){
		setScreen(new PlayScreen(this));
	}
	public void SetClassScreen() {
		setScreen(new PickClassScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();

	}
}
