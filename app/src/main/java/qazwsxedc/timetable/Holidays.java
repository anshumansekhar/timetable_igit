package qazwsxedc.timetable;


import android.content.ContentValues;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

public class Holidays extends AppCompatActivity {
    MaterialCalendarView calendarview;
    DatabaseReference ref;
    ArrayList<holiday> holidays;
    holiday fgh;


    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holidays);
        setTitle("Holidays");
        calendarview=(MaterialCalendarView) findViewById(R.id.holiday);
        calendarview.setShowOtherDates(MaterialCalendarView.SHOW_ALL);
        holidays=new ArrayList<holiday>();
        fgh=new holiday();
        ref=FirebaseDatabase.getInstance().getReference().child("Holidays");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot: dataSnapshot.getChildren())
                {
                    fgh.setHolidaydate(postSnapshot.getValue().toString());
                    fgh.setHolidayName(postSnapshot.getKey());
                    holidays.add(fgh);
                    calendardecorater decorator=new calendardecorater(holidays);
                    calendarview.addDecorator(decorator);
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
