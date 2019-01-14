package uk.ac.shef.oak.com6510;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import uk.ac.shef.oak.com6510.viewmodel.PictureViewModel;

/**
 * Result activity of app which shows search result.
 */
public class ResultActivity extends AppCompatActivity {
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		setTitle("Picture Manager - Result");

		final int numberOfColumns = 3;

		RecyclerView recyclerView = findViewById(R.id.grid_recycler_view_result);
		recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
		recyclerView.setHasFixedSize(true);
		recyclerView.setNestedScrollingEnabled(false);

		PictureAdapter adapter = new PictureAdapter();
		recyclerView.setAdapter(adapter);

		PictureViewModel viewModel = ViewModelProviders.of(this).get(PictureViewModel.class);

		String key = getIntent().getStringExtra("key");

		//update recyclerView
		viewModel.search(key).observe(this, adapter::setPictures);
	}
}
