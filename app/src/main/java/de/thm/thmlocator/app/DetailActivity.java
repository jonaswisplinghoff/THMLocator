package de.thm.thmlocator.app;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import de.thm.thmlocator.app.Entity.Event;
import de.thm.thmlocator.app.Entity.Room;


public class DetailActivity extends Activity {
    Room myRoom;
    TextView textViewRoomName, textViewEventType, textViewEventName, textViewCourseOfStudies, textViewTeacherName;
    ImageView imageViewCoverImage;
    ImageButton imageButtonTeacher;
    Button buttonARSnova;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Get Views from runtime
        textViewRoomName = (TextView) findViewById(R.id.textViewRoomName);
        imageButtonTeacher = (ImageButton) findViewById(R.id.imageButtonTeacher);
        textViewEventType = (TextView) findViewById(R.id.textViewEventType);
        textViewEventName = (TextView) findViewById(R.id.textViewEventName);
        textViewCourseOfStudies = (TextView) findViewById(R.id.textViewCourseOfStudies);
        textViewTeacherName = (TextView) findViewById(R.id.textViewTeacherName);
        imageViewCoverImage = (ImageView) findViewById(R.id.imageViewCoverImage);
        buttonARSnova = (Button) findViewById(R.id.buttonARSnova);

        int roomId =  getIntent().getIntExtra(MainActivity.ROOM_ID, 0);

        myRoom = DataManager.getRoomByID(roomId);

        if(myRoom != null) {

            textViewRoomName.setText(myRoom.getRoomName());
            imageViewCoverImage.setImageBitmap(myRoom.getRoomPicture());
            final Event currentEvent = myRoom.getEventByTime(new Date());
            if(currentEvent != null) {
                textViewEventType.setText(currentEvent.getEventType());
                textViewEventName.setText(currentEvent.getEventName());
                textViewCourseOfStudies.setText(currentEvent.getCourse());
                textViewTeacherName.setText(currentEvent.getEventProf());
                buttonARSnova.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(DetailActivity.this, currentEvent.getArsnovaURL(), Toast.LENGTH_LONG).show();
                    }
                });
            }
            imageButtonTeacher.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
