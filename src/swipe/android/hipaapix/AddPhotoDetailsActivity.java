package swipe.android.hipaapix;

import org.droidparts.widget.ClearableEditText;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

public class AddPhotoDetailsActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_photo_details_layout);

		getActionBar().setDisplayOptions(
				ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
		ActionBar ab = this.getActionBar();
		ab.setDisplayShowTitleEnabled(true);
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setHomeButtonEnabled(true);

		ab.setTitle("Photo Details");

		Intent intent = getIntent();
		Bitmap bitmap = (Bitmap) intent.getParcelableExtra("BitmapImage");
		ImageView imagePreview = (ImageView) findViewById(R.id.image_preview);
		imagePreview.setImageBitmap(bitmap);

		ClearableEditText notes = (ClearableEditText) findViewById(R.id.notes_field);

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

	public void enableDisableDone() {
		menu.getItem(0).setEnabled(hasChosenCategory);

	}

	Menu menu;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.add_photo_details_menu, menu);
		this.menu = menu;
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.done_item:
			uploadImage();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}
	public void uploadImage(){
		
	}
}