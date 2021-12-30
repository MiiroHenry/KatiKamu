import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

//class to chose a file thats contains the marks of students to be entered into the database
public class EnterMarks extends JFrame{
    private final String tid;
    private JPanel entermarkspanel;
    private JLabel title;
    private JButton button1;
    private JButton BACKButton;

    public EnterMarks(String title,String tid){
        super(title);
        this.tid =tid;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(entermarkspanel);
        this.pack();
        //on buttonClick of the button enter results
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

                int returnVal = fc.showSaveDialog(entermarkspanel);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    fc.setAcceptAllFileFilterUsed(false);
                    File fs = fc.getSelectedFile();
                    String extension = utils.getExtension(fs);
                    if (extension != null) {
                        if (extension.equals(utils.xls)) {
                            System.out.println("accepted");
                            EnterResults em = new EnterResults();
                            try {
                                em.marks(fs.getPath());
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            } catch (InstantiationException ex) {
                                ex.printStackTrace();
                            } catch (IllegalAccessException ex) {
                                ex.printStackTrace();
                            }
                            System.out.println("finished");
                        } else {
                            System.out.println("not accepted");
                        }
                    }
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
    }
}
