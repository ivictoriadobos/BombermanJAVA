package PaooGame.Database;
import PaooGame.Score.Score;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 \class Database
 \brief Clasa ce se ocupa cu gestionarea bazei de date sqlite

    Clasa implementeaza sablonul SINGLETON.
    Aceasta baza de date este proiectata in asa maniera incat sa retina doar primele 5 cele mai mari scoruri. Fiecare scor are timestamp.
 */
public class Database {

    /// Referinta statica catre o instanta a clasei ce completeaza sablonul singleton.
    private static Database db;
    ///Conexiunea catre baza de date
    private static Connection c = null;
    ///Un obiect Statement
    private static Statement stmt = null;

    ///Un hashmap ce contine primele 5 cele mai mari scoruri din baza de date
    private Map<String, Integer> dataAndScores = new HashMap<>();


    /// un fel de constructor, dar nu chiar, asa procedam pentru atributele statice
    static {
            db = new Database();
            System.out.println("Opened database successfully");
         }


         private Database() {
        try {
            System.out.println("CONSTRUCTOR OF DATABASE");
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:paooGame.db");

            stmt = c.createStatement();

            ///Se creeaza tabela SCOR daca nu exista deja cu coloanele DATA (timestampul scorului) si VALOARE (valoarea scorului)
            String sql = "CREATE TABLE IF NOT EXISTS SCOR " +
                    "( DATA TEXT PRIMARY KEY NOT NULL," +
                    " VALOARE INT NOT NULL)";
            stmt.executeUpdate(sql);
            stmt.close();
            System.out.println("END OF CONSTRUCTOR OF DATABASE");

        }
        catch (SQLException | ClassNotFoundException e)
        {
            System.out.println("XXXXXXXXXXXXXXXXXXXXX     DataBase.DataBase ERROR");
            return;
        }
        }

        /*!
        \fn         public void UpdateDatabase()
        Metoda ce se apeleaza inainte de trecerea in stare de joc terminat (GameEndState), astfel facandu-se actualizarile bazei de date la finalul meciului
         */
         public void UpdateDatabase()  {
             try {
                 System.out.println("&&&&&&&&&&     UpdateDatabase BEGINS");

                 Class.forName("org.sqlite.JDBC");
                 c = DriverManager.getConnection("jdbc:sqlite:paooGame.db");


                 int nouScor = Score.GetScoreInstance().getScore();

                 System.out.println("SCORUL DE INTRODUS E " + nouScor);


                 Date date = new Date(); // This object contains the current date value
                 SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                 String data = formatter.format(date);

                 stmt = c.createStatement();
                 String sql = "INSERT INTO SCOR (VALOARE, DATA) " +
                         "VALUES ( " + (int)nouScor + ", \'" + data + "\' );";
                 stmt.executeUpdate(sql);
                 stmt.close();
                 c.close();

                 System.out.println("\n*** Records created successfully\n");
                 System.out.println("&&&&&&&&&&     UpdateDatabase ENDS");
                 System.out.println("&&&&&&&&&&     KEEP5BIGGEST MUST BEGIN.. ");

             }
             catch (SQLException | ClassNotFoundException e  )
             {
                 if (e.getMessage().startsWith("UNIQUE")) {
                     return;
                 }
                 else {
                     System.out.println("XXXXXXXXXXXXXXX     DataBase.UpdateDatabase ERROR");
                     System.out.println(e.getMessage());
                 }
             }

             ///Odata introdus in BD noul scor, se pastreaza primele 5 cele mai mari
             keep5BiggestScoresFromDB();

         }


///Metoda redundanta pentru gameplay, am folosit-o la debug
    public static void showDB() {  // CLEAN
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:paooGame.db");
            stmt = c.createStatement();
            System.out.println("\n\n************SHOW DB BEGINS");
            ResultSet rs = stmt.executeQuery("SELECT * FROM SCOR ;");
            while (rs.next()) {
                int valoare = rs.getInt("VALOARE");
                String data = rs.getString("DATA");
                System.out.println("SCOR : " + valoare);
                System.out.println("DATA : " + data);
                System.out.println();

            }

            stmt.close();
            c.close();
            System.out.println("************SHOW DB ENDS");
        }
        catch (SQLException | ClassNotFoundException sql )
        {
            System.out.println("xxxxxxxxxxxxxxxx     DataBase.showDB ERROR");;
        }
    }

    /*!
    \fn    private void keep5BiggestScoresFromDB()

     */
    private void keep5BiggestScoresFromDB()
    {

        System.out.println("@@@@@@@@@@@ KEEEP5BIGGEST... BEGINS");
        String  toDelete = null; // variabila in care punem data celui mai mic scor (avem nevoie doar de un string fiindca avem maxim 6 intrari in db)
        int counter = 0; // in acest counter tinem evidenta numarului de entries in db (el va fi maxim 5)

        try {
            System.out.println("TRYING TO CONNECT...");
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:paooGame.db");
            stmt = c.createStatement();
            System.out.println("CONNECTION SUCCEEDED");

            ResultSet rs = stmt.executeQuery("SELECT * FROM SCOR ORDER BY VALOARE DESC; "); /// luam intrarile din db in ordine descendenta

            //   String sql2 = "SELECT COUNT(*) AS noOfEntries FROM SCOR;"; // numaram cate intrari sunt in db
            Statement stmt2 = c.createStatement();
            ResultSet rs2 = stmt2.executeQuery("SELECT COUNT(*) AS noOfEntries FROM SCOR;");  /// numaram cate intrari sunt in db
            int noOfEntries = rs2.getInt("noOfEntries");


            while (rs.next()) {  /// parcurgem intrarile din db (in ordine descendenta)
                if (counter < 5) {
                    ///daca inca numaram sub 5 intrari in db, inseamna ca e una din cele mai mari (Ca si scor) si o punem in hashmapul clasei
                    dataAndScores.put(rs.getString("DATA"), rs.getInt("VALOARE"));
                    counter++;
                }
                else {///altfel inseamna ca e cea mai mica intrare (Deoarece parcurgem elementele selectate descendent ca scor) si trebuie s o stergem
                    toDelete = rs.getString("DATA");  // aceasta intrare trebuie s o stergem
                    System.out.println("TO DELETE VALOARE FROM DATA : " + toDelete);
                }
            }
            System.out.println("DELETE from SCOR WHERE DATA = '" + toDelete + "' ;)");
            if ((toDelete != null) && (stmt.executeUpdate("DELETE from SCOR WHERE DATA = '" + toDelete + "' ;") != 0)) {
                System.out.println("Deletion successfully done");
            }
            stmt.close();
            stmt2.close();
            c.close();

            System.out.println("@@@@@@@@@@ KEEEP5BIGGEST... ENDS");

        }
        catch (SQLException | ClassNotFoundException e)
        {
            System.out.println("\nXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
            System.out.println("DataBase.keepAndOrder5BiggestScoresFromDB");
            System.out.println(e.getMessage());
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n");
        }


    }

    /*!
    \fn     public void Draw(Graphics g)
Metoda responsabila cu desenarea bazei de date
     */
    public void Draw(Graphics g)
    {
        System.out.println("DRAW DB CALLED");
        int i = 0;
        g.setFont(new Font("default", Font.BOLD, 20));
        g.setColor(Color.blue);
        for (Map.Entry<String, Integer> entry : dataAndScores.entrySet()) {
            String k = entry.getKey();
            int v = entry.getValue();
            System.out.println("Key: " + k + ", Value: " + v);

            System.out.println(k + " : " + v);
            g.drawString(k + " : " + v, 300, 300+(i++)*40);
        }


    }

///Metoda statica ce intoarce referinta catre baza de date. Completeaza sablonul Singleton
    public static Database getInstance()
    {
        return db;
    }
/*
    public static void main(String[] args) {

        Score.GetScoreInstance().alterScore(50);
        getInstance().UpdateDatabase();
        showDB();
    }

 */
}
