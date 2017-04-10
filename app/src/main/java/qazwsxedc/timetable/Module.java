package qazwsxedc.timetable;

/**
 * Created by Anshuman-HP on 13-01-2017.
 */

public class Module {
    String Number;
    String Content;
    public Module() {
    }

    public Module(String number, String content) {
        Number = number;
        Content = content;
    }
    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
