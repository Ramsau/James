package com.example.james

import android.os.Looper
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import androidx.appcompat.app.AppCompatActivity
import java.net.URL

class MainActivity : AppCompatActivity() {
}

class MyTileService : TileService() {
    override fun onTileAdded() {
        super.onTileAdded()

        // Update state
        qsTile.state = Tile.STATE_ACTIVE

        // Update looks
        qsTile.updateTile();
    }

    override fun onClick() {
        super.onClick()

        if(qsTile.state == Tile.STATE_ACTIVE){
            // Turn the light off
            qsTile.state = Tile.STATE_INACTIVE
            turnOffLight()
        }
        else{
            // Turn the light on
            qsTile.state = Tile.STATE_ACTIVE
            turnOnLight()
        }

        qsTile.updateTile();
    }

    fun turnOnLight(){
        Thread {
            Looper.prepare();
            try {
                URL("http://192.168.0.8:2060/lights?turn=on").readText()
            } catch (e : Exception) {
            }
        }.start();
    }

    fun turnOffLight(){
        Thread {
            try {
                URL("http://192.168.0.8:2060/lights?turn=off").readText()
            } catch (e : Exception) {
            }
        }.start();
    }

}
