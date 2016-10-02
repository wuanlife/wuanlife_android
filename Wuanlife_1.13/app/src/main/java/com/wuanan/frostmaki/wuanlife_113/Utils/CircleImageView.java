package com.wuanan.frostmaki.wuanlife_113.Utils;

/**
 * Created by Frostmaki on 2016/9/27.
 */
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * 自定义的圆形ImageView，可以直接当组件在布局中使用。
 */
@SuppressLint("DrawAllocation")
public class CircleImageView extends ImageView{

    private Paint paint ;
    public CircleImageView(Context context) {
        this(context,null);
    }
    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        paint = new Paint();

    }

    /**
     *  绘制圆形图片
     *  @author se7en
     */
    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {

        Drawable drawable = getDrawable();
        if (drawable != null) {

            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            //Log.e("Bitmap++------", String.valueOf((bitmap==null)));
            if (bitmap!=null){
                Bitmap b = getCircleBitmap(bitmap, 14);
                int z = Math.min(b.getWidth(),b.getHeight());
                final Rect rectSrc = new Rect(0, 0, z , z);
                final Rect rectDest = new Rect(0,0,getWidth(),getHeight());
                paint.reset();
                canvas.drawBitmap(b, rectSrc, rectDest, paint);
            }


        } else {
            super.onDraw(canvas);
        }
    }

    /**
     * 获取圆形图片方法
     * @param bitmap
     * @param pixels
     * @return Bitmap
     * @author se7en
     */
    private Bitmap getCircleBitmap(Bitmap bitmap, int pixels) {
        //Log.e("Bitmap+++++", String.valueOf((bitmap==null)));
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        int min = Math.min(bitmap.getWidth(),bitmap.getHeight());
        final Rect rect = new Rect(0, 0, min , min);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(min / 2, min / 2, min / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }
}
