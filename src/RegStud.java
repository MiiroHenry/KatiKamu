public class RegStud {
    private String SID;
    private String FirstName;
    private String LastName;
    private String aClass;
    private String Sex;
    private String Subject;
    private String RegisteredBy;

    public RegStud(String SID, String firstName, String lastName,String sex,String OptionalSubject, String aclass, String registeredBy) {
        this.SID = SID;
        FirstName = firstName;
        LastName = lastName;
        Sex = sex;
        Subject = OptionalSubject;
        aClass = aclass;
        RegisteredBy = registeredBy;
    }

    public String getSID() {
        return SID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getSex() {
        return Sex;
    }

    public String getSubject() {
        return Subject;
    }

    public String getaClass() {
        return aClass;
    }

    public String getRegisteredBy() {
        return RegisteredBy;
    }
}
