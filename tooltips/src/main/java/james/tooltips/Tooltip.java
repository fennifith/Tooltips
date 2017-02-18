package james.tooltips;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
    private ValueAnimator animator;

    public Tooltip(Activity activity) {
        context = activity;
        rootView = (ViewGroup) activity.findViewById(android.R.id.content);
        init();
    }

    public Tooltip(ViewGroup rootView) {
        context = rootView.getContext();
        this.rootView = rootView;
        init();
    }

    private void init() {
        tooltipView = new TooltipView(context);

        int width = rootView.getWidth();
        if (width > 0)
            tooltipView.setMaximumWidth(width - (2 * padding));
        else {
            rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    tooltipView.setMaximumWidth((int) ((rootView.getWidth() - (2 * padding)) * 0.6));
                    rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            });
        }
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

    public Tooltip setIcon(@Nullable Drawable drawable) {
        if (drawable != null) {
            tooltipView.imageView.setVisibility(View.VISIBLE);
            tooltipView.imageView.setImageDrawable(drawable);
        } else
            tooltipView.imageView.setVisibility(View.GONE);

        return this;
    }

    public Tooltip setIconTint(@ColorInt int color, @NonNull PorterDuff.Mode mode) {
        tooltipView.imageView.setColorFilter(new PorterDuffColorFilter(color, mode));
        return this;
    }

    public Tooltip setPosition(@NonNull Position position) {
        this.position = position;
        return this;
    }

    public Tooltip setPadding(int padding) {
        this.padding = padding;
        return this;
    }

    public Tooltip setMaximumWidth(int maxWidth) {
        tooltipView.setMaximumWidth(maxWidth);
        return this;
    }

    public TooltipView getView() {
        return tooltipView;
    }

    public void setView(@NonNull TooltipView tooltipView) {
        this.tooltipView = tooltipView;
    }

    public void attachTo(@NonNull View view) {
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

    public void showFor(@NonNull final View view) {
        if (tooltipView.getParent() != null) return;
        else if (isAnimating()) animator.cancel();

        tooltipView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                float newX, newY;
                switch (position) {
                    case ABOVE:
                        newX = ViewUtils.getUncenteredX(tooltipView, ViewUtils.getCenterX(view));
                        newY = (view.getTop() - padding) - tooltipView.getHeight();
                        break;
                    case BELOW:
                        newX = ViewUtils.getUncenteredX(tooltipView, ViewUtils.getCenterX(view));
                        newY = view.getBottom() + padding;
                        break;
                    case LEFT:
                        newX = (view.getLeft() - padding) - tooltipView.getWidth();
                        newY = ViewUtils.getUncenteredY(tooltipView, ViewUtils.getCenterY(view));
                        break;
                    case RIGHT:
                        newX = view.getRight() + padding;
                        newY = ViewUtils.getUncenteredY(tooltipView, ViewUtils.getCenterY(view));
                        break;
                    default:
                        newX = ViewUtils.getUncenteredX(tooltipView, ViewUtils.getCenterX(view));
                        newY = ViewUtils.getUncenteredY(tooltipView, ViewUtils.getCenterY(view));
                        break;
                }

                tooltipView.setX(Math.min((rootView.getWidth() - tooltipView.getWidth()) - padding, Math.max(padding, newX)));
                tooltipView.setY(Math.min((rootView.getHeight() - tooltipView.getHeight()) - padding, Math.max(padding, newY)));

                animator = ValueAnimator.ofFloat(0, 1);
                animator.addUpdateListener(Tooltip.this);
                animator.start();

                tooltipView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        rootView.addView(tooltipView);
    }

    public void showFor(final int x, final int y) {
        if (tooltipView.getParent() != null) return;
        else if (isAnimating()) animator.cancel();

        tooltipView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                float newX, newY;
                switch (position) {
                    case ABOVE:
                        newX = ViewUtils.getUncenteredX(tooltipView, x);
                        newY = (y - padding) - tooltipView.getHeight();
                        break;
                    case BELOW:
                        newX = ViewUtils.getUncenteredX(tooltipView, x);
                        newY = y + padding;
                        break;
                    case LEFT:
                        newX = (x - padding) - tooltipView.getWidth();
                        newY = ViewUtils.getUncenteredY(tooltipView, y);
                        break;
                    case RIGHT:
                        newX = x + padding;
                        newY = ViewUtils.getUncenteredY(tooltipView, y);
                        break;
                    default:
                        newX = ViewUtils.getUncenteredX(tooltipView, x);
                        newY = ViewUtils.getUncenteredY(tooltipView, y);
                        break;
                }

                tooltipView.setX(Math.min((rootView.getWidth() - tooltipView.getWidth()) - padding, Math.max(padding, newX)));
                tooltipView.setY(Math.min((rootView.getHeight() - tooltipView.getHeight()) - padding, Math.max(padding, newY)));

                animator = ValueAnimator.ofFloat(0, 1);
                animator.addUpdateListener(Tooltip.this);
                animator.start();

                tooltipView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        rootView.addView(tooltipView);
    }

    public void dismiss() {
        if (tooltipView.getParent() == null) return;
        else if (isAnimating()) animator.cancel();

        animator = ValueAnimator.ofFloat(1, 0);
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

    public boolean isAnimating() {
        return animator != null && animator.isRunning();
    }

    public boolean isShowing() {
        return tooltipView.getParent() != null;
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        float scale = (float) animation.getAnimatedValue();
        tooltipView.setAlpha(scale);
    }

    public enum Position {
        ABOVE,
        BELOW,
        LEFT,
        RIGHT,
        CENTER
    }
}
