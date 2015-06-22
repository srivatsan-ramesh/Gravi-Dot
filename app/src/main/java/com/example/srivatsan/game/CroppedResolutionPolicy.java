package com.example.srivatsan.game;

import android.view.View.MeasureSpec;
import org.andengine.engine.options.resolutionpolicy.BaseResolutionPolicy;
import org.andengine.opengl.view.RenderSurfaceView;
/**
 * Created by srivatsan on 26/5/15.
 */

public class CroppedResolutionPolicy extends BaseResolutionPolicy
{
    // ===========================================================
    // Fields
    // ===========================================================

    private final float mCameraWidth;
    private final float mCameraHeight;
    private float mMarginVertical;	// Pixels from top of canvas to visible area, and from bottom of canvas to visible area
    private float mMarginHorizontal;	// Pixels from left of canvas to visible area, and from right of canvas to visible area

    // ===========================================================
    // Constructors
    // ===========================================================

    public CroppedResolutionPolicy(final float nCameraWidth, final float nCameraHeight)
    {
        this.mCameraWidth = nCameraWidth;
        this.mCameraHeight = nCameraHeight;

        this.mMarginVertical = 0;
        this.mMarginHorizontal = 0;
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================
    public float getMarginVertical()
    {
        return this.mMarginVertical;
    }

    public float getMarginHorizontal()
    {
        return this.mMarginHorizontal;
    }


    @Override
    public void onMeasure(RenderSurfaceView pRenderSurfaceView, int pWidthMeasureSpec, int pHeightMeasureSpec)
    {
        BaseResolutionPolicy.throwOnNotMeasureSpecEXACTLY(pWidthMeasureSpec, pHeightMeasureSpec);

        int measuredWidth = MeasureSpec.getSize(pWidthMeasureSpec);
        int measuredHeight = MeasureSpec.getSize(pHeightMeasureSpec);

        final float nCamRatio = (float)mCameraWidth /  (float)mCameraHeight;
        final float nCanvasRatio = (float)measuredWidth /  (float)measuredHeight;

        if(  (float)measuredWidth /  (float)measuredHeight < nCamRatio )
        {
            // Scale to fit height, width will crop
            measuredWidth = (int) ( measuredHeight * nCamRatio);
            this.mMarginHorizontal = ( this.mCameraWidth - ( (float) this.mCameraHeight * nCanvasRatio ) ) / 2.0f;
        }
        else
        {
            // Scale to fit width, height will crop
            measuredHeight = (int) ( measuredWidth / nCamRatio );
            this.mMarginVertical = ( this.mCameraHeight - ( (float) this.mCameraWidth / nCanvasRatio ) ) / 2.0f;
        }

        pRenderSurfaceView.setMeasuredDimensionProxy(measuredWidth, measuredHeight);
    }
}

