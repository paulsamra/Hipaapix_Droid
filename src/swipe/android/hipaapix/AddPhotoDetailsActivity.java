package swipe.android.hipaapix;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.droidparts.widget.ClearableEditText;

import swipe.android.hipaapix.json.TrueVaultResponse;
import swipe.android.hipaapix.json.UpdateDocumentResponse;
import swipe.android.hipaapix.json.UploadImageResponse;
import swipe.android.hipaapix.json.UploadPatientResponse;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.edbert.library.network.AsyncTaskCompleteListener;
import com.edbert.library.network.PostDataWebTask;
import com.edbert.library.network.PutDataWebTask;
import com.edbert.library.utils.MapUtils;

public class AddPhotoDetailsActivity extends HipaaActivity implements
		AsyncTaskCompleteListener {
	byte[] photo;
	Bitmap bitmap;
	ClearableEditText notes;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_photo_details_layout);

		
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setDisplayShowTitleEnabled(true);

		
		LayoutInflater mInflater = LayoutInflater.from(this);
		View mCustomView = mInflater.inflate(R.layout.photo_details_action_bar_layout,
				null);
		
		ActionBar.LayoutParams lp = new ActionBar.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);

		getActionBar().setCustomView(mCustomView, lp);
		getActionBar().setDisplayShowCustomEnabled(true);
		
///
		getActionBar().setDisplayUseLogoEnabled(false);

		getActionBar().setTitle(Html.fromHtml("<font color='#DA2922'>Cancel</font>"));


	/*	Bundle extras = getIntent().getExtras();
		photo = extras.getByteArray("BitmapImage");*/
		photo = GlobalTransfer.byte_array;
		bitmap = BitmapFactory.decodeByteArray(photo, 0, photo.length);
		// Bitmap bitmap = (Bitmap) intent.getParcelableExtra("BitmapImage");
		ImageView imagePreview = (ImageView) findViewById(R.id.image_preview);
		imagePreview.setImageBitmap(bitmap);

		notes = (ClearableEditText) findViewById(R.id.notes_field);

		categoryText = (TextView) findViewById(R.id.choose_category);
		TableRow chooseCategoryRow = (TableRow) findViewById(R.id.category_table_row);
		setUpCategory(chooseCategoryRow, R.array.category);
		
	}

	TextView categoryText;
	boolean hasChosenCategory = false;

	protected void setUpCategory(final View rowOfCategory, int array_of_string) {

		final String[] items = this.getResources().getStringArray(
				array_of_string);
		rowOfCategory.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				AlertDialog.Builder builder = new AlertDialog.Builder(
						AddPhotoDetailsActivity.this);

				builder.setItems(items, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						hasChosenCategory = true;
						enableDisableDone();
						categoryText.setText(items[item]);
						dialog.cancel();
					}
				});
				AlertDialog alert = builder.create();
				alert.show();
			}

		});
	}

	public void enableDisableDone( ) {

		menu.getItem(0).setEnabled(hasChosenCategory);
		
	}

	Menu menu;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.add_photo_details_menu, menu);
		this.menu = menu;

		enableDisableDone();
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.done_item:
		
			uploadImage(false);
			return true;

		default:
			this.onBackPressed();
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onBackPressed() {
		// code here to show dialog
		// super.onBackPressed(); // optional depending on your needs
		Intent intent = new Intent(this, HipaaPixCamera.class);
		this.startActivity(intent);
		this.finish();
	}

	public void uploadImage(boolean thumbnail) {
		// send multipart form request
		String url = APIManager.uploadImageURL(this);
		HashMap<String, String> params = APIManager.defaultSessionHeaders();

		/*
		 * String tempPath = Environment.getExternalStorageDirectory()
		 * .toString();
		 */
		try {
			File outputDir = this.getCacheDir();
			File tempFile = File.createTempFile("temp", "jpeg", outputDir);
			OutputStream fOut = null;
			// File tempFile = new File(tempPath, "temp.jpg");

			fOut = new FileOutputStream(tempFile);
			int compression = 70;
			if (thumbnail) {
				compression = 30;
			}
			bitmap.compress(Bitmap.CompressFormat.JPEG, compression, fOut);
			fOut.close();
			fOut.flush();

			new PostDataWebTask<UploadImageResponse>(this,
					UploadImageResponse.class, tempFile, true).execute(url,
					MapUtils.mapToString(params), null);
		}

		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	Uri selectedImageUri;
	String imgPath;

	public String getImagePath() {
		return imgPath;
	}

	String blobID = null, thumbnailID = null;

	void postNonThumbnail(UploadImageResponse result) {
		// String url = APIManager.createUserDocumentURL(this);
		blobID = result.getBlob_id();

		uploadImage(true);
	}

	void postThumbnail(UploadImageResponse result) {
		thumbnailID = result.getBlob_id();
		LocalPatientImage image = new LocalPatientImage(categoryText.getText()
				.toString(), blobID, thumbnailID, /*
												 * SessionManager.getInstance(
												 * this).getPatientId()
												 */"", notes.getText()
				.toString());
		String document = APIManager.createPatientImageDocument(this, image);

		Map<String, String> headers = APIManager.defaultSessionHeaders();
		Map<String, String> form = new HashMap<String, String>();
		form.put("document", document);
		form.put("schema_id", SessionManager.getInstance(this).getSchemaId());

		String url = APIManager.createUserDocumentURL(this);

		new PostDataWebTask<UploadPatientResponse>(
				(AsyncTaskCompleteListener) this, this,
				UploadPatientResponse.class, true, true).execute(url,
				MapUtils.mapToString(headers), MapUtils.mapToString(form));
	}

	public void postPatientResponse(UploadPatientResponse result) {
		String documentID = result.getDocument_id();
		String url = APIManager.updateUserDocumentURL(this);
		Map<String, String> headers = APIManager.defaultSessionHeaders();
		Map<String, String> form = new HashMap<String, String>();
		String document = APIManager.updatePatientDocumentString(this,
				documentID);
		SessionManager.getInstance(this).setNewDoucment(documentID);
		form.put("document", document);
		new PutDataWebTask<UpdateDocumentResponse>(this, this,
				UpdateDocumentResponse.class,true, true).execute(url,
				MapUtils.mapToString(headers), MapUtils.mapToString(form));
	}

	@Override
	public void onTaskComplete(Object result) {
		if (result == null) {
			displayNetworkError();
			blobID = null;
			thumbnailID = null;
			return;
		}
		TrueVaultResponse tvResult = (TrueVaultResponse) result;

		if (!tvResult.isValid()) {
			super.displayError(tvResult);
			blobID = null;
			thumbnailID = null;
		} else if (result instanceof UploadImageResponse && blobID == null) {
			postNonThumbnail((UploadImageResponse) result);
		} else if (result instanceof UploadImageResponse && blobID != null) {
			postThumbnail((UploadImageResponse) result);
		} else if (result instanceof UploadPatientResponse) {
			postPatientResponse((UploadPatientResponse) result);
		} else {

			String loginTitle = "Success";

			String try_again = "OK";
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(try_again)
					.setTitle(loginTitle)
					.setCancelable(false)
					.setPositiveButton(try_again,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.dismiss();
									  SessionManager.getInstance(AddPhotoDetailsActivity.this).setIsDataOutdated(true);
									AddPhotoDetailsActivity.this.finish();
								}
							});

			AlertDialog alert = builder.create();
			alert.show();
		}

	}
}