import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DisplayTimetable extends JFrame {
    private String SID;
    private final String StdClass;
    private JPanel mainpanel;
    private JButton BACKButton;
    private JTable timetable;
    private JScrollPane scrollplane;
//constructor that accepts a students id and a students class
    public DisplayTimetable(String SID,String StdClass) throws SQLException, InstantiationException, IllegalAccessException {
        super();
        this.StdClass = StdClass;
        this.SID = SID;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainpanel);
        this.pack();
        setSize(300, 400);

//connection class to database
        DB conn = new DB();

//list of type timetables class
        List<timetables> tt = new ArrayList<>();

        Statement st = conn.getConnection().createStatement();
        //query to select students classes from the database
        String sql = "SELECT * FROM " + StdClass + "";

        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            String TID = rs.getString("TID");
            String Subject = rs.getString("Subject");
            String Day = rs.getString("Day");
            String session = rs.getString("Session");
            tt.add(new timetables(TID,Subject,Day,session));
        }
        rs.close();
        //table to display the timetable based on the student's class
        DisplayTimetable.StudentsTimeTableModel studentsTableModel = new DisplayTimetable.StudentsTimeTableModel(tt);
        timetable.setModel(studentsTableModel);
        timetable.setAutoCreateRowSorter(true);
        timetable.setAutoResizeMode(400);
        scrollplane.setViewportView(timetable);
        scrollplane.setVisible(true);

//on click of the back button
        BACKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Students obj = new Students(SID,StdClass);
                obj.setVisible(true);
                dispose();
            }
        });
    }
//creating of a student's timetable table  to display the timetable of the students
    private static class StudentsTimeTableModel extends AbstractTableModel {
        private  String[] COLUMNS = {"TID", "Subject", "Day", "Session"};
        private List<timetables> rs;
//constructor of the StudentsTimeTableModel taking in a list as parameters
        private StudentsTimeTableModel(List<timetables> timetable) {
            this.rs = timetable;
        }

        @Override
        public int getRowCount() {
            return rs.size();
        }

        @Override
        public int getColumnCount() {
            return COLUMNS.length;
        }
//getting of the results from the databse from the timetable class list
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return switch (columnIndex) {
                case 0 -> rs.get(rowIndex).getTID();
                case 1 -> rs.get(rowIndex).getSubject();
                case 2 -> rs.get(rowIndex).getDay();
                case 3 -> rs.get(rowIndex).getSessionTime();
                default -> "-";
            };
        }
//getting the columns names of the table
        @Override
        public String getColumnName(int column) {
            return COLUMNS[column];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (getValueAt(0, columnIndex) != null) {
                return getValueAt(0, columnIndex).getClass();
            } else {
                return Object.class;
            }
        }

    }
}
