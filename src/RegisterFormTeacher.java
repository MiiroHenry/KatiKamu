import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class RegisterFormTeacher  extends JFrame{
    private JPanel regpanel;
    private JLabel title;
    private JTextField FirstNameField;
    private JTextField lastNameField;
    private JPasswordField passwordField;
    public JButton regbutton;
    private JButton back;
    private JComboBox sexcomboBox;
    private JComboBox subjectcomboBox;
    private String username;

    public RegisterFormTeacher(){
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(regpanel);
        this.pack();
        sexcomboBox.addItem("MALE");
        sexcomboBox.addItem("FEMALE");
        subjectcomboBox.addItem("MATH");
        subjectcomboBox.addItem("ENGLISH");
        subjectcomboBox.addItem("SCIENCE");
        subjectcomboBox.addItem("SST");
        regbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DB conn = new DB();
                //we generate a random Number for the students ID
                Random random = new Random();
                // generate random number from 0 to 100
                int number = random.nextInt(100000);
                String tid = "TID"+number;
                //concatenate the first name and last name to get a username
                username = FirstNameField.getText()+lastNameField.getText();
//query to insert Teachers into the database
                String sql ="insert into Teachers(TID,FirstName,LastName,UserName,SubjectsTaught,Sex,Password) values (?,?,?,?,?,?,?)";
                String sql2 ="insert into Users(Username,Password,UserType) values (?,?,?)";
                PreparedStatement ptst = null;
                try {
                    ptst = conn.getConnection().prepareStatement(sql);
                    ptst.setString(1,tid);
                    ptst.setString(2,FirstNameField.getText());
                    ptst.setString(3,lastNameField.getText());
                    ptst.setString(4,username);
                    ptst.setString(5, (String) subjectcomboBox.getSelectedItem());
                    ptst.setString(6, (String) sexcomboBox.getSelectedItem());
                    //hash the password using MD5
                    ptst.setString(7, conn.MD5(String.valueOf(passwordField.getPassword())));
                    ptst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "You have been succefully registered and your Teachers Numbers: "+tid+"\n Teachers Username:"+username);
                    conn.getConnection().close();
//redirect to the login page after registering
                    LoginPageTeachers obj =new LoginPageTeachers("Teacher Login");
                    obj.setVisible(true);
                    dispose();
                    obj.LOGINButton.setEnabled(false);

                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (InstantiationException ex) {
                    ex.printStackTrace();
                } catch (IllegalAccessException ex) {
                    ex.printStackTrace();
                }
                try{
                    ptst = conn.getConnection().prepareStatement(sql2);
                    ptst.setString(1,username);
                    ptst.setString(2, conn.MD5(String.valueOf(passwordField.getPassword())));
                    String type = "Teacher";
                    ptst.setString(3,type);
                    ptst.executeUpdate();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (InstantiationException ex) {
                    ex.printStackTrace();
                } catch (IllegalAccessException ex) {
                    ex.printStackTrace();
                }


            }
        });
        //validate all textfields to check if they are empty
        FirstNameField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                changed();
            }

            public void changedUpdate(DocumentEvent e) {
                changed();
            }
            public void removeUpdate(DocumentEvent e) {
                changed();
            }

            public void changed() {
                if (FirstNameField.getText().equals("")){
                    regbutton.setEnabled(false);
                }
                else {
                    regbutton.setEnabled(true);
                }

            }
        });
        lastNameField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                changed();
            }

            public void changedUpdate(DocumentEvent e) {
                changed();
            }
            public void removeUpdate(DocumentEvent e) {
                changed();
            }

            public void changed() {
                if (lastNameField.getText().equals("")){
                    regbutton.setEnabled(false);
                }
                else {
                    regbutton.setEnabled(true);
                }

            }
        });
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginPageTeachers obj =new LoginPageTeachers("Teacher Login");
                obj.setVisible(true);
                dispose();
                obj.LOGINButton.setEnabled(false);
            }
        });
    }
}
