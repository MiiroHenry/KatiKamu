import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Iterator;

//class the used to capture the excel sheet and store the values to the database
public class EnterResults {

    public void marks(String path) throws IOException, SQLException, InstantiationException, IllegalAccessException {
        //call to the database
        DB conn = new DB();

        long start = System.currentTimeMillis();
        File file = new File(path);
        FileInputStream inputStream = new FileInputStream(file);
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

        XSSFSheet firstSheet = workbook.getSheetAt(0);
//iterate through the values in the excel sheet
        Iterator iterator = firstSheet.iterator();

        conn.getConnection().setAutoCommit(false);

        PreparedStatement statement = null;
        PreparedStatement statement1 = null;
        while (iterator.hasNext()) {
            Row nextRow = (Row) iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();

            while (cellIterator.hasNext()) {
                Cell nextCell = cellIterator.next();
                //query to insert values to the database
                String sql = "INSERT INTO Marks (SID,Subject,Marks,TID) VALUES (?,?,?,?)";

                statement = conn.getConnection().prepareStatement(sql);


                int columnIndex = nextCell.getColumnIndex();

                switch (columnIndex) {
                    case 0:
                        String SID = nextCell.getStringCellValue();
                        statement.setString(1,SID);
                        break;
                    case 1:
                        String Subject = nextCell.getStringCellValue();
                        statement.setString(2,Subject);
                        break;
                    case 2:
                        int Marks = (int) nextCell.getNumericCellValue();
                        statement.setInt(3,Marks);
                        break;
                    case 3:
                        String TID = nextCell.getStringCellValue();
                        statement.setString(4,TID);
                            break;
                }
            }
            String sid = nextRow.getCell(0).getStringCellValue();
            int marks = (int) nextRow.getCell(2).getNumericCellValue();
            String subject = nextRow.getCell(1).getStringCellValue();
            String tid = nextRow.getCell(3).getStringCellValue();
            //query to check if teacher exists in the database
            String sql1 = "SELECT TID FROM Teachers WHERE TID ='" + tid + "'";
            //query to check if students exists in the database
            String sql2 = "SELECT SID FROM Students WHERE SID ='" + sid + "'";
            Statement st = conn.getConnection().createStatement();
            Statement st2 = conn.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql1);
            ResultSet rs2 = st2.executeQuery(sql2);
            //iterate through the results from the database
            if (rs.next()) {
                if(rs2.next()) {
                    statement.setString(1, sid);
                    statement.setString(2, subject);
                    statement.setInt(3, marks);
                    statement.setString(4, tid);
                    statement.executeUpdate();
                    conn.getConnection().close();
                }else {
                    System.out.println("student not in database:"+sid);
                }
            }else{
                conn.getConnection().close();
                System.out.println("teacher not in database:"+tid);

            }
        }

        workbook.close();
        conn.getConnection().close();

        long end = System.currentTimeMillis();
        System.out.printf("Import done in %d ms\n", (end - start));

    }

    }
