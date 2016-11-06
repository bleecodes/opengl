package com.first.bleed.myfirstgame;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by BLEED on 11/5/2016.
 */
public class Square {
    private FloatBuffer vertexBuffer;
    private ShortBuffer drawListBuffer;

    static final int COORDS_PER_VERTEX = 3;
    static float squareCoords[] = {
            -0.5f,  0.5f, 0.0f,   // top left
            -0.5f, -0.5f, 0.0f,   // bottom left
            0.5f, -0.5f, 0.0f,   // bottom right
            0.5f,  0.5f, 0.0f }; // top right

    private short drawOrder[] = {0,1,2,0,2,3};
    float color[] = {.23432f, .3232f, .5787f, 1.0f};
    private final String vertexShaderCode =
            "uniform mat4 uMVPMatrix;" +
            "attribute vec4 vPosition;"+
            "void main(){"+
            "gl_Position = uMVPMatrix * vPosition;"+
            "}";
    private final String fragmentShaderCode =
            "precision mediump float;"+
                    "uniform vec4 vColor;"+
                    "void main(){"+
                    "gl_FragColor = vColor;"+
                    "}";

    private final int mProgram;

    private int mPositionHandle;
    private int mColorHandle;
    private int mMVPMatrixHandle;
    private final int vertexCount = squareCoords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX *4;

    public Square(){
        ByteBuffer bb = ByteBuffer.allocateDirect(squareCoords.length*4);//coord vals * 4 bytes per float
bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(squareCoords);
        vertexBuffer.position(0);

        ByteBuffer dlb = ByteBuffer.allocateDirect(drawOrder.length*2);
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);

        int vertexShader = MyGLSVRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = MyGLSVRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);
        GLES20.glLinkProgram(mProgram);


    }

    public void draw (float[] mvpMatrix){
        GLES20.glUseProgram(mProgram);
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
        GLES20.glUniform4fv(mColorHandle,1,color,0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0 , vertexCount);
        GLES20.glDisableVertexAttribArray(mPositionHandle);
        //triangle keeps its shape from portrait to landscape
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
        GLES20.glDisableVertexAttribArray(mPositionHandle);

    }
    //draw()
}
