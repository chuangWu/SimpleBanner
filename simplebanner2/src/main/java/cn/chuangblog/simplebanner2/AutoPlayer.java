package cn.chuangblog.simplebanner2;

import android.os.Handler;
import android.os.Looper;


public class AutoPlayer {

    public interface Playable {

         void playTo(int to);

         void playNext();

         void playPrevious();

         int getTotal();

         int getCurrent();
    }

    public enum PlayDirection {
        to_left, to_right
    }

    public enum PlayRecycleMode {
        repeat_from_start, play_back
    }

    private PlayDirection mDirection = PlayDirection.to_right;
    private PlayRecycleMode mPlayRecycleMode = PlayRecycleMode.repeat_from_start;
    private int mTimeInterval = 5000;
    private Playable mPlayable;

    private Runnable mTimerTask;

    private boolean mSkipNext = false;
    private int mTotal;
    private boolean mPlaying = false;
    private boolean mPaused = false;

    public AutoPlayer(Playable playable) {
        mPlayable = playable;
    }

    public void play() {
        play(0, PlayDirection.to_right);
    }

    public void skipNext() {
        mSkipNext = true;
    }

    public void play(int start, PlayDirection direction) {
        if (mPlaying)
            return;
        mTotal = mPlayable.getTotal();
        if (mTotal <= 1) {
            return;
        }
        mPlaying = true;
        playTo(start);

        final Handler handler = new Handler(Looper.myLooper());
        mTimerTask = new Runnable() {
            @Override
            public void run() {
                if (!mPaused) {
                    playNextFrame();
                }
                if (mPlaying) {
                    handler.postDelayed(mTimerTask, mTimeInterval);
                }
            }
        };
        handler.postDelayed(mTimerTask, mTimeInterval);
    }

    public void play(int start) {
        play(start, PlayDirection.to_right);
    }

    public void stop() {
        if (!mPlaying) {
            return;
        }

        mPlaying = false;
    }

    public AutoPlayer setTimeInterval(int timeInterval) {
        mTimeInterval = timeInterval;
        return this;
    }

    public AutoPlayer setPlayRecycleMode(PlayRecycleMode playRecycleMode) {
        mPlayRecycleMode = playRecycleMode;
        return this;
    }

    private void playNextFrame() {
        if (mSkipNext) {
            mSkipNext = false;
            return;
        }
        int current = mPlayable.getCurrent();
        if (mDirection == PlayDirection.to_right) {
            if (current == mTotal - 1) {
                if (mPlayRecycleMode == PlayRecycleMode.play_back) {
                    mDirection = PlayDirection.to_left;
                    playNextFrame();
                } else {
                    playTo(0);
                }
            } else {
                playNext();
            }
        } else {
            if (current == 0) {
                if (mPlayRecycleMode == PlayRecycleMode.play_back) {
                    mDirection = PlayDirection.to_right;
                    playNextFrame();
                } else {
                    playTo(mTotal - 1);
                }
            } else {
                playPrevious();
            }
        }
    }

    public void pause() {
        mPaused = true;
    }

    public void resume() {
        mPaused = false;
    }

    private void playTo(int to) {
        mPlayable.playTo(to);
    }

    private void playNext() {
        mPlayable.playNext();
    }

    private void playPrevious() {
        mPlayable.playPrevious();
    }
}
