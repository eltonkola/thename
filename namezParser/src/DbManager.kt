
import com.google.gson.Gson
import java.sql.SQLException
import java.sql.DriverManager

class DbManager {

    private val url = "jdbc:sqlite:/home/elton/eltongit/namezParser/output/emra.sqlite"

    fun createNewDatabase() {
        try {
            DriverManager.getConnection(url).use { conn ->
                if (conn != null) {
                    val meta = conn.metaData
                    println("The driver name is " + meta.driverName)
                    println("A new database has been created.")
                }

            }
        } catch (e: SQLException) {
            println(e.message)
        }

    }

    fun createTable() {

        val sql1 = ("CREATE TABLE IF NOT EXISTS namez (\n"
                + "	name text PRIMARY KEY ,\n"
                + "	male integer NOT NULL ,\n"
                + "	frequency integer NOT NULL,\n"
                + " thumb INTEGER DEFAULT 0,\n"
                + " favorite INTEGER DEFAULT 0,\n"
                + "	peryear text\n"
                + ");")
        val sql2 = ("CREATE TABLE android_metadata (locale TEXT);")
        val sql3 = ("INSERT INTO android_metadata VALUES ('en_US');")
        try {
            DriverManager.getConnection(url).use { conn ->
                conn.createStatement().use { stmt ->
                    stmt.execute(sql1)
                    stmt.execute(sql2)
                    stmt.execute(sql3)
                }
            }
        } catch (e: SQLException) {
            println(e.message)
        }

    }

    private val gson = Gson()

    fun insertEmri(emri: Emri){
        val sql = ("INSERT INTO namez (name, male, frequency, peryear) VALUES ( '${emri.emri}', '${emri.mashkull}',  ${emri.frequency}, '${gson.toJson(emri.perYear)}'); ")

        //println("sql to run: $sql")
        try {
            DriverManager.getConnection(url).use { conn ->
                conn.createStatement().use { stmt ->
                    stmt.execute(sql)
                }
            }
        } catch (e: SQLException) {
            println(e.message)
        }
    }

/*

CREATE  INDEX favorite ON namez(favorite);
CREATE  INDEX frequency ON namez(frequency);
CREATE  INDEX thumbUp ON namez(thumbUp);
CREATE  INDEX thumbDown ON namez(thumbDown);
CREATE  INDEX male ON namez(male);

CREATE TABLE android_metadata (locale TEXT);
INSERT INTO android_metadata VALUES ("en_US");

CREATE VIEW eksploro(name, male, frequency, thumbUp, thumbDown, favorite, peryear) AS Select * from namez where thumbDown == 'false' and thumbUp == 'false' and favorite == 'false'
CREATE VIEW femra(name, male, frequency, thumbUp, thumbDown, favorite, peryear) AS Select * from namez where male == 'false'
CREATE VIEW meshkuj(name, male, frequency, thumbUp, thumbDown, favorite, peryear) AS Select * from namez where male == 'true'
CREATE VIEW liked(name, male, frequency, thumbUp, thumbDown, favorite, peryear) AS Select * from namez where thumbUp == 'true'
CREATE VIEW favorited(name, male, frequency, thumbUp, thumbDown, favorite, peryear) AS Select * from namez where favorite == 'true'

*/

}