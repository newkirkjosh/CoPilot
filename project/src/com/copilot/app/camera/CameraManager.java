package com.copilot.app.camera;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;

/**
 * Represents a singleton class with the sole purpose of handling the Android
 * camera.
 * 
 * @author Dejan Ristic
 * 
 */
public class CameraManager {

	private static final String TAG = CameraManager.class.getName();

	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;

	private static String imageAbsolutePath;

	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private static Activity mActivity;
	private static Uri fileUri;

	private static CameraManager mInstance;
	private static Context mContext;
	private Camera mCamera;

	private CameraManager() {

	}

	public static CameraManager getInstance(Context context, Activity activity) {
		if (mInstance == null)
			mInstance = new CameraManager();
		mContext = context;
		mActivity = activity;

		return mInstance;
	}

	public void startCaptureIntent() {

		fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

		// Camera.
		final List<Intent> cameraIntents = new ArrayList<Intent>();
		final Intent captureIntent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		final PackageManager packageManager = mContext.getPackageManager();
		final List<ResolveInfo> listCam = packageManager.queryIntentActivities(
				captureIntent, 0);
		for (ResolveInfo res : listCam) {
			final String packageName = res.activityInfo.packageName;
			final Intent intent = new Intent(captureIntent);
			intent.setComponent(new ComponentName(res.activityInfo.packageName,
					res.activityInfo.name));
			intent.setPackage(packageName);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
			cameraIntents.add(intent);
		}

		// File system.
		final Intent galleryIntent = new Intent();
		galleryIntent.setType("image/*");
		galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

		// Chooser of file system options.
		final Intent chooserIntent = Intent.createChooser(galleryIntent,
				"Select Source");

		// Add the camera options.
		chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
				cameraIntents.toArray(new Parcelable[] {}));

		// start the image capture Intent
		mActivity.startActivityForResult(chooserIntent,
				CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
	}
	
	public Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(
                mActivity.getContentResolver().openInputStream(selectedImage), null, o);

        final int REQUIRED_SIZE = 100;

        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(
        		mActivity.getContentResolver().openInputStream(selectedImage), null, o2);
    }

	public Bitmap retrieveImageFromPath() {
		Bitmap bmp = BitmapFactory.decodeFile(imageAbsolutePath);
		return bmp;
	}

	
	 
	/** Create a file Uri for saving an image or video */
	private static Uri getOutputMediaFileUri(int type) {
		return Uri.fromFile(getOutputMediaFile(type));
	}

	/** Create a File for saving an image or video */
	private static File getOutputMediaFile(int type) {
		// To be safe, you should check that the SDCard is mounted
		// using Environment.getExternalStorageState() before doing this.

		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				"copilot");
		// This location works best if you want the created images to be shared
		// between applications and persist after your app has been uninstalled.

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d("CoPilot", "failed to create directory");
				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
				Locale.ENGLISH).format(new Date());
		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "IMG_" + timeStamp + ".jpg");
			imageAbsolutePath = mediaFile.getAbsolutePath();

			Log.v("imagePATH ", imageAbsolutePath);
		} else if (type == MEDIA_TYPE_VIDEO) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "VID_" + timeStamp + ".mp4");
		} else {
			return null;
		}

		return mediaFile;
	}
}
