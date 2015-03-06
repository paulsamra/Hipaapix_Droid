package swipe.android.hipaapix;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HipaaPixCamera extends Activity implements SurfaceHolder.Callback,
		Camera.PictureCallback {

	Camera camera;
	SurfaceView surfaceView;
	SurfaceHolder surfaceHolder;
	boolean previewing = false;
	LayoutInflater controlInflater = null;
	RelativeLayout flashLayout;
	TextView flash;

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

		TextView cancelText = (TextView) findViewById(R.id.cancel_text);
		cancelText.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				HipaaPixCamera.this.finish();
			}
		});
		flash = (TextView) findViewById(R.id.flash_toggle);
		flashLayout = (RelativeLayout) findViewById(R.id.flash_layout);
		flashLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setFlash();
			}
		});
	}

	public void setFlash() {
		if (flashIsOn) {
			flash.setText("Off");
		} else {
			flash.setText("On");
		}
		flashIsOn = !flashIsOn;
		reloadCamera();
	}

	boolean flashIsOn = false;

	public void takePicture() {
		camera.takePicture(myShutterCallback, myPictureCallback_RAW, this);
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
			/*
			 * Bitmap bitmapPicture = BitmapFactory.decodeByteArray(arg0, 0,
			 * arg0.length);
			 */

			Intent intent = new Intent(HipaaPixCamera.this,
					AddPhotoDetailsActivity.class);
			/*
			 * Bundle bundle = new Bundle(); bundle.putByteArray("BitmapImage",
			 * arg0); intent.putExtras(bundle);
			 */
			GlobalTransfer.byte_array = arg0;
			if (dialog != null)
				dialog.dismiss();
			// intent.putExtra("BitmapImage", bitmapPicture);
			HipaaPixCamera.this.startActivity(intent);
			HipaaPixCamera.this.finish();
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
		if (currentCameraId == Camera.CameraInfo.CAMERA_FACING_BACK)
			currentCameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
		else
			currentCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
		reloadCamera();
	}

	public void onPause() {
		super.onPause();
		if (camera != null) {
			Parameters p = camera.getParameters();
			camera.setParameters(p);
			p.setFlashMode(Parameters.FLASH_MODE_OFF);

			camera.setParameters(p);
		}
	}

	public void onResume() {
		super.onResume();
		if (camera != null) {
			Parameters p = camera.getParameters();
			camera.setParameters(p);
			if (this.flashIsOn) {
				p.setFlashMode(Parameters.FLASH_MODE_TORCH);
			} else {
				p.setFlashMode(Parameters.FLASH_MODE_OFF);
			}
			camera.setParameters(p);
		}
	}

	public void reloadCamera() {
		if (camera != null) {
			camera.stopPreview();
			camera.release();
			camera = null;
		}

		try {
			camera = Camera.open(currentCameraId);

			camera.setDisplayOrientation(90);

			camera.setPreviewDisplay(surfaceHolder);

			Parameters p = camera.getParameters();
			if (this.flashIsOn) {
				p.setFlashMode(Parameters.FLASH_MODE_TORCH);
			} else {
				p.setFlashMode(Parameters.FLASH_MODE_OFF);
			}
			p.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
			camera.setParameters(p);

			camera.startPreview();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	ProgressDialog dialog;

	@Override
	public void onPictureTaken(byte[] data, Camera camera) {

		new LongOperation(data).execute();

	}
	

    private class LongOperation extends AsyncTask<Void, Void, byte[]> {
      byte[] data;
      LongOperation(byte[] data){
    	  this.data = data;
      }

        @Override
        protected void onPostExecute(byte[] byteArray) {
        	myPictureCallback_JPG.onPictureTaken(byteArray, camera);
        	dialog.cancel();
        }

        @Override
        protected void onPreExecute() {
        	dialog = new ProgressDialog(HipaaPixCamera.this);
    		dialog.setMessage("Rendering image...");
    		dialog.show();
        }

    
		@Override
		protected byte[] doInBackground(Void... params) {
			Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

			int rotation = 90;
			Log.d("RENDER", " DISALOG");		
			if (rotation != 0) {
				Bitmap oldBitmap = bitmap;

				Matrix matrix = new Matrix();
				matrix.postRotate(rotation);
				bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
						bitmap.getHeight(), matrix, false);

				oldBitmap.recycle();
			}

			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
			byte[] byteArray = stream.toByteArray();
			
			return byteArray;
		}
    }

}