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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.client.CallResult;
import com.spotify.protocol.client.ErrorCallback;
import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.ImageUri;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;
import com.spotify.protocol.types.Uri;

public class Spotify extends AppCompatActivity {

    Intent intent;

    private static final String CLIENT_ID = "e9fc1156fec14e98a9d676025ae28857";
    private static final String REDIRECT_URI = "bestgroupever.vehicleinfotainmentsystem.Controller.Controller://callback";
    private static final String PLAYLIST_URI = "spotify:user:spotify:playlist:37i9dQZF1E35wPm8FVh0t8";

    private SpotifyAppRemote mSpotifyAppRemote;
    private int count = 0; //counts how many times play button is pressed

    TextView music_info;
    ImageView album_image;
    //ImageButton playButton = (ImageButton)findViewById(R.id.playButton);
    ImageUri imageUri;
    private TextView mRecentErrorView;

    private final ErrorCallback mErrorCallback = throwable -> logError(throwable, "Boom!");
    private static final String TAG = Spotify.class.getSimpleName();






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spotify);


        //mConnect = findViewById(R.id.connect);
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
                        //Spotify.this.onConnected();
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

        // Subscribe to PlayerState
        mSpotifyAppRemote.getPlayerApi()
                .subscribeToPlayerState()
                .setEventCallback(new Subscription.EventCallback<PlayerState>() {

                    public void onEvent(PlayerState playerState) {
                        final Track track = playerState.track;
                        if (playerState.track != null) {
                            Log.d("Spotify", track.name + " by " + track.artist.name);
                            music_info.setText(track.name + " by " + track.artist.name);

                         // AHHHHH   Bitmap bitmap = MediaStore.Images.Media.getBitmap(.getContentResolver(), uri);
                            mSpotifyAppRemote.getImagesApi()
                                    .getImage(track.imageUri)
                                    .setResultCallback(bitmap -> album_image.setImageBitmap(bitmap));




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
        }


    }

    public void onPlayButtonClicked(View playButton) {
        // Play or resume position
        count++;
        if (count == 1)
            playUri(PLAYLIST_URI);
        else
            mSpotifyAppRemote.getPlayerApi().resume();

    }

    public void onPauseButtonClicked(View pauseButton) {
        // Pause
        mSpotifyAppRemote.getPlayerApi().pause();
    }

    public void onSkipNextButtonClicked(View nextTrack) {
        // skips to next track
        mSpotifyAppRemote.getPlayerApi().skipNext();
    }

    public void onSkipPreviousButtonClicked(View previousTrack) {
        // skips to previous track
        mSpotifyAppRemote.getPlayerApi().skipPrevious();
    }

    private void playUri(String uri){
        mSpotifyAppRemote.getPlayerApi()
                .play(uri)
                .setResultCallback(empty -> logMessage("Play successful"))
                .setErrorCallback(mErrorCallback);
    }

    private void logError(Throwable t, String msg) {
        Toast.makeText(this, "Error: " + msg, Toast.LENGTH_SHORT).show();
        Log.e(TAG, msg, t);
        mRecentErrorView.setText(String.valueOf(t));
    }

    private void logMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        Log.d(TAG, msg);
    }



}
