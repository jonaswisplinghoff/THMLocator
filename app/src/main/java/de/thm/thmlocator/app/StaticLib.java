package de.thm.thmlocator.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;

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

    public static String encodeTobase64(Bitmap image)
    {
        Bitmap immagex=image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.e("LOOK", imageEncoded);
        return imageEncoded;
    }
    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}
