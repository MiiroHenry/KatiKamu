import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

//class to register students
public class RegisterStudent extends JFrame {
    private final String tid;
    private JPanel regpanel;
    private JLabel title;
    private JTextField firstNameField;
    private JTextField lastNamefield;
    private JLabel firstName;
    private JLabel lastName;
    private JLabel registerby;
    private JTextField registeredByField;
    private JButton registerbutton;
    private JPasswordField passwordField;
    private JButton BACKButton;
    private JComboBox sexcomboBox;
    private JComboBox subjectcomboBox;
    private JComboBox classcomboBox;
    private JLabel Sex;


    public RegisterStudent(String tid){
        super();
        this.tid=tid;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(regpanel);
        this.pack();

        //adding values to the combobox
        sexcomboBox.addItem("MALE");
        sexcomboBox.addItem("FEMALE");
        subjectcomboBox.addItem("CRE");
        subjectcomboBox.addItem("IRE");
        classcomboBox.addItem("P1");
        classcomboBox.addItem("P2");
        classcomboBox.addItem("P3");
        classcomboBox.addItem("P4");
        classcomboBox.addItem("P5");
        classcomboBox.addItem("P6");
        classcomboBox.addItem("P7");
        registerbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //connect to database
                DB conn = new DB();
                //we generate a random Number for the Teachers ID
                Random random = new Random();
                // generate random number from 0 to 100
                int number = random.nextInt(10000000);
                String sid = "SID"+number;
                //concatenate the first name and last name to get a username
                String username = firstNameField.getText()+lastNamefield.getText();
//query to insert Students into the database
                String sql ="insert into Students(SID,FirstName,LastName,userName,Sex,OptionalSubject,Class,Password,RegisteredBy) values (?,?,?,?,?,?,?,?,?)";
                String sql2 ="insert into Users(Username,Password,UserType) values (?,?,?)";
                PreparedStatement ptst = null;
                try {
                    ptst = conn.getConnection().prepareStatement(sql);
                    ptst.setString(1,sid);
                    ptst.setString(2,firstNameField.getText());
                    ptst.setString(3,lastNamefield.getText());
                    ptst.setString(4,username);
                    ptst.setString(5, (String) sexcomboBox.getSelectedItem());
                    ptst.setString(6, (String) subjectcomboBox.getSelectedItem());
                    ptst.setString(7, (String) classcomboBox.getSelectedItem());
                    ptst.setString(8, conn.MD5(String.valueOf(passwordField.getPassword())));
                    ptst.setString(9, registeredByField.getText());
                    ptst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Student has been registered and your STUDENTS NUMBER: "+sid+"\nYour Username is: "+username+"\n Your password is: "+String.valueOf(passwordField.getPassword()));
                    conn.getConnection().close();
//redirect to the Teachers page after registering
                    Teacher obj =new Teacher(tid);
                    obj.setVisible(true);
                    obj.loginbutton.setEnabled(false);
                    dispose();

                } catch (SQLException | IllegalAccessException | InstantiationException ex) {
                    ex.printStackTrace();
                }

                try{
                    ptst = conn.getConnection().prepareStatement(sql2);
                    ptst.setString(1,username);
                    ptst.setString(2, conn.MD5(String.valueOf(passwordField.getPassword())));
                    String type = "Student";
                    ptst.setString(3,type);
                    ptst.executeUpdate();
                } catch (SQLException | IllegalAccessException | InstantiationException ex) {
                    ex.printStackTrace();
                }
            }
        });
        BACKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Teacher obj =new Teacher(tid);
                obj.setVisible(true);
                dispose();
                obj.loginbutton.setEnabled(false);
            }
        });
        //validation if they are empty
        firstNameField.getDocument().addDocumentListener(new DocumentListener() {
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
                if (firstNameField.getText().equals("")){
                    registerbutton.setEnabled(false);
                }
                else {
                    registerbutton.setEnabled(true);
                }

            }
        });
        lastNamefield.getDocument().addDocumentListener(new DocumentListener() {
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
                if (lastNamefield.getText().equals("")){
                    registerbutton.setEnabled(false);
                }
                else {
                    registerbutton.setEnabled(true);
                }

            }
        });

        registeredByField.getDocument().addDocumentListener(new DocumentListener() {
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
                if (registeredByField.getText().equals("")){
                    registerbutton.setEnabled(false);
                }
                else {
                    registerbutton.setEnabled(true);
                }

            }
        });


    }


}
