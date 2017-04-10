package qazwsxedc.timetable;

/**
 * Created by Anshuman-HP on 28-12-2016.
 */

public class classs {
    String Classname;
    String From;
    String To;
    String Type;
    String Thumb;
    String TeacherName;
    String RoomNum;

    public classs() {
    }

    public classs(String classname, String from, String to, String type,  String thumb,String teacherName,String roomNum) {
        Classname = classname;
        From = from;
        To = to;
        Type = type;
        this.Thumb = thumb;
        TeacherName=teacherName;
        RoomNum=roomNum;
    }

    public String getClassname() {
        return Classname;
    }

    public void setClassname(String classname) {
        Classname = classname;
    }

    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        From = from;
    }

    public String getTo() {
        return To;
    }

    public void setTo(String to) {
        To = to;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getThumb() {
        return Thumb;
    }

    public void setThumb(String thumb) {
        this.Thumb = thumb;
    }

    public String getTeacherName() {
        return TeacherName;
    }

    public void setTeacherName(String teacherName) {
        TeacherName = teacherName;
    }

    public String getRoomNum() {
        return RoomNum;
    }

    public void setRoomNum(String roomNum) {
        RoomNum = roomNum;
    }
}
