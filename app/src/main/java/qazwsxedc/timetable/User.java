package qazwsxedc.timetable;

/**
 * Created by Anshuman-HP on 17-01-2017.
 */

public class User {
    String Name;
    String PhotoURL;
    String Branch;
    String Registrationnumber;
    String Email;

    public User() {
    }

    public User(String name, String photoURL, String branch, String registrationnumber,String email) {
        Name = name;
        PhotoURL = photoURL;
        Branch = branch;
        Registrationnumber = registrationnumber;
        Email=email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhotoURL() {
        return PhotoURL;
    }

    public void setPhotoURL(String photoURL) {
        PhotoURL = photoURL;
    }

    public String getBranch() {
        return Branch;
    }

    public void setBranch(String branch) {
        Branch = branch;
    }

    public String getRegistrationnumber() {
        return Registrationnumber;
    }

    public void setRegistrationnumber(String registrationnumber) {
        Registrationnumber = registrationnumber;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
