package android.example.james

import android.os.AsyncTask
import android.os.Bundle
import android.service.quicksettings.Tile
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.service.quicksettings.TileService

import kotlinx.android.synthetic.main.activity_main.*
import java.net.HttpURLConnection
import java.net.URL
import kotlin.coroutines.startCoroutine

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
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
}

fun turnOnLight(){
    Thread({
        URL("http://192.168.0.8:2060/lights?turn=on").readText()
    }).start();
}

fun turnOffLight(){
    Thread({
        URL("http://192.168.0.8:2060/lights?turn=off").readText()
    }).start();
}


