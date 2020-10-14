package jarg.company.paint;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import jarg.company.models.ObjectSerializer;
import jarg.company.models.Settings;
import jarg.company.models.User;

public class SettingsActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    public Settings settings;
    private Button backgroudSelectedColorButton;
    private Button textSelectedColorButton;
    private int settingsChanged = 0;

    public void save() {
        try {
            Log.i("save-------------->", "save");

            ColorDrawable drawableBackgroudSelectedColorButton = (ColorDrawable) backgroudSelectedColorButton.getBackground();

            int intBackgroundColor = drawableBackgroudSelectedColorButton.getColor();

            String hexBackgroundColor = String.format("#%06X", (0xFFFFFF & intBackgroundColor));

            this.settings.setBackground(hexBackgroundColor);

            ColorDrawable drawableTextSelectedColorButton = (ColorDrawable) textSelectedColorButton.getBackground();

            int intTextSelectedColorButton = drawableTextSelectedColorButton.getColor();

            String hexTextSelectedColorButton = String.format("#%06X", (0xFFFFFF & intTextSelectedColorButton));

            this.settings.setTextColor(hexTextSelectedColorButton);

            this.sharedPreferences.edit().putString("settings", ObjectSerializer.serialize(settings)).apply();

            this.settingsChanged = 1;

            Toast.makeText(this, R.string.toast_data_success, Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }
    public void setSettings() {
        try {
            String settingsSerialized = this.sharedPreferences.getString("settings", "");

            this.settings = (Settings) ObjectSerializer.deserialize(settingsSerialized);

            backgroudSelectedColorButton = (Button) findViewById(R.id.backgroudSelectedColorButton);
            textSelectedColorButton = (Button) findViewById(R.id.textSelectedColorButton);

            backgroudSelectedColorButton.setBackgroundColor(Color.parseColor(this.settings.getBackground()));
            textSelectedColorButton.setBackgroundColor(Color.parseColor(this.settings.getTextColor()));

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void setBackgroudSelectedColorButton(String hexCcolor) {
        this.backgroudSelectedColorButton.setBackgroundColor(Color.parseColor(hexCcolor));
    }

    public void setTextSelectedColorButton(String hexCcolor) {
        this.textSelectedColorButton.setBackgroundColor(Color.parseColor(hexCcolor));
    }

    public void finish() {
        // Prepare data intent
        Intent data = new Intent();
        data.putExtra("settingsChanged", this.settingsChanged);
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
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.sharedPreferences = this.getSharedPreferences("jarg.company.paint", Context.MODE_PRIVATE);
        // Set settings
        this.setSettings();
        Button saveButton= (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

        Button blackBackgroudColorButton= (Button) findViewById(R.id.blackBackgroudColorButton);
        blackBackgroudColorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBackgroudSelectedColorButton("#000000");
            }
        });
        Button whiteBackgroudColorButton= (Button) findViewById(R.id.whiteBackgroudColorButton);
        whiteBackgroudColorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBackgroudSelectedColorButton("#ffffff");
            }
        });

        Button orangeTextColorButton= (Button) findViewById(R.id.orangeTextColorButton);
        orangeTextColorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextSelectedColorButton("#ff9900");
            }
        });
        Button blueTextColorButton= (Button) findViewById(R.id.blueTextColorButton);
        blueTextColorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextSelectedColorButton("#0099ff");
            }
        });


    }
}