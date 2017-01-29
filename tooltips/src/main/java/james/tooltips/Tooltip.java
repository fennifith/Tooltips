package james.tooltips;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import james.tooltips.utils.ViewUtils;
import james.tooltips.views.TooltipView;

public class Tooltip implements ValueAnimator.AnimatorUpdateListener {

    private Context context;
    private ViewGroup rootView;
    private TooltipView tooltipView;
    private Position position = Position.BELOW;
    private int padding = ViewUtils.dpToPx(16);

    public Tooltip(Activity activity) {
        context = activity;
        rootView = (ViewGroup) activity.findViewById(android.R.id.content);
        tooltipView = new TooltipView(context);
    }

    public Tooltip(ViewGroup rootView) {
        context = rootView.getContext();
        this.rootView = rootView;
        tooltipView = new TooltipView(context);
    }

    public Tooltip setBackground(@NonNull Drawable drawable) {
        tooltipView.setBackground(drawable);
        return this;
    }

    public Tooltip setBackgroundColor(@ColorInt int color) {
        DrawableCompat.setTint(tooltipView.getBackground(), color);
        return this;
    }

    public Tooltip setText(@NonNull String text) {
        tooltipView.textView.setText(text);
        return this;
    }

    public Tooltip setTextColor(@ColorInt int color) {
        tooltipView.textView.setTextColor(color);
        return this;
    }

    public Tooltip setPosition(Position position) {
        this.position = position;
        return this;
    }

    public Tooltip setPadding(int padding) {
        this.padding = padding;
        return this;
    }

    public void attachTo(View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        showFor(v);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        dismiss();
                        break;
                }
                return false;
            }
        });
    }

    public void showFor(final View view) {
        if (tooltipView.getParent() != null) return;

        rootView.addView(tooltipView);
        tooltipView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                switch (position) {
                    case ABOVE:
                        ViewUtils.setCenterX(tooltipView, ViewUtils.getCenterX(view));
                        tooltipView.setY((view.getTop() - padding) - tooltipView.getHeight());
                        break;
                    case BELOW:
                        ViewUtils.setCenterX(tooltipView, ViewUtils.getCenterX(view));
                        tooltipView.setY(view.getBottom() + padding);
                        break;
                    case LEFT:
                        tooltipView.setX((view.getLeft() - padding) - tooltipView.getWidth());
                        ViewUtils.setCenterY(tooltipView, ViewUtils.getCenterY(view));
                        break;
                    case RIGHT:
                        tooltipView.setX(view.getRight() + padding);
                        ViewUtils.setCenterY(tooltipView, ViewUtils.getCenterY(view));
                        break;
                    case CENTER:
                        ViewUtils.setCenterX(tooltipView, ViewUtils.getCenterX(view));
                        ViewUtils.setCenterY(tooltipView, ViewUtils.getCenterY(view));
                        break;
                }

                ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
                animator.addUpdateListener(Tooltip.this);
                animator.start();

                tooltipView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    public void showFor(final int x, final int y) {
        if (tooltipView.getParent() != null) return;

        rootView.addView(tooltipView);
        tooltipView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                switch (position) {
                    case ABOVE:
                        ViewUtils.setCenterX(tooltipView, x);
                        tooltipView.setY((y - padding) - tooltipView.getHeight());
                        break;
                    case BELOW:
                        ViewUtils.setCenterX(tooltipView, x);
                        tooltipView.setY(y + padding);
                        break;
                    case LEFT:
                        tooltipView.setX((x - padding) - tooltipView.getWidth());
                        ViewUtils.setCenterY(tooltipView, y);
                        break;
                    case RIGHT:
                        tooltipView.setX(x + padding);
                        ViewUtils.setCenterY(tooltipView, y);
                        break;
                    case CENTER:
                        ViewUtils.setCenterX(tooltipView, x);
                        ViewUtils.setCenterY(tooltipView, y);
                        break;
                }

                ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
                animator.addUpdateListener(Tooltip.this);
                animator.start();

                tooltipView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    public void dismiss() {
        if (tooltipView.getParent() == null) return;

        ValueAnimator animator = ValueAnimator.ofFloat(1, 0);
        animator.addUpdateListener(Tooltip.this);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                rootView.removeView(tooltipView);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        animator.start();
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        tooltipView.setAlpha((float) animation.getAnimatedValue());
    }

    public enum Position {
        ABOVE,
        BELOW,
        LEFT,
        RIGHT,
        CENTER
    }
}
