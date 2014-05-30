package de.thm.thmlocator.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import de.thm.thmlocator.app.Entity.Room;


public class DetailActivity extends Activity {
    Room myRoom;
    TextView roomName;
    ImageView teacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Get Views from runtime
        roomName = (TextView) findViewById(R.id.textViewRoomName);
        teacher = (ImageView) findViewById(R.id.imageButtonTeacher);

        int roomId =  getIntent().getIntExtra(MainActivity.ROOM_ID, 0);

        myRoom = DataManager.getRoomByID(roomId);

        if(myRoom != null) {

            roomName.setText(roomId);
            teacher.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if (b) {
                        view.setBackground(getResources().getDrawable(R.drawable.teacher_click));
                    } else {
                        view.setBackground(getResources().getDrawable(R.drawable.teacher));
                    }
                }
            });
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
