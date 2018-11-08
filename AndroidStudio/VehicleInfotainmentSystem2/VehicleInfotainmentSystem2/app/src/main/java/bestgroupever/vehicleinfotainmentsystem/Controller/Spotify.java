package bestgroupever.vehicleinfotainmentsystem.Controller;
import bestgroupever.vehicleinfotainmentsystem.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.ImageUri;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;
import com.spotify.protocol.types.Uri;

public class Spotify extends AppCompatActivity {

    Intent intent;

    private static final String CLIENT_ID = "e9fc1156fec14e98a9d676025ae28857";
    private static final String REDIRECT_URI = "bestgroupever.vehicleinfotainmentsystem.Controller.Controller://callback";
    private SpotifyAppRemote mSpotifyAppRemote;
    TextView music_info;
    ImageView album_image;
    ImageUri imageUri;
    //private TextView mRecentErrorView;

    //private final ErrorCallback mErrorCallback = throwable -> logError(throwable, "Boom!");
    //private static final String TAG = Spotify.class.getSimpleName();

    //ImageButton playButton = (ImageButton) findViewById(R.id.playButton);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spotify);





        /*
        //trying to set up a connection to the button in the GUI
        ImageButton playButton = findViewById(R.id.playButton);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //playButtonClicked();
            }
        });
        */

    }

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

        music_info = findViewById(R.id.tv_song_info);
        album_image = findViewById(R.id.album_view);



        // Play a playlist
        mSpotifyAppRemote.getPlayerApi().play("spotify:user:spotify:playlist:37i9dQZF1E35wPm8FVh0t8");

        // Subscribe to PlayerState
        mSpotifyAppRemote.getPlayerApi()
                .subscribeToPlayerState()
                .setEventCallback(new Subscription.EventCallback<PlayerState>() {

                    public void onEvent(PlayerState playerState) {
                        final Track track = playerState.track;
                        if (track != null) {
                            Log.d("Spotify", track.name + " by " + track.artist.name);
                            music_info.setText(track.name + " by " + track.artist.name);

                         // AHHHHH   Bitmap bitmap = MediaStore.Images.Media.getBitmap(.getContentResolver(), uri);




                        }
                    }
                });
    }
    public void interact(View v){

        switch (v.getId()){


            case R.id.back_button:
                SpotifyAppRemote.disconnect(mSpotifyAppRemote);
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.playButton:
                mSpotifyAppRemote.getPlayerApi().play("spotify:user:spotify:playlist:37i9dQZF1DX2sUQwD7tbmL");
                break;

            case R.id.nextTrack:
                mSpotifyAppRemote.getPlayerApi().skipNext();
                break;

            case R.id.previousTrack:
                mSpotifyAppRemote.getPlayerApi().skipPrevious();
                break;
        }


    }
/*
    private void logError(Throwable t, String msg) {
        Toast.makeText(this, "Error: " + msg, Toast.LENGTH_SHORT).show();
        Log.e(TAG, msg, t);
        mRecentErrorView.setText(String.valueOf(t));
    }

    private void logMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        Log.d(TAG, msg);
    }
*/
}

