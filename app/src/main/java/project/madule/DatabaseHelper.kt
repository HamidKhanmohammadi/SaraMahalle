package project.madule

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import project.main.G

class DatabaseHelper(context: Context?) :
    SQLiteOpenHelper(context, G.directorySQLTest.toString() + "/" + DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        createProdactTable(db)
        createCustomerTable(db)
        //db.execSQL("CREATE YOUR TABLES b ( QUERY )")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
       if(newVersion == 2){
           createProdactTable(db)
       }
       if(newVersion == 3){
           createCustomerTable(db)
       }
        //db.execSQL("YOUR UPGRAGE QUERY 2")
    }

    companion object {
        const val DB_NAME = "testDB.sqlite"
        const val DB_VERSION = 3
    }
}

fun createProdactTable(db: SQLiteDatabase){
    db.execSQL("CREATE TABLE IF NOT EXISTS products (" +
            "product_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "product_name TEXT," +
            "product_logoUrl TEXT)"
    )
}
fun createCustomerTable(db: SQLiteDatabase){
    db.execSQL("CREATE TABLE IF NOT EXISTS customers (" +
            "customer_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "product_id INTEGER," +
            "customer_name TEXT," +
            "customer_famili TEXT," +
            "customer_phoneNumber TEXT)"
    )
}