package qazwsxedc.timetable;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v7.app.ActionBar;
import android.util.Log;

import com.amulyakhare.textdrawable.TextDrawable;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Anshuman-HP on 16-01-2017.
 */

public class calendardecorater implements DayViewDecorator {
    ActionBar actionBar;
    ArrayList<holiday> holidayArrayList;
    SimpleDateFormat f;
    ArrayList<Date> dates;

    public calendardecorater(ArrayList<holiday> holidayArrayList) {
       this.holidayArrayList=new ArrayList<holiday>();
        dates=new ArrayList<Date>();
        for(holiday holiday:holidayArrayList)
        {
            this.holidayArrayList.add(holiday);
        }
        f = new SimpleDateFormat("dd/MMM/yyyy");
        for(holiday holiday:holidayArrayList)
        {
            try {
                dates.add(f.parse(holiday.getHolidaydate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day.getDate());
    }

    @Override
    public void decorate(DayViewFacade view) {
        TextDrawable drawabled = TextDrawable.builder()
                .buildRect("A", Color.GREEN);
        view.setBackgroundDrawable(drawabled);

    }
}
