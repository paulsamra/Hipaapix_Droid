package swipe.android.hipaapix;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

public class HipaaPixCamera extends Activity implements SurfaceHolder.Callback, Camera.PictureCallback  {

	Camera camera;
	SurfaceView surfaceView;
	SurfaceHolder surfaceHolder;
	boolean previewing = false;
	LayoutInflater controlInflater = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.camera_layout);

		getActionBar().setDisplayOptions(
				ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
		ActionBar ab = this.getActionBar();
		ab.setDisplayShowTitleEnabled(true);
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setHomeButtonEnabled(true);
		ab.hide();

		getWindow().setFormat(PixelFormat.UNKNOWN);
		surfaceView = (SurfaceView) findViewById(R.id.camerapreview);
		surfaceHolder = surfaceView.getHolder();
		surfaceHolder.addCallback(this);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		controlInflater = LayoutInflater.from(getBaseContext());
		View viewControl = controlInflater.inflate(R.layout.control, null);
		LayoutParams layoutParamsControl = new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		this.addContentView(viewControl, layoutParamsControl);

		ImageView swtichFrontBack = (ImageView) findViewById(R.id.switch_front_back_camera);
		swtichFrontBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				switchFrontBackCamera();
			}
		});

		ImageView takePictureButton = (ImageView) findViewById(R.id.take_picture_btn);
		takePictureButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				takePicture();
			}
		});

	}

	public void takePicture() {
		camera.takePicture(myShutterCallback, myPictureCallback_RAW,
				this);
	}

	ShutterCallback myShutterCallback = new ShutterCallback() {

		@Override
		public void onShutter() {

		}
	};

	PictureCallback myPictureCallback_RAW = new PictureCallback() {

		@Override
		public void onPictureTaken(byte[] arg0, Camera arg1) {

		}
	};

	PictureCallback myPictureCallback_JPG = new PictureCallback() {

		@Override
		public void onPictureTaken(byte[] arg0, Camera arg1) {
			// TODO Auto-generated method stub
			/*Bitmap bitmapPicture = BitmapFactory.decodeByteArray(arg0, 0,
					arg0.length);*/
			//Log.d("hi","hi");
			Intent intent = new Intent(HipaaPixCamera.this, AddPhotoDetailsActivity.class);
			Bundle bundle = new Bundle();
            bundle.putByteArray("BitmapImage", arg0);
            intent.putExtras(bundle);
			//intent.putExtra("BitmapImage", bitmapPicture);
			HipaaPixCamera.this.startActivity(intent);
		}
	};

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

		camera.setDisplayOrientation(90);
		if (camera != null) {
			try {
				camera.setPreviewDisplay(surfaceHolder);
				camera.startPreview();
				previewing = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		camera = Camera.open();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		camera.stopPreview();
		camera.release();
		camera = null;
		previewing = false;
	}

	int currentCameraId = 0;

	public void switchFrontBackCamera() {
		if (camera != null) {
			camera.stopPreview();
			camera.release();
			camera = null;
		}

		// swap the id of the camera to be used
		if (currentCameraId == Camera.CameraInfo.CAMERA_FACING_BACK)
			currentCameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
		else
			currentCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;

		try {
			camera = Camera.open(currentCameraId);

			camera.setDisplayOrientation(90);

			camera.setPreviewDisplay(surfaceHolder);

			camera.startPreview();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onPictureTaken(byte[] data, Camera camera) {
		
		   Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

	        int rotation = 90;

	        if (rotation != 0) {
	            Bitmap oldBitmap = bitmap;

	            Matrix matrix = new Matrix();
	            matrix.postRotate(rotation);

	            bitmap = Bitmap.createBitmap(
	                bitmap,
	                0,
	                0,
	                bitmap.getWidth(),
	                bitmap.getHeight(),
	                matrix,
	                false
	            );

	            oldBitmap.recycle();
	        }
	       
	        ByteArrayOutputStream stream = new ByteArrayOutputStream();
	        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
	        byte[] byteArray = stream.toByteArray();
		myPictureCallback_JPG.onPictureTaken(byteArray, camera);
		
	}
}