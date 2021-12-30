import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
// class to help us to connect to the database
public class DB {

    public Connection getConnection() throws SQLException, InstantiationException, IllegalAccessException {
        String dbURL = "jdbc:mysql://localhost:3306/katikamudrb";
        String username = "tonny";
        String password = "tony256k";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch( ClassNotFoundException e ) {
            e.printStackTrace();
        }


        Connection con = DriverManager.getConnection( dbURL, username, password );

        if ( con != null ) {
            return con;
        }
        else return null;
    }

//method for hashing of the password
    public  String MD5(String password) {

            String passwordToHash = password;
            String generatedPassword = null;

            try
            {
                // Create MessageDigest instance for MD5
                MessageDigest md = MessageDigest.getInstance("MD5");

                // Add password bytes to digest
                md.update(passwordToHash.getBytes());

                // Get the hash's bytes
                byte[] bytes = md.digest();

                // This bytes[] has bytes in decimal format. Convert it to hexadecimal format
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < bytes.length; i++) {
                    sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
                }

                // Get complete hashed password in hex format
                generatedPassword = sb.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return generatedPassword;
        }
    }