package uk.ac.shef.oak.com6510;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

/**
 * Search activity of app which provides search function.
 */
public class SearchActivity extends AppCompatActivity {
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		setTitle("Picture Manager - Search");

		final EditText editTextKeyword = findViewById(R.id.search_key);

		Button searchButton = findViewById(R.id.search_button);
		searchButton.setOnClickListener(v -> {
			Intent intent = new Intent(SearchActivity.this, ResultActivity.class);
			intent.putExtra("key", editTextKeyword.getText().toString());
			startActivity(intent);
		});
	}
}
