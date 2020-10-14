package jarg.company.paint;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import jarg.company.models.ObjectSerializer;
import jarg.company.models.User;

public class AboutActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    public User user;
    private TextInputEditText nameTextInputEditText;
    private TextInputEditText ageTextInputEditText;
    private TextInputEditText addressTextInputEditText;
    private int dataChanged = 0;

    public void save() {
        try {
            Log.i("save-------------->", "save");
            this.user.setName(nameTextInputEditText.getText().toString());
            this.user.setAge(Integer.parseInt(ageTextInputEditText.getText().toString()));
            this.user.setAddress(addressTextInputEditText.getText().toString());

            this.sharedPreferences.edit().putString("user", ObjectSerializer.serialize(user)).apply();

            Toast.makeText(this, R.string.toast_data_success, Toast.LENGTH_LONG).show();

            this.dataChanged = 1;

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, R.string.toast_data_error, Toast.LENGTH_LONG).show();
        }

    }

    public void setUser() {
        try {
            String userSerialized = this.sharedPreferences.getString("user", "");

            this.user = (User) ObjectSerializer.deserialize(sharedPreferences.getString("user", ObjectSerializer.serialize(userSerialized)));

            nameTextInputEditText       = (TextInputEditText) findViewById(R.id.nameTextInputEditText);
            ageTextInputEditText        = (TextInputEditText) findViewById(R.id.ageTextInputEditText);
            addressTextInputEditText    = (TextInputEditText) findViewById(R.id.addressTextInputEditText);

            nameTextInputEditText.setText(this.user.getName());
            ageTextInputEditText.setText(this.user.getAge().toString());
            addressTextInputEditText.setText(this.user.getAddress());

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void finish() {
        // Prepare data intent
        Intent data = new Intent();
        data.putExtra("dataChanged", this.dataChanged);
        // Activity finished ook return the data
        setResult(RESULT_OK, data);

        super.finish();
    }

    @Override
    public void onBackPressed() {
        Log.d("RESULT_OK", "onBackPressed Called");
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.sharedPreferences = this.getSharedPreferences("jarg.company.paint", Context.MODE_PRIVATE);

        setContentView(R.layout.activity_about);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Set user
        this.setUser();
        Button saveButton= (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }
}