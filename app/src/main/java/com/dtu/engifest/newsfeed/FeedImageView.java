package com.dtu.engifest.newsfeed;

/**
 * Created by sikkavayu2013 on 15-01-2015.
 */

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;

public class FeedImageView extends ImageView {

    public interface ResponseObserver {
        public void onError();

        public void onSuccess();
    }

    private ResponseObserver mObserver;

    public void setResponseObserver(ResponseObserver observer) {
        mObserver = observer;
    }

    /**
     * The URL of the network image to load
     */
    private String mUrl;


    private int mDefaultImageId;


    private int mErrorImageId;


    private ImageLoader mImageLoader;


    private ImageContainer mImageContainer;

    public FeedImageView(Context context) {
        this(context, null);
    }

    public FeedImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FeedImageView(Context context, AttributeSet attrs,
                         int defStyle) {
        super(context, attrs, defStyle);
    }


    public void setImageUrl(String url, ImageLoader imageLoader) {
        mUrl = url;
        mImageLoader = imageLoader;
        loadImageIfNecessary(false);
    }


    public void setDefaultImageResId(int defaultImage) {
        mDefaultImageId = defaultImage;
    }


    public void setErrorImageResId(int errorImage) {
        mErrorImageId = errorImage;
    }


    private void loadImageIfNecessary(final boolean isInLayoutPass) {
        final int width = getWidth();
        int height = getHeight();

        boolean isFullyWrapContent = getLayoutParams() != null
                && getLayoutParams().height == LayoutParams.WRAP_CONTENT
                && getLayoutParams().width == LayoutParams.WRAP_CONTENT;

        if (width == 0 && height == 0 && !isFullyWrapContent) {
            return;
        }


        if (TextUtils.isEmpty(mUrl)) {
            if (mImageContainer != null) {
                mImageContainer.cancelRequest();
                mImageContainer = null;
            }
            setDefaultImageOrNull();
            return;
        }


        if (mImageContainer != null && mImageContainer.getRequestUrl() != null) {
            if (mImageContainer.getRequestUrl().equals(mUrl)) {

                return;
            } else {

                mImageContainer.cancelRequest();
                setDefaultImageOrNull();
            }
        }


        ImageContainer newContainer = mImageLoader.get(mUrl,
                new ImageListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (mErrorImageId != 0) {
                            setImageResource(mErrorImageId);
                        }

                        if (mObserver != null) {
                            mObserver.onError();
                        }
                    }

                    @Override
                    public void onResponse(final ImageContainer response,
                                           boolean isImmediate) {

                        if (isImmediate && isInLayoutPass) {
                            post(new Runnable() {
                                @Override
                                public void run() {
                                    onResponse(response, false);
                                }
                            });
                            return;
                        }

                        int bWidth = 0, bHeight = 0;
                        if (response.getBitmap() != null) {

                            setImageBitmap(response.getBitmap());
                            bWidth = response.getBitmap().getWidth();
                            bHeight = response.getBitmap().getHeight();
                            adjustImageAspect(bWidth, bHeight);

                        } else if (mDefaultImageId != 0) {
                            setImageResource(mDefaultImageId);
                        }

                        if (mObserver != null) {
                            mObserver.onSuccess();

                        }
                    }
                }
        );


        mImageContainer = newContainer;

    }

    private void setDefaultImageOrNull() {
        if (mDefaultImageId != 0) {
            setImageResource(mDefaultImageId);
        } else {
            setImageBitmap(null);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        loadImageIfNecessary(true);
    }

    @Override
    protected void onDetachedFromWindow() {
        if (mImageContainer != null) {
            mImageContainer.cancelRequest();
            setImageBitmap(null);

            mImageContainer = null;
        }
        super.onDetachedFromWindow();
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        invalidate();
    }

    /*
     * Adjusting imageview height
     * */
    private void adjustImageAspect(int bWidth, int bHeight) {
        LayoutParams params = (LayoutParams) getLayoutParams();

        if (bWidth == 0 || bHeight == 0)
            return;

        int swidth = getWidth();
        int new_height = 0;
        new_height = swidth * bHeight / bWidth;
        params.width = swidth;
        params.height = new_height;
        setLayoutParams(params);
    }
}