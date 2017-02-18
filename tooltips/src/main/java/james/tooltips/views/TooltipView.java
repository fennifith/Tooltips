package james.tooltips.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import james.tooltips.R;

public class TooltipView extends FrameLayout {

    public AppCompatImageView imageView;
    public TextView textView;

    private int maxWidth;

    public TooltipView(Context context) {
        super(context);
        init();
    }

    public TooltipView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TooltipView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public TooltipView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init() {
        setAlpha(0);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setBackground(DrawableCompat.wrap(ContextCompat.getDrawable(getContext(), R.drawable.tooltip)));

        View v = LayoutInflater.from(getContext()).inflate(R.layout.layout_tooltip, this, false);
        imageView = (AppCompatImageView) v.findViewById(R.id.image);
        textView = (TextView) v.findViewById(R.id.text);
        addView(v);
    }

    public void setMaximumWidth(int maxWidth) {
        this.maxWidth = maxWidth;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (maxWidth > 0 && maxWidth < MeasureSpec.getSize(widthMeasureSpec))
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(maxWidth, MeasureSpec.getMode(widthMeasureSpec));

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
