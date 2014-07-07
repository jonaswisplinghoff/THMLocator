package de.thm.thmlocator.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
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
    Button buttonARSnova, buttonEmail, buttonMoodle;

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
        buttonEmail = (Button) findViewById(R.id.buttonEmail);
        buttonMoodle = (Button) findViewById(R.id.buttonMoodleKurs);

        int roomId =  getIntent().getIntExtra(MainActivity.ROOM_ID, 0);

        myRoom = DataManager.getRoomByID(roomId);

        if(myRoom != null) {

            textViewRoomName.setText(myRoom.getRoomName());
            Bitmap myCoverPicture = myRoom.getRoomPicture();
            if(myCoverPicture != null) {
                imageViewCoverImage.setImageBitmap(myCoverPicture);
            }
            final Event currentEvent = myRoom.getEventByTime(new Date());
            if(currentEvent != null) {
                textViewEventType.setText(currentEvent.getEventType());
                textViewEventName.setText(currentEvent.getEventName());
                textViewCourseOfStudies.setText(currentEvent.getCourse());
                textViewTeacherName.setText(currentEvent.getEventProf());
                buttonARSnova.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(DetailActivity.this, currentEvent.getArsnovaURL(), Toast.LENGTH_LONG).show();
                        Intent newIntent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse(currentEvent.getArsnovaURL()));
                        startActivity(newIntent);
                    }
                });

                buttonEmail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(DetailActivity.this, currentEvent.getArsnovaURL(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/html");
                        intent.putExtra(Intent.EXTRA_EMAIL, currentEvent.getContactMail());
                        intent.putExtra(Intent.EXTRA_SUBJECT, currentEvent.getEventName());
                        intent.putExtra(Intent.EXTRA_TEXT, "Dear "+currentEvent.getEventProf()+",\n");
                        startActivity(Intent.createChooser(intent, "Send Email"));
                    }
                });


                buttonMoodle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(DetailActivity.this, currentEvent.getArsnovaURL(), Toast.LENGTH_LONG).show();
                        Intent newIntent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse(currentEvent.getMoodleCourse()));
                        startActivity(newIntent);
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

        return super.onOptionsItemSelected(item);
    }

}
