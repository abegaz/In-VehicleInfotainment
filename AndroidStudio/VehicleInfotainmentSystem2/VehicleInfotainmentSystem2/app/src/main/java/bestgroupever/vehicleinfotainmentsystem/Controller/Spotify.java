package bestgroupever.vehicleinfotainmentsystem.Controller;
import bestgroupever.vehicleinfotainmentsystem.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.client.ErrorCallback;
import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;

public class Spotify extends AppCompatActivity {

    private static final String CLIENT_ID = "e9fc1156fec14e98a9d676025ae28857";
    private static final String REDIRECT_URI = "bestgroupever.vehicleinfotainmentsystem.Controller.Controller://callback";
    private static final String PLAYLIST_URI = "spotify:user:spotify:playlist:37i9dQZF1E35wPm8FVh0t8";
    private static final String TAG = Spotify.class.getSimpleName();
    private final ErrorCallback mErrorCallback = throwable -> logError(throwable, "Boom!");
    private SpotifyAppRemote mSpotifyAppRemote;
    private TrackProgressBar mTrackProgressBar;
    private SeekBar mSeekBar;
    private int count = 0; //counts how many times play button is pressed
    private TextView mRecentErrorView;
    Intent intent;
    TextView music_info;
    ImageView album_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spotify);
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

        // Subscribe to PlayerState
        mSpotifyAppRemote.getPlayerApi()
                .subscribeToPlayerState()
                .setEventCallback(new Subscription.EventCallback<PlayerState>() {
                    public void onEvent(PlayerState playerState) {
                        final Track track = playerState.track;
                        if (playerState.track != null) {
                            Log.d("Spotify", track.name + " by " + track.artist.name);
                            music_info.setText(track.name + " by " + track.artist.name);

                            mSpotifyAppRemote.getImagesApi()
                                    .getImage(track.imageUri)
                                    .setResultCallback(bitmap -> album_image.setImageBitmap(bitmap));

                            mTrackProgressBar.setDuration(playerState.track.duration);
                            mTrackProgressBar.update(playerState.playbackPosition);
                        }

                        if (playerState.playbackSpeed > 0) {
                            mTrackProgressBar.unpause();
                        } else {
                            mTrackProgressBar.pause();
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
        if (count == 0) {
            playUri(PLAYLIST_URI);
            count++;
        } else
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

    public class TrackProgressBar {

        private static final int LOOP_DURATION = 500;
        private final SeekBar mSeekBar;
        private final Handler mHandler;

        private final SeekBar.OnSeekBarChangeListener mSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mSpotifyAppRemote.getPlayerApi().seekTo(seekBar.getProgress())
                        .setErrorCallback(mErrorCallback);
            }
        };

        private final Runnable mSeekRunnable = new Runnable() {
            @Override
            public void run() {
                int progress = mSeekBar.getProgress();
                mSeekBar.setProgress(progress + LOOP_DURATION);
                mHandler.postDelayed(mSeekRunnable, LOOP_DURATION);
            }
        };

        private TrackProgressBar(SeekBar seekBar) {
            mSeekBar = seekBar;
            mSeekBar.setOnSeekBarChangeListener(mSeekBarChangeListener);
            mHandler = new Handler();
        }

        private void setDuration(long duration) {
            mSeekBar.setMax((int) duration);
        }

        private void update(long progress) {
            mSeekBar.setProgress((int) progress);
        }

        private void pause() {
            mHandler.removeCallbacks(mSeekRunnable);
        }

        private void unpause() {
            mHandler.removeCallbacks(mSeekRunnable);
            mHandler.postDelayed(mSeekRunnable, LOOP_DURATION);
        }
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
