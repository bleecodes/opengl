package com.first.bleed.myfirstgame;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by BLEED on 10/31/2016.
 */

public class MyGLSV extends GLSurfaceView {
    private final MyGLSVRenderer myGLSVRender;

    public MyGLSV(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        myGLSVRender = new MyGLSVRenderer();
        setRenderer(myGLSVRender);
//        setRenderMode(RENDERMODE_WHEN_DIRTY);
    }
}
