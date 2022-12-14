package com.kw.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.kw.gdx.constant.Constant;
import com.kw.gdx.manager.AnimationBaseScreen;
import com.kw.gdx.manager.ScreenTransition;

public class AnimationBaseGame extends BaseGame{
    private static final String TAG = AnimationBaseGame.class.getName();

    private boolean init;
    private AnimationBaseScreen currScreen;
    private AnimationBaseScreen nextScreen;
    private FrameBuffer currFbo;
    private FrameBuffer nextFbo;
    private SpriteBatch batch;
    private float transitionTime;
    private ScreenTransition screenTransition;

    public void setScreen(AnimationBaseScreen screen) {
        setScreen(screen, null);
    }

    public void setScreen(AnimationBaseScreen screen, ScreenTransition screenTransition) {
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();
        if (!init) {
            currFbo = new FrameBuffer(Pixmap.Format.RGB888, w, h, false);
            nextFbo = new FrameBuffer(Pixmap.Format.RGB888, w, h, false);
            batch = new SpriteBatch();
            init = true;
        }
        // Start new transition
        nextScreen = screen;
        nextScreen.show(); // Activate next screen
        nextScreen.resize(w, h);
        nextScreen.buildStage();
        Gdx.input.setInputProcessor(null); // Disable input
        this.screenTransition = screenTransition;
        transitionTime = 0;
    }

    @Override
    public void render() {
        // Get delta time and ensure an upper limit of one 60th second
        float deltaTime = Math.min(Gdx.graphics.getDeltaTime(), 1.0f / 60.0f);
        if (nextScreen == null) {
            // No ongoing transition
            if (currScreen != null) {
                currScreen.render(deltaTime);
            }
        } else {
            // Ongoing transition
            float duration = 0;
            if (screenTransition != null) {
                duration = screenTransition.getDuration();
            }
            transitionTime = Math.min(transitionTime + deltaTime, duration);
            if (screenTransition == null || transitionTime >= duration) {
                // No transition effect set or transition has just finished
                if (currScreen != null) {
                    currScreen.dispose();
                }
                nextScreen.resume();
                // Enable input for next screen
                Gdx.input.setInputProcessor(nextScreen.getInputProcessor());
                // Switch screens
                currScreen = nextScreen;
                nextScreen = null;
                screenTransition = null;
            } else {
                // Render screens to FBOs
                currFbo.begin();
                if (currScreen != null) {
                    currScreen.render(deltaTime);
                }
                currFbo.end();
                // WA: Applies the viewport to the camera and sets the glViewport
                if (currScreen != null) {
                    currScreen.getViewport().apply();
                }

                nextFbo.begin();
                nextScreen.render(deltaTime);
                nextFbo.end();
                // WA: Applies the viewport to the camera and sets the glViewport
                nextScreen.getViewport().apply();

                // Render transition effect to screen
                float alpha = transitionTime / duration;
                screenTransition.render(batch, currFbo.getColorBufferTexture(), nextFbo.getColorBufferTexture(), alpha);
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        if (currScreen != null) {
            currScreen.resize(width, height);
        }
        if (nextScreen != null) {
            nextScreen.resize(width, height);
        }
    }

    @Override
    public void pause() {
        if (currScreen != null) {
            currScreen.pause();
        }
    }

    @Override
    public void resume() {
        if (currScreen != null) {
            currScreen.resume();
        }
    }

    @Override
    public void dispose() {
        if (currScreen != null) {
            currScreen.dispose();
        }
        if (nextScreen != null) {
            nextScreen.dispose();
        }
        if (init) {
            currFbo.dispose();
            currScreen = null;
            nextFbo.dispose();
            nextScreen = null;
            batch.dispose();
            init = false;
        }
    }
}