package com.yigitozgumus.timer.domain

import java.io.BufferedInputStream
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip

interface SoundPlayer {
    fun playClickSound()
}

class DesktopSoundPlayer : SoundPlayer {
    override fun playClickSound() {
        this::class.java.classLoader.getResourceAsStream("mouse_click.wav")?.use { stream ->
            val audioInputStream = AudioSystem.getAudioInputStream(BufferedInputStream(stream))
            val clip: Clip = AudioSystem.getClip()
            clip.open(audioInputStream)
            clip.start()
        }
    }
}