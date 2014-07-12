package de.thm.thmlocator.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Created by atabaksahraei on 12.07.14.
 */
public class StaticLib {
    /**
     * This method returns a bitmap related to resource id. It is ready to use method, you can
     * use it by simply copying in your project.
     *
     * @param context Context of calling activity
     * @param drawableId Resource ID of bitmap drawable
     * @return Bitmap whose resource id was passed to method.
     */
    public static Bitmap getBitmapFromDrawableId(Context context,int drawableId){
        Bitmap bitmap = null;
        try {
            BitmapDrawable drawable = (BitmapDrawable)context.getResources().getDrawable(drawableId);
            bitmap = drawable.getBitmap();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    /**
     * This method returns a bitmap related to drawable. It is ready to use method, you can
     * use it by simply copying in your project.
     *
     * @param drawable Drawable resource of image
     * @return Bitmap whose resource id was passed to method.
     */
    public static Bitmap getBitmapFromDrawable(Drawable drawable){
        Bitmap bitmap = null;
        try {
            BitmapDrawable bitmapDrawable = (BitmapDrawable)drawable;
            bitmap = bitmapDrawable.getBitmap();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
