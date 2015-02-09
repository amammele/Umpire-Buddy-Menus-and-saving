package alexm.umkc.edu.ub;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;





public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    public final static String EXTRA_DATA = "edu.umkc.alexm.ub.About";

    static final private String TAG = "Umpire Buddy";
    private static final String PREFS_NAME = "MyPrefsFile";

    private int Scount = 0;
    private int Bcount = 0;
    private int Tcount = 0;

    // checks for saved variables either instants or stored data and loads them in
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Starting onCreate...");
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        Tcount = settings.getInt("Tcount", 0);

        if (savedInstanceState != null) {


            Scount = savedInstanceState.getInt("Scount");
            Bcount = savedInstanceState.getInt("Bcount");
            Tcount = savedInstanceState.getInt("Tcount");
        }
        // makes buttons clickable
        setContentView(R.layout.activity_main);

        View strikeButton = findViewById(R.id.StrikeButton);

        strikeButton.setOnClickListener(this);

        View ballButton = findViewById(R.id.BallButton);
        ballButton.setOnClickListener(this);

        updateCount();
    }
// updates the count
    private void updateCount() {
        TextView s = (TextView) findViewById(R.id.StrikeCountValue);
        s.setText(Integer.toString(Scount));
        TextView b = (TextView) findViewById(R.id.BallCountValue);
        b.setText(Integer.toString(Bcount));
        TextView t = (TextView) findViewById(R.id.TotalCountValue);
        t.setText(Integer.toString(Tcount));
    }

    // strike button increments the strikes up to 3 after that increments the total strikes
    // and saves the data
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.StrikeButton:

                if (Scount == 2) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);

                    builder.setMessage("You're Out!!!");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            Scount = 0;
                            Bcount = 0;
                            Tcount++;
                            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putInt("Tcount", Tcount);
                            // Commit the edits
                            editor.commit();


                            updateCount();
                        }
                    });
                    builder.show();


                } else {
                    Scount++;


                }
                break;
            // ball button increases the amount of balls counted up to 4
            // then starts over after a dialog box
            case R.id.BallButton:
                if (Bcount == 3) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);

                    builder.setMessage("Take A Walk!!!");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            Scount = 0;
                            Bcount = 0;
                            updateCount();
                        }
                    });
                    builder.show();


                } else {
                    Bcount++;
                }
                break;

        }
        updateCount();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
// the reset button lets you turn everyhting back to zero but will not change the save state of
    // the total strikes
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.reset:
                Scount = 0;
                Bcount = 0;
                Tcount = 0;
                updateCount();
                return true;
            case R.id.about:
                Intent intent = new Intent(this, About.class);
                intent.putExtra(EXTRA_DATA, "extra data or parameter you want to pass to activity");
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

// save instants so when you rotate or go to another screen variables stay the same
    @Override
    protected void onSaveInstanceState(Bundle icicle) {
        super.onSaveInstanceState(icicle);

        Log.i(TAG, "onSaveInstanceState()");
        icicle.putInt("Scount", Scount);
        icicle.putInt("Bcount", Bcount);
        icicle.putInt("Tcount", Tcount);
    }
}









