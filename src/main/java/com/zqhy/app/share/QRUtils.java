package com.zqhy.app.share;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.zqhy.app.core.tool.utilcode.SizeUtils;
import com.zqhy.app.newproject.R;

import java.util.Hashtable;

/**
 *
 * @author lihan
 * @date 2016/5/12
 */
public class QRUtils {

    // 最小留白长度, 单位: px
    private static final int PADDING_SIZE_MIN = 6;

    //要转换的地址或字符串,可以是中文
    public static Bitmap createQRImage(Context mContext, String url, int QR_WIDTH, int QR_HEIGHT,boolean isContainsLogo) {
        try {
            //判断URL合法性
            if (url == null || "".equals(url) || url.length() < 1) {
                return null;
            }

            if (QR_WIDTH <= 0) {
                QR_WIDTH = SizeUtils.dp2px(mContext, 144);
            }
            if (QR_HEIGHT <= 0) {
                QR_HEIGHT = SizeUtils.dp2px(mContext, 144);
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);

            boolean isFirstBlackPoint = false;
            int startX = 0;
            int startY = 0;

            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
            //下面这里按照二维码的算法，逐个生成二维码的图片，
            //两个for循环是图片横列扫描的结果
            for (int y = 0; y < QR_HEIGHT; y++) {
                for (int x = 0; x < QR_WIDTH; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * QR_WIDTH + x] = 0xff000000;
                        if (isFirstBlackPoint == false) {
                            isFirstBlackPoint = true;
                            startX = x;
                            startY = y;
                        }
                    } else {
                        pixels[y * QR_WIDTH + x] = 0xffffffff;
                    }
                }
            }

            //------------------添加图片部分------------------//
            //生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);

            // 剪切中间的二维码区域，减少padding区域
            if (startX <= PADDING_SIZE_MIN) {
                return bitmap;
            }

            int x1 = startX - PADDING_SIZE_MIN;
            int y1 = startY - PADDING_SIZE_MIN;
            if (x1 < 0 || y1 < 0) {
                return bitmap;
            }

            int w1 = QR_WIDTH - x1 * 2;
            int h1 = QR_HEIGHT - y1 * 2;

            Bitmap bitmapQR = Bitmap.createBitmap(bitmap, x1, y1, w1, h1);

            Bitmap bitmapLogo = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_launcher);
            if(isContainsLogo){
                bitmapQR = addLogo(bitmapQR,getRoundBitmap(bitmapLogo.copy(Bitmap.Config.ARGB_8888, true)));
            }
            //显示到一个ImageView上面
            return bitmapQR;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap createQRImage(Context mContext, String url, int QR_WIDTH, int QR_HEIGHT) {
        return createQRImage(mContext,url,QR_WIDTH,QR_HEIGHT,false);
    }

    /**
     * 在二维码中间添加Logo图案
     */
    private static Bitmap addLogo(Bitmap src, Bitmap logo) {
        if (src == null) {
            return null;
        }

        if (logo == null) {
            return src;
        }

        //获取图片的宽高
        int srcWidth = src.getWidth();
        int srcHeight = src.getHeight();
        int logoWidth = logo.getWidth();
        int logoHeight = logo.getHeight();

        if (srcWidth == 0 || srcHeight == 0) {
            return null;
        }

        if (logoWidth == 0 || logoHeight == 0) {
            return src;
        }

        //logo大小为二维码整体大小的1/5
        float scaleFactor = srcWidth * 1.0f / 4 / logoWidth;
        Bitmap bitmap = Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);
        try {
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(src, 0, 0, null);
            canvas.scale(scaleFactor, scaleFactor, srcWidth / 2, srcHeight / 2);
            canvas.drawBitmap(logo, (srcWidth - logoWidth) / 2, (srcHeight - logoHeight) / 2, null);

            canvas.save();
            canvas.restore();
        } catch (Exception e) {
            bitmap = null;
            e.getStackTrace();
        }

        return bitmap;
    }

    public static Bitmap getRoundBitmap(Bitmap bitmap){
        int roundPx = 12;
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        //这里的颜色决定了边缘的颜色
        paint.setColor(Color.WHITE);
        int w = bitmap.getWidth() ;
        int h = bitmap.getHeight() ;
        RectF rectF = new RectF(0, 0, w, h);

        Bitmap roundBitmap = getCroppedBitmap(bitmap, w, roundPx);

        Canvas canvas = new Canvas(bitmap);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawBitmap(roundBitmap, 0, 0, paint);
        return roundBitmap;
    }

    public static Bitmap getCroppedBitmap(Bitmap bmp, int length,int roundPx) {

        Bitmap sbmp;
        if (bmp.getWidth() != length || bmp.getHeight() != length) {
            sbmp = Bitmap.createScaledBitmap(bmp, length, length, false);
        } else {
            sbmp = bmp;
        }

        Bitmap output = Bitmap.createBitmap(sbmp.getWidth(), sbmp.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);


        final RectF rectF = new RectF(0, 0, sbmp.getWidth(), sbmp.getHeight());
        final Rect rect = new Rect(6, 6, sbmp.getWidth() - 6, sbmp.getHeight() - 6);

        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.WHITE);

        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        canvas.drawBitmap(sbmp, rect, rectF, paint);

        return output;
    }

}
