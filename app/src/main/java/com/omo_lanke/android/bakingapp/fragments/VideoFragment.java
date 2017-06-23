package com.omo_lanke.android.bakingapp.fragments;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.omo_lanke.android.bakingapp.R;
import com.omo_lanke.android.bakingapp.data.Step;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VideoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoFragment extends Fragment implements ExoPlayer.EventListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "step";

    // TODO: Rename and change types of parameters
    private Step step;

    @BindView(R.id.playerView)
    SimpleExoPlayerView mPlayerView;

    @BindView(R.id.frag_info)
    TextView frag_info;
    Context context;

    @BindView(R.id.buttonNext)
    Button buttonNext;

    @BindView(R.id.buttonPrevious)
    Button buttonPrevious;

    long playbackPosition = 0L;
    int currentWindow = 0;
    boolean playWhenReady = true;

    String TAG = VideoFragment.class.getSimpleName();

    private SimpleExoPlayer mExoPlayer;
    private PlaybackStateCompat.Builder mStateBuilder;

    VideoFragment.OnVideoClickListener videoCallback;

    public interface OnVideoClickListener{
        void onVideoButtonSelected(int button);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try{
            videoCallback = (VideoFragment.OnVideoClickListener)context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement OnVideoClickListener");
        }
    }

    public VideoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param step Parameter 1.
     * @return A new instance of fragment VideoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VideoFragment newInstance(Step step) {
        VideoFragment fragment = new VideoFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, step);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            step = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        context = this.getContext();
        ButterKnife.bind(this, view);

        frag_info.setText(step.getDescription());
        if(!(step.getThumbnailURL()).isEmpty() || !(step.getVideoURL()).isEmpty()) {
            initializePlayer();
        }else {
            mPlayerView.setVisibility(View.GONE);
        }

        return view;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        releasePlayer();
    }

    @Override
    public void onStop(){
        super.onStop();
        releasePlayer();
    }

    @Override
    public void onPause(){
        super.onPause();
        releasePlayer();
    }

    private void initializePlayer(){
        if (mExoPlayer == null){
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(context)
                    , trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            mExoPlayer.prepare(buildMediaSource());
            mExoPlayer.setPlayWhenReady(playWhenReady);
            if(currentWindow != 0 && playbackPosition != 0L){
                mExoPlayer.seekTo(currentWindow, playbackPosition);
            }
        }
    }

    private MediaSource buildMediaSource() {
        // these are reused for both media sources we create below
        DefaultExtractorsFactory extractorsFactory =
                new DefaultExtractorsFactory();
        DefaultHttpDataSourceFactory dataSourceFactory =
                new DefaultHttpDataSourceFactory( "user-agent");

        ExtractorMediaSource videoSource =
                new ExtractorMediaSource(Uri.parse(step.getVideoURL()), dataSourceFactory,
                        extractorsFactory, null, null);
        return videoSource;
    }

    private void releasePlayer(){
        if (mExoPlayer != null) {
            playbackPosition = mExoPlayer.getCurrentPosition();
            currentWindow = mExoPlayer.getCurrentWindowIndex();
            playWhenReady = mExoPlayer.getPlayWhenReady();
        }
        try {
            mExoPlayer.stop();
            mExoPlayer.release();
        }catch (Exception ex){

        }

        mExoPlayer = null;
    }

    @OnClick(R.id.buttonNext)
    public void buttonNext(){
        videoCallback.onVideoButtonSelected(1);
    }

    @OnClick(R.id.buttonPrevious)
    public void buttonPrevious(){
        videoCallback.onVideoButtonSelected(0);
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object o) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray){

    }

    @Override
    public void onLoadingChanged(boolean b) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playBackState) {

        if(playBackState == ExoPlayer.STATE_READY  && playWhenReady) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    mExoPlayer.getCurrentPosition(), 1f);
        }else if((playBackState == ExoPlayer.STATE_READY)){

            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    mExoPlayer.getCurrentPosition(), 1f);
        }

    }

    @Override
    public void onPlayerError(ExoPlaybackException e) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }
}
