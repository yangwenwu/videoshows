package com.lemon.video.video.MediaPlayer;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.Surface;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.ext.okhttp.OkHttpDataSourceFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory;
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import com.google.android.exoplayer2.video.VideoListener;
import com.lemon.video.application.BaseApp;
import com.lzy.okgo.OkGo;

import java.io.File;
import java.util.concurrent.TimeUnit;

import cn.jzvd.JZMediaInterface;
import cn.jzvd.JZMediaManager;
import cn.jzvd.JZVideoPlayerManager;
import okhttp3.CacheControl;

/**
 * Created by MinhDV on 5/3/18.
 */

public class JZExoPlayer extends JZMediaInterface implements Player.EventListener, VideoListener {
    private static Cache cache = new SimpleCache(new File(BaseApp.getInstance().getCacheDir(),"videoCache"), new LeastRecentlyUsedCacheEvictor(1024 * 1024 * 1024l));
    private SimpleExoPlayer simpleExoPlayer;
    private Handler mainHandler;
    private Runnable callback;
    private final String TAG = "JZExoPlayer";

    @Override
    public void start() {
        simpleExoPlayer.setPlayWhenReady(true);
        simpleExoPlayer.getPlaybackState();
    }

    @Override
    public void prepare() {
        mainHandler = new Handler();
        Context context = JZVideoPlayerManager.getCurrentJzvd().getContext();
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        LoadControl loadControl = new DefaultLoadControl.Builder()
                .setTargetBufferBytes(100 * 1024 * 1024)
                .createDefaultLoadControl();
        RenderersFactory renderersFactory = new DefaultRenderersFactory(context);
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector, loadControl);
        DataSource.Factory dataSourceFactory = new CacheDataSourceFactory(cache, new OkHttpDataSourceFactory(OkGo.getInstance().getOkHttpClient(), "USER_AGENT", null, new CacheControl.Builder().minFresh(7, TimeUnit.DAYS).build()));
        String currUrl = currentDataSource.toString();
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(currUrl));
        simpleExoPlayer.addVideoListener(this);
        simpleExoPlayer.addListener(this);
        simpleExoPlayer.prepare(videoSource);
        simpleExoPlayer.setPlayWhenReady(true);
        callback = new onBufferingUpdate();
        mainHandler.post(callback);
    }

    @Override
    public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
        JZMediaManager.instance().currentVideoWidth = width;
        JZMediaManager.instance().currentVideoHeight = height;
        JZMediaManager.instance().mainThreadHandler.post(() -> {
            if (JZVideoPlayerManager.getCurrentJzvd() != null) {
                JZVideoPlayerManager.getCurrentJzvd().onVideoSizeChanged();
            }
        });
    }

    @Override
    public void onRenderedFirstFrame() {
        Log.e(TAG, "onRenderedFirstFrame");
    }

    private class onBufferingUpdate implements Runnable {
        @Override
        public void run() {
            final int percent = simpleExoPlayer.getBufferedPercentage();
            JZMediaManager.instance().mainThreadHandler.post(() -> {
                if (JZVideoPlayerManager.getCurrentJzvd() != null) {
                    JZVideoPlayerManager.getCurrentJzvd().setBufferProgress(percent);
                    refreshBuffer();
                }
            });
        }
    }

    private void refreshBuffer() {
        mainHandler.postDelayed(callback, 1000);
    }

    @Override
    public void pause() {
        if (simpleExoPlayer != null) {
            simpleExoPlayer.setPlayWhenReady(false);
            simpleExoPlayer.getPlaybackState();
        }
    }

    @Override
    public boolean isPlaying() {
        return simpleExoPlayer.getPlayWhenReady();
    }

    @Override
    public void seekTo(long time) {
        simpleExoPlayer.seekTo(time);
    }

    @Override
    public void release() {
        if (simpleExoPlayer != null) {
            simpleExoPlayer.release();
        }
        if (mainHandler != null)
            mainHandler.removeCallbacks(callback);
    }

    @Override
    public long getCurrentPosition() {
        if (simpleExoPlayer != null)
            return simpleExoPlayer.getCurrentPosition();
        else return 0;
    }

    @Override
    public long getDuration() {
        if (simpleExoPlayer != null)
            return simpleExoPlayer.getDuration();
        else return 0;
    }

    @Override
    public void setSurface(Surface surface) {
        simpleExoPlayer.setVideoSurface(surface);
        Log.e(TAG, "setSurface");
    }

    @Override
    public void setVolume(float leftVolume, float rightVolume) {
        simpleExoPlayer.setVolume(leftVolume);
        simpleExoPlayer.setVolume(rightVolume);
    }

    @Override
    public void onTimelineChanged(final Timeline timeline, Object manifest, final int reason) {
        Log.e(TAG, "onTimelineChanged");
    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {
        Log.e(TAG, "onLoadingChanged");
    }

    @Override
    public void onPlayerStateChanged(final boolean playWhenReady, final int playbackState) {
        Log.e(TAG, "onPlayerStateChanged" + playbackState + "/ready=" + String.valueOf(playWhenReady));
        JZMediaManager.instance().mainThreadHandler.post(() -> {
            if (JZVideoPlayerManager.getCurrentJzvd() != null) {
                switch (playbackState) {
                    case Player.STATE_IDLE: {
                    }
                    break;
                    case Player.STATE_BUFFERING: {
                        JZVideoPlayerManager.getCurrentJzvd().onStatePreparing();
                    }
                    break;
                    case Player.STATE_READY: {
                        if (playWhenReady) {
                            JZVideoPlayerManager.getCurrentJzvd().onPrepared();
                        }
                    }
                    break;
                    case Player.STATE_ENDED: {
                        JZVideoPlayerManager.getCurrentJzvd().onAutoCompletion();
                    }
                    break;
                }
            }
        });
    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
        Log.e(TAG, "onPlayerError" + error.toString());
        JZMediaManager.instance().mainThreadHandler.post(() -> {
            if (JZVideoPlayerManager.getCurrentJzvd() != null) {
                JZVideoPlayerManager.getCurrentJzvd().onError(1000, 1000);
            }
        });
    }

    @Override
    public void onPositionDiscontinuity(int reason) {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {
        JZMediaManager.instance().mainThreadHandler.post(() -> {
            if (JZVideoPlayerManager.getCurrentJzvd() != null) {
                JZVideoPlayerManager.getCurrentJzvd().onSeekComplete();
            }
        });
    }
}