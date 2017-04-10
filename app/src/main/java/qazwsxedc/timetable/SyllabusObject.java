package qazwsxedc.timetable;

/**
 * Created by Anshuman-HP on 13-01-2017.
 */

public class SyllabusObject {
    String SubjectName;
    String Subphotourl;
    String Subcode;
    String Credits;
    Module module;

    public SyllabusObject() {
    }

    public SyllabusObject(String subjectName,  String subphotourl, String subcode, String credits,Module Module) {
        SubjectName = subjectName;
        Subphotourl = subphotourl;
        Subcode = subcode;
        Credits = credits;
        module=Module;

    }


    public String getSubjectName() {
        return SubjectName;
    }

    public void setSubjectName(String subjectName) {
        SubjectName = subjectName;
    }

    public String getSubphotourl() {
        return Subphotourl;
    }

    public void setSubphotourl(String subphotourl) {
        Subphotourl = subphotourl;
    }

    public String getSubcode() {
        return Subcode;
    }

    public void setSubcode(String subcode) {
        Subcode = subcode;
    }

    public String getCredits() {
        return Credits;
    }

    public void setCredits(String credits) {
        Credits = credits;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

}
