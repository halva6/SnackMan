package de.halva6.snackman.controller;

import javafx.scene.media.AudioClip;

/**
 * Loads and provides sounds for the game and ensures that the sounds are
 * played.
 */
public class AudioController
{
	private static final String EAT_SOUND_PATH = "/audio/sounds/apple_bite.wav";
	private static final String HUNT_SOUND_PATH = "/audio/sounds/losetrumpet.wav";
	private static final String WIN_SOUND_PATH = "/audio/sounds/win.wav";
	private final AudioClip eatSound;
	private final AudioClip huntSound;
	private final AudioClip winSound;

	/**
	 * Creates an audio controller which loads all the sound.
	 */
	public AudioController()
	{
		this.eatSound = new AudioClip(getClass().getResource(EAT_SOUND_PATH).toExternalForm());
		this.huntSound = new AudioClip(getClass().getResource(HUNT_SOUND_PATH).toExternalForm());
		this.winSound = new AudioClip(getClass().getResource(WIN_SOUND_PATH).toExternalForm());
	}

	public void playEatSound()
	{
		this.eatSound.play();
	}

	public void playHuntSound()
	{
		this.huntSound.play();
	}

	public void playWinSound()
	{
		this.winSound.play();
	}
}
