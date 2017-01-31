package james.tooltips.utils;

import android.content.res.Resources;
import android.view.View;

public class ViewUtils {

    public static float getCenterX(View view) {
        return view.getX() + (view.getWidth() / 2);
    }

    public static float getCenterY(View view) {
        return view.getY() + (view.getHeight() / 2);
    }

    public static float getUncenteredX(View view, float centerX) {
        return centerX - (view.getWidth() / 2);
    }

    public static float getUncenteredY(View view, float centerY) {
        return centerY - (view.getHeight() / 2);
    }

    public static void setCenterX(View view, float x) {
        view.setX(x - (view.getWidth() / 2));
    }

    public static void setCenterY(View view, float y) {
        view.setY(y - (view.getHeight() / 2));
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

}
