package de.ironjan.mensaupb.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class AspectRatioImageView extends ImageView {

    private static final int DEFAULT_WIDTH = 16;
    private static final int DEFAULT_HEIGHT = 9;
    private int requestedWidth = DEFAULT_WIDTH;
    private int requestedHeight = DEFAULT_HEIGHT;

    public AspectRatioImageView(Context context) {
        super(context);
    }

    public AspectRatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AspectRatioImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = width * requestedHeight / requestedWidth;
        setMeasuredDimension(width, height) ;
    }

    public void setRequestedDimensions(int requestedWidth, int requestedHeight) {
        this.requestedWidth = requestedWidth;
        this.requestedHeight = requestedHeight;
        invalidate();
    }
}
