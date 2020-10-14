package jarg.company.paint;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.res.Configuration;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import jarg.company.models.ObjectSerializer;
import jarg.company.models.Settings;
import jarg.company.models.User;

public class MainActivity extends AppCompatActivity {
    private final int REQUEST_CODE_SETTINGS = 1;
    private final int REQUEST_CODE_USER_DATA = 2;
    private SharedPreferences sharedPreferences;
    public User user;
    public Settings settings;

    public void setUser() {
        try {
            String userSerialized = this.sharedPreferences.getString("user", "");

            if(userSerialized.isEmpty()) {
                this.user = new User("Dummy User", 99, "Rua de cima");

                this.sharedPreferences.edit().putString("user", ObjectSerializer.serialize(user)).apply();
            } else {
                this.user = (User) ObjectSerializer.deserialize(sharedPreferences.getString("user", ObjectSerializer.serialize(userSerialized)));
            }

            Log.i("userSerialized----------->", "--------------------" + user.getName());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setSettings() {
        try {
            String settingsSerialized = this.sharedPreferences.getString("settings", "");

            if(settingsSerialized.isEmpty()) {
                this.settings = new Settings(Settings.BACKGROUND_DEFAULT, Settings.TEXT_COLOR_DEFAULT);

                this.sharedPreferences.edit().putString("settings", ObjectSerializer.serialize(this.settings)).apply();
            } else {
                this.settings = (Settings) ObjectSerializer.deserialize(sharedPreferences.getString("settings", ObjectSerializer.serialize(settingsSerialized)));
            }

            Log.i("userSerialized----------->", "--------------------" + settings.getBackground());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setUserView() {
        TextView unTextView = (TextView) findViewById(R.id.userNameTextView);

        unTextView.setText(this.user.getName());
    }

    public void setAppColors() {
        ConstraintLayout mConstraintLayout = (ConstraintLayout)findViewById(R.id.mainLayout);

        mConstraintLayout.setBackgroundColor(Color.parseColor(this.settings.getBackground()));

        TextView unTextView = (TextView) findViewById(R.id.userNameTextView);

        unTextView.setTextColor(Color.parseColor(this.settings.getTextColor()));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.about) {
            Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
            //startActivity(intent);
            startActivityForResult(intent, this.REQUEST_CODE_USER_DATA);

            return true;
        }

        if (item.getItemId() == R.id.settings) {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            //startActivity(intent);
            startActivityForResult(intent, this.REQUEST_CODE_SETTINGS);
            return true;
        }

        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("requestCode", Integer.toString(requestCode));
        Log.i("resultCode", Integer.toString(resultCode));
        Log.i("RESULT_OK", Integer.toString(RESULT_OK));
        Log.i("settingsChanged", data.hasExtra("settingsChanged") + "");
        Log.i("settingsChanged", data.getExtras().getInt("settingsChanged") + "");

        if(resultCode == RESULT_OK && requestCode == this.REQUEST_CODE_SETTINGS) {
            if(data.hasExtra("settingsChanged") && data.getExtras().getInt("settingsChanged") == 1) {
                this.setSettings();
                this.setAppColors();
            }
        }
        //REQUEST_CODE_USER_DATA
        if(resultCode == RESULT_OK && requestCode == this.REQUEST_CODE_USER_DATA) {
            if(data.hasExtra("dataChanged") && data.getExtras().getInt("dataChanged") == 1) {
                this.setUser();
                this.setUserView();
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        checkOrientation(newConfig);
    }

    private void checkOrientation(Configuration newConfig){
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.d("OrientationMyApp", "Current Orientation : Landscape");
            // Your magic here for landscape mode
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Log.d("OrientationMyApp", "Current Orientation : Portrait");
            // Your magic here for portrait mode
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.sharedPreferences = this.getSharedPreferences("jarg.company.paint", Context.MODE_PRIVATE);

        this.setUser();
        this.setSettings();

        setContentView(R.layout.activity_main);

        this.setUserView();
        this.setAppColors();
    }
}