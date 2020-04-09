package ca.davidpellegrini.scorekeeper_w20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Button;
import android.os.Bundle;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity
implements OnClickListener{

    private TextView teamA_score, teamB_score;
    private Button teamA_decrease, teamA_increase,teamB_decrease, teamB_increase;
    private RadioGroup scoreChange;
    private RadioButton score_1, score_2, score_3;
    private SharedPreferences savedValues, prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        teamA_score = findViewById(R.id.teamA_score);
        teamB_score = findViewById(R.id.teamB_score);
        teamA_decrease = findViewById(R.id.teamA_decrease);
        teamA_increase = findViewById(R.id.teamA_increase);
        teamB_decrease = findViewById(R.id.teamB_decrease);
        teamB_increase = findViewById(R.id.teamB_increase);
        scoreChange = findViewById(R.id.scoreChange);
        score_1 = findViewById(R.id.score_1);
        score_2 = findViewById(R.id.score_2);
        score_3 = findViewById(R.id.score_3);

        teamA_decrease.setOnClickListener(this);
        teamA_increase.setOnClickListener(this);
        teamB_decrease.setOnClickListener(this);
        teamB_increase.setOnClickListener(this);

        savedValues = getSharedPreferences("SharedValuesScoreKeeper", MODE_PRIVATE);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
    }

    public void onPause(){
        Editor editor = savedValues.edit();

        if(prefs.getBoolean("pref_remember_percent", false)){
            editor.putInt("TeamA_Score", Integer.parseInt(teamA_score.getText().toString()));
            editor.putInt("TeamB_Score", Integer.parseInt(teamB_score.getText().toString()));
        }
        else{
            editor.clear();
        }
        editor.putBoolean("pref_remember_percent", prefs.getBoolean("pref_remember_percent", true));
        editor.apply();

        super.onPause();
    }

    public void onResume(){
        super.onResume();
        prefs.edit().putBoolean("pref_remember_percent", true).apply();

        if(prefs.getBoolean("pref_remember_percent", false)){
            teamA_score.setText(Integer.toString(savedValues.getInt("TeamA_Score", 0)));
            teamB_score.setText(Integer.toString(savedValues.getInt("TeamB_Score", 0)));
        }
        else{
            teamA_score.setText("0");
            teamB_score.setText("0");
        }

    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(
                R.menu.activity_tip_calculator, menu
        );
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_settings:
                startActivity(new Intent(
                        getApplicationContext(),
                        SettingsActivity.class
                ));
                return true;
            case R.id.menu_about:
                Toast.makeText(this, "Made by David Pellegrini for JAV1001 labs",
                        Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        int score = 0;
        switch (scoreChange.getCheckedRadioButtonId()){
            case R.id.score_1:
                score = Integer.parseInt(score_1.getText().toString());
                break;
            case R.id.score_2:
                score = Integer.parseInt(score_2.getText().toString());
                break;
            case R.id.score_3:
                score = Integer.parseInt(score_3.getText().toString());
                break;
        }
        switch (v.getId()){
            case R.id.teamA_decrease:
                teamA_score.setText(Integer.toString(Integer.parseInt(teamA_score.getText().toString())-score));
                break;
            case R.id.teamA_increase:
                teamA_score.setText(Integer.toString(Integer.parseInt(teamA_score.getText().toString())+score));
                break;
            case R.id.teamB_decrease:
                teamB_score.setText(Integer.toString(Integer.parseInt(teamB_score.getText().toString())-score));
                break;
            case R.id.teamB_increase:
                teamB_score.setText(Integer.toString(Integer.parseInt(teamB_score.getText().toString())+score));
                break;
        }
    }
}
