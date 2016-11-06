package com.first.bleed.myfirstgame;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by BLEED on 10/31/2016.
 */
public class MyGLSVRenderer implements GLSurfaceView.Renderer {
    private Triangle mTriangle;
    private Square mSquare;

    //keeps shapes relatively the same when you change from portrait to landscape
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float [16];
    //rotation movement
    private float[] mRotationMatrix = new float[16];

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {

        mTriangle = new Triangle();
        mSquare = new Square();
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GLES20.glViewport(0,0,width,height);

        //keeps shapes relatively the same when you change from portrait to landscape
        float ratio = (float) width / height ;
        Matrix.frustumM(mProjectionMatrix, 0 , -ratio, ratio, -1, 1, 3, 7);

    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        //used for rotate movement
//        float[] scratch = new float[16];
//        long time = SystemClock.uptimeMillis() % 4000L;
//        float angle = 0.090f * ((int) time );
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

//        Matrix.setRotateM(mRotationMatrix, 0, angle, 0, 0, -1.0f);

        //mTriangle.draw();
        //keeps shapes relatively the same when you change from portrait to landscape
        Matrix.setLookAtM(mViewMatrix, 0 , 0 , 0, -3, 0f , 0f, 0f , 0f, 1.0f, 0.0f);
        Matrix.multiplyMM(mMVPMatrix, 0 , mProjectionMatrix, 0, mViewMatrix, 0);
       mTriangle.draw(mMVPMatrix);
        mSquare.draw(mMVPMatrix);
        //used for rotate movement
//        Matrix.multiplyMM(scratch, 0 , mMVPMatrix, 0, mRotationMatrix, 0);
//
//        mTriangle.draw(scratch);



    }

    public static int loadShader(int type, String shaderCode){
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }

}

