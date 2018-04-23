package test.lxl.com.demo;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;

import java.io.File;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import test.lxl.com.demo.LargeImageView.LargeImageView;
import test.lxl.com.demo.LargeImageView.factory.FileBitmapDecoderFactory;
import test.lxl.com.demo.LargeImageView.glide.ProgressTarget;

/**
 * Created by Jin on 5/26/15.
 */
public final class GlideImageUtil {
    public static final int LOADING_PLACE_HOLDER = R.mipmap.default_image;
    public static final int FAILED_PLACE_HOLDER = R.mipmap.default_image;

    public static void load(Context context, Object url, ImageView imageView) {
        load(context, url, imageView, LOADING_PLACE_HOLDER, FAILED_PLACE_HOLDER);
    }


    public static void load(Context context, String url, ImageView imageView, int loadingDrawable) {
        load(context, url, imageView, loadingDrawable, FAILED_PLACE_HOLDER);
    }

    /**
     * @param context
     * @param url
     * @param imageView
     * @param strategy  {@link DiskCacheStrategy}
     *                  DiskCacheStrategy.ALL:缓存源资源和转换后的资源
     *                  DiskCacheStrategy.NONE:不作任何磁盘缓存
     *                  DiskCacheStrategy.SOURCE:缓存源资源
     *                  DiskCacheStrategy.SOURCE：缓存转换后的资源
     */
    public static void load(Context context, String url, ImageView imageView, DiskCacheStrategy strategy) {
        try {
            Glide.with(context).load(url).diskCacheStrategy(strategy).placeholder(LOADING_PLACE_HOLDER).crossFade(500).error(FAILED_PLACE_HOLDER).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    /**
     * 缓存处理过来的图片到本地
     *
     * @param context
     * @param url
     * @param imageView
     * @param loadingDrawable
     * @param failedDrawable
     */
    public static void load(Context context, Object url, ImageView imageView, int loadingDrawable, int failedDrawable) {
        load(context, DiskCacheStrategy.SOURCE, url, imageView, loadingDrawable, failedDrawable);
    }

    public static void load(Context context, int res, ImageView imageView) {
        try {
            Glide.with(context).load(res).diskCacheStrategy(DiskCacheStrategy.SOURCE).dontAnimate().into(imageView);
        } catch (Exception e) {
            return;
        }
    }

    public static void load(Context context, DiskCacheStrategy strategy, Object url, ImageView imageView, int loadingDrawable, int failedDrawable) {
        try {
            Glide.with(context.getApplicationContext())
                    .load(url)
                    .diskCacheStrategy(strategy)
                    .placeholder(loadingDrawable)
                    .error(failedDrawable)
                    .dontAnimate()
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    public static void load(Context context, Object url,int fadeDuration, ImageView imageView) {
        try {
            Glide.with(context.getApplicationContext())
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .placeholder(LOADING_PLACE_HOLDER)
                    .error(FAILED_PLACE_HOLDER)
                    .crossFade(fadeDuration)
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }


    /**
     * 加载图形图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadCropCircle(Context context, Object url, ImageView imageView, int loadingDrawable, int failedDrawable) {
        try {
            Glide.with(context).load(url).bitmapTransform(new CropCircleTransformation(context))
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(loadingDrawable).
                    error(failedDrawable)
                    .dontAnimate()
                    .into(imageView);
        } catch (Exception e) {
            return;
        }
    }

    /**
     * 加载图形图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadCropCircleAndBorder(Context context, Object url, ImageView imageView, int loadingDrawable, int failedDrawable, int borderWidth, int borderColor) {
        try {
            Glide.with(context).load(url).bitmapTransform(new GlideCircleTransform(context, borderWidth, borderColor))
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(loadingDrawable).
                    error(failedDrawable)
                    .dontAnimate()
                    .into(imageView);
        } catch (Exception e) {
            return;
        }


    }

    /**
     * 加载图形图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadCropCircle(Context context, Object url, ImageView imageView) {
        try {
            Glide.with(context).load(url).bitmapTransform(new CropCircleTransformation(context))
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(R.mipmap.default_image).
                    error(R.mipmap.default_image)
                    .dontAnimate()
                    .into(imageView);
        } catch (Exception e) {
            return;
        }
    }

    public static void loadCropCircle(Context context, Object url, int duration,ImageView imageView) {
        try {
            Glide.with(context).load(url).bitmapTransform(new CropCircleTransformation(context))
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(R.mipmap.default_image).
                    error(R.mipmap.default_image)
                    .crossFade(duration)
                    .into(imageView);
        } catch (Exception e) {
            return;
        }
    }

    public static void loadCropCircle(Context context, int res, ImageView imageView) {
        try {
            Glide.with(context).load(res).bitmapTransform(new CropCircleTransformation(context))
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(R.mipmap.default_image).
                    error(R.mipmap.default_image)
                    .dontAnimate()
                    .into(imageView);
        } catch (Exception e) {
            return;
        }
    }

    public static void loadCropCircle(Context context, int res,int duration, ImageView imageView) {
        try {
            Glide.with(context).load(res).bitmapTransform(new CropCircleTransformation(context))
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(R.mipmap.default_image).
                    error(R.mipmap.default_image)
                    .crossFade(duration)
                    .into(imageView);
        } catch (Exception e) {
            return;
        }
    }


    /**
     * 加载图形图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadCropCircle(Context context, Object url, ImageView imageView, int resourceId) {

        try {
            Glide.with(context).load(url).bitmapTransform(new CropCircleTransformation(context))
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(resourceId).
                    error(resourceId)
                    .dontAnimate()
                    .into(imageView);
        } catch (Exception e) {
            return;
        }


    }

    /**
     * 加载图形图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadCropCircle(Context context, String url, ImageView imageView, int resourceId, int borderWidth, int borderColor) {
        try {
            Glide.with(context).load(url).bitmapTransform(new GlideCircleTransform(context, borderWidth, borderColor))
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(resourceId).
                    error(resourceId)
                    .dontAnimate()
                    .into(imageView);
        } catch (Exception e) {
            return;
        }


    }


    /**
     * 圆角
     *
     * @param url
     * @param imageView
     */

    public static void loadRoundedCorners(Context context, String url, final ImageView imageView, int radius) {
        Glide.with(context)
                .load(url)
                .bitmapTransform(new RoundedCornersTransformation(context, radius, 0,
                        RoundedCornersTransformation.CornerType.ALL))
                .dontAnimate()
                .into(imageView);
    }






    public static void loadRoundedCorners(Context context, int res, final ImageView imageView, int radius) {
        Glide.with(context)
                .load(res)
                .bitmapTransform(new RoundedCornersTransformation(context, radius, 0,
                        RoundedCornersTransformation.CornerType.ALL))
                .dontAnimate()
                .into(imageView);
    }

    public static void loadRoundedCorners(Context context, String url, final ImageView imageView, int radius,int width,int height) {
        Glide.with(context)
                .load(url)
                .override(width,height)
                .bitmapTransform(new RoundedCornersTransformation(context, radius, 0,
                        RoundedCornersTransformation.CornerType.ALL))
                .dontAnimate()
                .into(imageView);
    }

    public static void loadRoundedCorners(Context context, String url, final ImageView imageView,int duration, int radius,int width,int height) {
        Glide.with(context)
                .load(url)
                .override(width,height)
                .bitmapTransform(new RoundedCornersTransformation(context, radius, 0,
                        RoundedCornersTransformation.CornerType.ALL))
                .crossFade(duration)
                .into(imageView);
    }

    public static void loadRoundedCorners(Context context, int res, final ImageView imageView, int radius,int width,int height) {
        Glide.with(context)
                .load(res)
                .override(width,height)
                .bitmapTransform(new RoundedCornersTransformation(context, radius, 0,
                        RoundedCornersTransformation.CornerType.ALL))
                .dontAnimate()
                .into(imageView);
    }


    public static void downLoadPicture(final Context context, String url, final LargeImageView largeImageView) {
        try {
            Glide.with(context).load(url).downloadOnly(new ProgressTarget<String, File>(url, null) {
                @Override
                public void onLoadStarted(Drawable placeholder) {
                    super.onLoadStarted(placeholder);
                    largeImageView.setImage(R.mipmap.default_image);
                }

                @Override
                public void onProgress(long bytesRead, long expectedLength) {

                }

                @Override
                public void onResourceReady(File resource, GlideAnimation<? super File> animation) {
                    super.onResourceReady(resource, animation);
//                ringProgressBar.setVisibility(View.GONE);
                    largeImageView.setImage(new FileBitmapDecoderFactory(resource));
                }

                @Override
                public void getSize(SizeReadyCallback cb) {
                    cb.onSizeReady(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
                }
            });
        } catch (Exception e) {
            return;
        }

    }

    public static void loadThumbnail(Context context, Object url, ImageView imageView) {
        try {
            Glide.with(context.getApplicationContext())
                    .load(url)
                    .thumbnail(0.05f)
                    .placeholder(LOADING_PLACE_HOLDER)
                    .error(FAILED_PLACE_HOLDER)
                    .dontAnimate()
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }
}
