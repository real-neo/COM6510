package uk.ac.shef.oak.com6510;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.media.ExifInterface;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import uk.ac.shef.oak.com6510.database.Picture;
import uk.ac.shef.oak.com6510.viewmodel.PictureViewModel;

public class InfoActivity extends AppCompatActivity {
	private Picture element = null;
	private ExifInterface exifInterface;
	private InfoAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		setTitle("Picture Manager - Edit");

		RecyclerView recyclerView = findViewById(R.id.recycler_view_info);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recyclerView.setHasFixedSize(true);
		recyclerView.setNestedScrollingEnabled(false);

		element = (Picture) getIntent().getSerializableExtra("pic");
		if (element != null) {
			mAdapter = new InfoAdapter(element);
			recyclerView.setAdapter(mAdapter);

			final EditText editTextTitle = findViewById(R.id.edit_text_title);
			final EditText editTextDescription = findViewById(R.id.edit_text_description);

			final PictureViewModel viewModel = ViewModelProviders.of(this).get(PictureViewModel.class);

			try {
				exifInterface = new ExifInterface(element.getPicturePath());
			} catch (IOException e) {
				e.printStackTrace();
				Toast.makeText(getApplicationContext(),
						"Something wrong:\n" + e.toString(),
						Toast.LENGTH_LONG).show();
			}

			editTextTitle.setText(element.getTitle());
			editTextDescription.setText(element.getDescription());

			Button saveButton = findViewById(R.id.save_button);
			saveButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					element.setTitle(editTextTitle.getText().toString());
					element.setDescription(editTextDescription.getText().toString());
					Log.d("EDIT_TEXT_TITLE", editTextTitle.getText().toString());
					Log.d("EDIT_TEXT_DESCRIPTION", editTextDescription.getText().toString());

					viewModel.update(element);

					Toast.makeText(InfoActivity.this, "Save successfully!\nTitle: " + element.getTitle() + "\nDescription: " + element.getDescription(), Toast.LENGTH_LONG).show();
				}
			});
		}
	}

	String getExif() {
		String exif = "";
		exif += "\nHeight: " +
				exifInterface.getAttribute(ExifInterface.TAG_IMAGE_LENGTH);
		exif += "\nWidth: " +
				exifInterface.getAttribute(ExifInterface.TAG_IMAGE_WIDTH);
		exif += "\nDate: " +
				exifInterface.getAttribute(ExifInterface.TAG_DATETIME);
		exif += "\nManufacturer: " +
				exifInterface.getAttribute(ExifInterface.TAG_MAKE);
		exif += "\nCamera model: " +
				exifInterface.getAttribute(ExifInterface.TAG_MODEL);
		exif += "\nOrientation: " +
				exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION);
		exif += "\nWhite balance: " +
				exifInterface.getAttribute(ExifInterface.TAG_WHITE_BALANCE);
		exif += "\nFocal length: " +
				exifInterface.getAttribute(ExifInterface.TAG_FOCAL_LENGTH);
		exif += "\nFlash: " +
				exifInterface.getAttribute(ExifInterface.TAG_FLASH);
		exif += "\nTAG_GPS_DATESTAMP: " +
				exifInterface.getAttribute(ExifInterface.TAG_GPS_DATESTAMP);
		exif += "\nTAG_GPS_TIMESTAMP: " +
				exifInterface.getAttribute(ExifInterface.TAG_GPS_TIMESTAMP);
		exif += "\nLatitude: " +
				exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
		exif += "\nTAG_GPS_LATITUDE_REF: " +
				exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
		exif += "\nLongitude: " +
				exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
		exif += "\nTAG_GPS_LONGITUDE_REF: " +
				exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);
		exif += "\nTAG_GPS_PROCESSING_METHOD: " +
				exifInterface.getAttribute(ExifInterface.TAG_GPS_PROCESSING_METHOD);

		return exif;
	}
}
