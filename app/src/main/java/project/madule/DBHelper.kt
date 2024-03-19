package project.madule

import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.text.TextUtils
import android.util.Log
import project.main.G

class DBHelper(private val db: SQLiteDatabase) {
    fun truncate(table: String) {
        db.execSQL("DELETE FROM $table")
    }

    fun dumpCursor(cursor: Cursor) {
        val columnCount = cursor.columnCount
        var index = 0
        while (cursor.moveToNext()) {
            index++
            var rowStr = ""
            for (i in 0 until columnCount) {
                val type = cursor.getType(i)
                var value = ""
                when (type) {
                    Cursor.FIELD_TYPE_NULL -> value = "NULL"
                    Cursor.FIELD_TYPE_INTEGER -> value = "" + cursor.getInt(i)
                    Cursor.FIELD_TYPE_FLOAT -> value = "" + cursor.getFloat(i)
                    Cursor.FIELD_TYPE_STRING -> value = cursor.getString(i)
                }
                rowStr += "$value | "
            }
            Debug.info("#$index: $rowStr")
        }
    }

    fun createObject(clazz: Class<*>, cursor: Cursor): Array<Any?> {
        val columnCount = cursor.columnCount
        val objects = arrayOfNulls<Any>(cursor.count)
        var index = 0
        while (cursor.moveToNext()) {
            var `object`: Any? = null
            try {
                `object` = clazz.newInstance()
                objects[index] = `object`
            } catch (e: InstantiationException) {
                //e.printStackTrace();
            } catch (e: IllegalAccessException) {
                //e.printStackTrace();
            }
            for (i in 0 until columnCount) {
                val type = cursor.getType(i)
                val fieldName = cursor.getColumnName(i)
                try {
                    val field = clazz.getDeclaredField(fieldName)
                    when (type) {
                        Cursor.FIELD_TYPE_NULL -> field[`object`] = null
                        Cursor.FIELD_TYPE_INTEGER -> field[`object`] = cursor.getInt(i)
                        Cursor.FIELD_TYPE_FLOAT -> field[`object`] = cursor.getFloat(i)
                        Cursor.FIELD_TYPE_STRING -> field[`object`] = cursor.getString(i)
                    }
                } catch (e: IllegalAccessException) {
                    //e.printStackTrace();
                } catch (e: NoSuchFieldException) {
                    //e.printStackTrace();
                }
            }
            index++
        }
        return objects
    }

    fun insert(table: String, columns: Array<String>, values: Array<Any>): Int {
        val columnsStr = joinColumns(columns)
        val valuesStr = joinValues(values)
        try {
            db.execSQL("INSERT INTO $table($columnsStr) VALUES ($valuesStr)")
        } catch (e: SQLiteConstraintException) {
            Log.e("LOG", e.message)
        }
        return lastInsertId
    }

    fun delete(table: String, where: String) {
        db.execSQL("DELETE FROM $table WHERE $where")
    }

    fun update(table: String, columns: Array<String>, values: Array<Any>, where: String) {
        val updatesStr = joinColumnValuePair(columns, values)
        try {
            db.execSQL("UPDATE $table SET $updatesStr WHERE $where")
        } catch (e: SQLiteConstraintException) {
            Log.e("LOG", e.message)
        }
    }

    private fun joinColumnValuePair(columns: Array<String>, values: Array<Any>): String {
        val updates = arrayOfNulls<String>(columns.size)
        for (i in columns.indices) {
            val column = columns[i]
            val value = quote(values[i].toString())
            updates[i] = "$column=$value"
        }
        return TextUtils.join(", ", updates)
    }

    private val lastInsertId: Int
        private get() {
            val cursor = G.database!!.rawQuery("SELECT last_insert_rowid() AS lastId", null)
            cursor.moveToFirst()
            return cursor.getInt(cursor.getColumnIndex("lastId"))
        }

    private fun joinValues(values: Array<Any>): String {
        val valuesAsStr = arrayOfNulls<String>(values.size)
        for (i in values.indices) {
            val value = values[i].toString()
            valuesAsStr[i] = quote(value)
        }
        return TextUtils.join(", ", valuesAsStr)
    }

    private fun joinColumns(columns: Array<String>): String {
        return TextUtils.join(", ", columns)
    }

    private fun quote(value: String): String {
        return "'$value'"
    }
}