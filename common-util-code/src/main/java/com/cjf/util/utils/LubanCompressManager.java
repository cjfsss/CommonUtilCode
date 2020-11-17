package com.cjf.util.utils;

import android.app.Activity;
import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import com.cjf.thread.executor.ThreadTaskExecutor;
import com.cjf.thread.extension.ThreadExtKt;
import com.cjf.util.R;
import com.cjf.util.listener.OnCompressToLubanResultListener;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.compress.OnCompressListener;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.PictureSelectionConfig;
import com.luck.picture.lib.engine.ImageEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.thread.PictureThreadUtils;
import com.luck.picture.lib.tools.AndroidQTransformUtils;
import com.luck.picture.lib.tools.BitmapUtils;
import com.luck.picture.lib.tools.MediaUtils;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.luck.picture.lib.tools.SdkVersionUtils;
import com.luck.picture.lib.tools.ValueOf;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/**
 * <p>Title: LubanCompressManager </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/10/16 1:11
 */
public class LubanCompressManager {
    private final PictureSelectionConfig selectionConfig;
    private OnCompressToLubanResultListener mOnCompressToLubanResultListener;

    private LubanCompressManager(PictureSelectionConfig selectionConfig, OnCompressToLubanResultListener listener) {
        this.selectionConfig = selectionConfig;
        mOnCompressToLubanResultListener = listener;
    }

    @NonNull
    public static Builder builder() {
        return new Builder();
    }

    public void compressImagePathIo(Activity activity, final List<String> pathList) {
        ThreadTaskExecutor.getInstance().postIo(new Runnable() {
            @Override
            public void run() {
                compressImagePath(activity, pathList);
            }
        });
    }

    public void compressImagePath(Activity activity, List<String> pathList) {
        List<LocalMedia> result = new ArrayList<>();
        for (String targetPath : pathList) {
            LocalMedia media = new LocalMedia();
            String mimeType = PictureMimeType.MIME_TYPE_IMAGE;
            int[] newSize = new int[2];
            if (PictureMimeType.isContent(targetPath)) {
                // content: Processing rules
                String path = PictureFileUtils.getPath(activity, Uri.parse(targetPath));
                if (!TextUtils.isEmpty(path)) {
                    File cameraFile = new File(path);
                    media.setSize(cameraFile.length());
                }
                if (PictureMimeType.isHasImage(mimeType)) {
                    newSize = MediaUtils.getImageSizeForUrlToAndroidQ(activity, targetPath);
                }
                int lastIndexOf = targetPath.lastIndexOf("/") + 1;
                media.setId(lastIndexOf > 0 ? ValueOf.toLong(targetPath.substring(lastIndexOf)) : -1);
                media.setRealPath(path);
                // Custom photo has been in the application sandbox into the file
//                String mediaPath = intent != null ? intent.getStringExtra(PictureConfig.EXTRA_MEDIA_PATH) : null;
//                media.setAndroidQToPath(mediaPath);
            } else {
                if (PictureMimeType.isHasImage(mimeType)) {
                    int degree = PictureFileUtils.readPictureDegree(activity, targetPath);
                    BitmapUtils.rotateImage(degree, targetPath);
                    newSize = MediaUtils.getImageSizeForUrl(targetPath);
                }
                // Taking a photo generates a temporary id
                media.setId(System.currentTimeMillis());
            }
            media.setWidth(newSize[0]);
            media.setHeight(newSize[1]);
            media.setCut(false);
            media.setPath(targetPath);
            media.setCutPath(targetPath);
            media.setChooseModel(PictureConfig.TYPE_IMAGE);
            long bucketId = MediaUtils.getCameraFirstBucketId(activity);
            media.setBucketId(bucketId);
            result.add(media);
        }
        compressImage(activity, result);
    }

    /**
     * compressImage
     */
    protected void compressImage(Activity activity, final List<LocalMedia> result) {
        if (PictureSelectionConfig.cacheResourcesEngine != null) {
            // 在Android 10上通过图片加载引擎的缓存来获得沙盒内的图片
            int size = result.size();
            for (int i = 0; i < size; i++) {
                LocalMedia media = result.get(i);
                if (media == null) {
                    continue;
                }
                if (!PictureMimeType.isHasHttp(media.getPath())) {
                    String cachePath = PictureSelectionConfig.cacheResourcesEngine.onCachePath(activity, media.getPath());
                    media.setAndroidQToPath(cachePath);
                }
            }
        }
        ThreadTaskExecutor.getInstance().postOnMain(new Runnable() {
            @Override
            public void run() {
                compressToLuban(activity, result);
            }
        });
    }

    /**
     * compress
     *
     * @param result
     */
    private void compressToLuban(Activity activity, List<LocalMedia> result) {
        Luban.with(activity)
                .loadMediaData(result)
                .ignoreBy(selectionConfig.minimumCompressSize)
                .isCamera(selectionConfig.camera)
                .setCompressQuality(selectionConfig.compressQuality)
                .setTargetDir(selectionConfig.compressSavePath)
                .setFocusAlpha(selectionConfig.focusAlpha)
                .setNewCompressFileName(selectionConfig.renameCompressFileName)
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(List<LocalMedia> list) {
                        onResult(activity, list);
                    }

                    @Override
                    public void onError(Throwable e) {
                        onResult(activity, result);
                    }
                }).launch();
    }

    /**
     * handleCompressCallBack
     *
     * @param images
     * @param files
     */
    private void handleCompressCallBack(Activity activity, List<LocalMedia> images, List<File> files) {
        if (images == null || files == null) {
            if (mOnCompressToLubanResultListener != null) {
                mOnCompressToLubanResultListener.onResult(images);
            }
            return;
        }
        boolean isAndroidQ = SdkVersionUtils.checkedAndroid_Q();
        int size = images.size();
        if (files.size() == size) {
            for (int i = 0, j = size; i < j; i++) {
                File file = files.get(i);
                if (file == null) {
                    continue;
                }
                String path = file.getAbsolutePath();
                LocalMedia image = images.get(i);
                boolean http = PictureMimeType.isHasHttp(path);
                boolean flag = !TextUtils.isEmpty(path) && http;
                boolean isHasVideo = PictureMimeType.isHasVideo(image.getMimeType());
                image.setCompressed(!isHasVideo && !flag);
                image.setCompressPath(isHasVideo || flag ? null : path);
                if (isAndroidQ) {
                    image.setAndroidQToPath(image.getCompressPath());
                }
            }
        }
        onResult(activity, images);
    }

    /**
     * return image result
     *
     * @param activity
     * @param images
     */
    protected void onResult(Activity activity, List<LocalMedia> images) {
        boolean isAndroidQ = SdkVersionUtils.checkedAndroid_Q();
        if (isAndroidQ && selectionConfig.isAndroidQTransform) {
            onResultToAndroidAsy(activity, images);
        } else {
            if (selectionConfig.isCheckOriginalImage) {
                int size = images.size();
                for (int i = 0; i < size; i++) {
                    LocalMedia media = images.get(i);
                    media.setOriginal(true);
                    media.setOriginalPath(media.getPath());
                }
            }
            if (mOnCompressToLubanResultListener != null) {
                mOnCompressToLubanResultListener.onResult(images);
            }
        }
    }

    /**
     * Android Q
     *
     * @param activity
     * @param images
     */
    private void onResultToAndroidAsy(Activity activity, List<LocalMedia> images) {
        PictureThreadUtils.executeByIo(new PictureThreadUtils.SimpleTask<List<LocalMedia>>() {
            @Override
            public List<LocalMedia> doInBackground() {
                int size = images.size();
                for (int i = 0; i < size; i++) {
                    LocalMedia media = images.get(i);
                    if (media == null || TextUtils.isEmpty(media.getPath())) {
                        continue;
                    }
                    boolean isCopyAndroidQToPath = !media.isCut()
                            && !media.isCompressed()
                            && TextUtils.isEmpty(media.getAndroidQToPath());
                    if (isCopyAndroidQToPath && PictureMimeType.isContent(media.getPath())) {
                        if (!PictureMimeType.isHasHttp(media.getPath())) {
                            String AndroidQToPath = AndroidQTransformUtils.copyPathToAndroidQ(activity,
                                    media.getPath(), media.getWidth(), media.getHeight(), media.getMimeType(), selectionConfig.cameraFileName);
                            media.setAndroidQToPath(AndroidQToPath);
                        }
                    } else if (media.isCut() && media.isCompressed()) {
                        media.setAndroidQToPath(media.getCompressPath());
                    }
                    if (selectionConfig.isCheckOriginalImage) {
                        media.setOriginal(true);
                        media.setOriginalPath(media.getAndroidQToPath());
                    }
                }
                return images;
            }

            @Override
            public void onSuccess(List<LocalMedia> images) {
                if (mOnCompressToLubanResultListener != null) {
                    mOnCompressToLubanResultListener.onResult(images);
                }
            }
        });
    }

    public static class Builder {

        private final PictureSelectionConfig selectionConfig;

        public Builder() {
            selectionConfig = PictureSelectionConfig.getCleanInstance();
        }

        /**
         * @param themeStyleId PictureSelector Theme style
         * @return PictureSelectionModel
         * Use {@link R.style#picture_default_style#picture_Sina_style#picture_white_style#picture_QQ_style#picture_WeChat_style}
         */
        public Builder theme(@StyleRes int themeStyleId) {
            selectionConfig.themeStyleId = themeStyleId;
            return this;
        }

        /**
         * @param engine Image Load the engine
         * @return
         */
        public Builder imageEngine(ImageEngine engine) {
            if (PictureSelectionConfig.imageEngine != engine) {
                PictureSelectionConfig.imageEngine = engine;
            }
            return this;
        }

        /**
         * @param isCompress Whether to open compress
         * @return
         */
        public Builder isCompress(boolean isCompress) {
            selectionConfig.isCompress = isCompress;
            return this;
        }

        /**
         * @param cutQuality crop compress quality default 90
         * @return
         */
        public Builder cutOutQuality(int cutQuality) {
            selectionConfig.cropCompressQuality = cutQuality;
            return this;
        }

        /**
         * @param Less than how many KB images are not compressed
         * @return
         */
        public Builder minimumCompressSize(int size) {
            selectionConfig.minimumCompressSize = size;
            return this;
        }

        public LubanCompressManager build(OnCompressToLubanResultListener listener) {
            return new LubanCompressManager(selectionConfig, listener);
        }
    }
}
