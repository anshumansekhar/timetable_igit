package qazwsxedc.timetable;

import java.util.Date;

/**
 * Created by Anshuman-HP on 16-01-2017.
 */

public class holiday {
    String HolidayName;
    String holidaydate;

    public holiday() {
    }

    public holiday(String holidayName, String holidaydate) {
        HolidayName = holidayName;
        this.holidaydate = holidaydate;
    }

    public String getHolidayName() {
        return HolidayName;
    }

    public void setHolidayName(String holidayName) {
        HolidayName = holidayName;
    }

    public String getHolidaydate() {
        return holidaydate;
    }

    public void setHolidaydate(String holidaydate) {
        this.holidaydate = holidaydate;
    }
}
