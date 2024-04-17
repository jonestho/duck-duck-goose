package com.game.duckduckgoose

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class GameMusicService: Service() {
    private lateinit var musicPlayer: MediaPlayer

    override fun onCreate(){
        super.onCreate()

        musicPlayer = MediaPlayer.create(this, R.raw.game_music)
        musicPlayer.isLooping = true
        musicPlayer.setVolume(1f, 1f)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        musicPlayer.start()
        return START_STICKY
    }

    override fun onDestroy() {
        musicPlayer.stop()
        musicPlayer.release()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}