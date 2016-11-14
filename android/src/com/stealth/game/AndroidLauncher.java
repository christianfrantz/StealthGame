package com.stealth.game;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;
import com.stealth.game.MainGame;
import com.stealth.game.PlayServices.PlayServices;

public class AndroidLauncher extends AndroidApplication implements PlayServices {
	private GameHelper gameHelper;
	private final static int requestCode = 1;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
		gameHelper.enableDebugLog(false);

		GameHelper.GameHelperListener gameHelperListener = new GameHelper.GameHelperListener() {
			@Override
			public void onSignInFailed() {

			}

			@Override
			public void onSignInSucceeded() {

			}
		};
		gameHelper.setup(gameHelperListener);
		initialize(new MainGame(this), config);
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		gameHelper.onStart(this);
	}

	@Override
	protected void onStop()
	{
		super.onStop();
		gameHelper.onStop();
	}

	@Override
	protected void onActivityResult(int requestCode, int result, Intent data)
	{
		super.onActivityResult(requestCode, result, data);
		gameHelper.onActivityResult(requestCode, result, data);
	}

	@Override
	public void signIn() {
		try{
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					gameHelper.beginUserInitiatedSignIn();
				}
			});
		}
		catch (Exception e){
			Gdx.app.log("MainActivity", "Log in failed");
		}
	}

	@Override
	public void signOut() {
		try
		{
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					gameHelper.signOut();
				}
			});
		}catch (Exception e){
			Gdx.app.log("main", "log out failed");
		}
	}

	@Override
	public void rateGame() {
		String str = "Playstore link";
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
	}

	@Override
	public void unlockAchievement() {
		//Games.Achievements.unlock(gameHelper.getApiClient(), getString(R.string.achievement));
	}

	@Override
	public void submitScore(int highScore) {
		if(isSignedIn() == true){
			//Games.Leaderboards.submitScore(gameHelper.getApiClient(), getString(R.string.leaderboard, highScore));
		}
	}

	@Override
	public void showAchievement() {
		if(isSignedIn() == true){
			//startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.getApiClient(), getString(R.string.achievement)), requestCode);
		}
		else
		{
			signIn();
		}
	}

	@Override
	public void showScore() {

	}

	@Override
	public boolean isSignedIn() {
		if(gameHelper.isSignedIn())
			return true;

		return false;
	}
}
