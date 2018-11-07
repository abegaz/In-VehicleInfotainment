package bestgroupever.vehicleinfotainmentsystem.Controller.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.android.appremote.api.*;

import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.client.*;
import com.spotify.protocol.types.*;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;




import bestgroupever.vehicleinfotainmentsystem.R;


public class Spotify extends AppCompatActivity {
    private static final String CLIENT_ID = "your_client_id";
    private static final String REDIRECT_URI = "bestgroupever.vehicleinfotainmentsystem.Controller.Controller://callback";
    private SpotifyAppRemote mSpotifyAppRemote;

    //ImageButton playButton = (ImageButton) findViewById(R.id.playButton);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spotify);


        //trying to set up a connection to the button in the GUI
        ImageButton playButton = findViewById(R.id.playButton);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //playButtonClicked();
            }
        });


    }
/*
    private void playButtonClicked(){
        //plays a playlist
        mSpotifyAppRemote.getPlayerApi().play("spotify:user:spotify:playlist:37i9dQZF1DX2sUQwD7tbmL");

        mSpotifyAppRemote.getPlayerApi()
                .subscribeToPlayerState()
                .setEventCallback(new Subscription.EventCallback<PlayerState>() {

                }

        if(mSpotifyAppRemote..isPaused()){

            //??????
        }
    }
*/
    @Override
    protected void onStart() {
        super.onStart();
        ConnectionParams connectionParams =
                new ConnectionParams.Builder(CLIENT_ID)
                        .setRedirectUri(REDIRECT_URI)
                        .showAuthView(true)
                        .build();

        SpotifyAppRemote.connect(this, connectionParams,
                new Connector.ConnectionListener() {

                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                        mSpotifyAppRemote = spotifyAppRemote;
                        Log.d("Spotify", "Connected! Yay!");

                        // Now you can start interacting with App Remote
                        connected();

                    }

                    public void onFailure(Throwable throwable) {
                        Log.e("Spotify", throwable.getMessage(), throwable);

                        // Something went wrong when attempting to connect! Handle errors here
                    }
                });
    }

    @Override
    protected void onStop() {
        super.onStop();
        SpotifyAppRemote.disconnect(mSpotifyAppRemote);
    }



    private void connected() {

        // Play a playlist
        mSpotifyAppRemote.getPlayerApi().play("spotify:user:spotify:playlist:37i9dQZF1DX2sUQwD7tbmL");

        // Subscribe to PlayerState
        mSpotifyAppRemote.getPlayerApi()
                .subscribeToPlayerState()
                .setEventCallback(new Subscription.EventCallback<PlayerState>() {

                    public void onEvent(PlayerState playerState) {
                        final Track track = playerState.track;
                        if (track != null) {
                            Log.d("Spotify", track.name + " by " + track.artist.name);
                        }
                    }
                });
    }

}

