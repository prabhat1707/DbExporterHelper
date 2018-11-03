package com.android.dbexporterlibrary

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Environment
import au.com.bytecode.opencsv.CSVWriter
import java.io.*
import java.util.*

class ExportDbUtil(context: Context, db: String, directoryName: String, private var exporterListener: ExporterListener) {
    var dbName: String
    var directoryName: String
    var context: Context
    lateinit var dbFile: File
    lateinit var database: SQLiteDatabase
    lateinit var tables: ArrayList<String>
    lateinit var exportDir: File

    companion object {

    }

    init {
        this.context = context
        this.dbName = db
        this.directoryName = directoryName
        exportToCsv()
    }

    private fun exportToCsv() {
        dbFile = context.getDatabasePath(dbName).absoluteFile
        database = SQLiteDatabase.openOrCreateDatabase(dbFile, null)
        tables = getAllTables(database)
        exportDir = File(Environment.getExternalStorageDirectory().absolutePath + "/" + directoryName)
        if (!exportDir.exists()) {
            exportDir.mkdirs()
        }
    }


    fun getAllTables(database: SQLiteDatabase?): ArrayList<String> {
        val tables = ArrayList<String>()
        val cursor = database!!.rawQuery("select name from sqlite_master where type='table' order by name", null)
        while (cursor.moveToNext()) {
            tables.add(cursor.getString(0))
        }
        cursor.close()
        return tables
    }

    /**
     * @param tableName = export table name
     * @param csvFileName = filemame that you want to show after export
     *
     */
    fun exportSingleTable(tableName: String, csvFileName: String) {
        val file = File(exportDir, csvFileName)
        try {

            file.createNewFile()
            val csvWrite = CSVWriter(FileWriter(file))
            exportTable(tableName, csvWrite)
            csvWrite.close()
            exporterListener.success("$tableName successfully Exported")
        } catch (sqlEx: Exception) {
            exporterListener.fail("Export $tableName fail", sqlEx.message.toString())
        }
    }

    /**
     * @param csvFileName = filemame that you want to show after export
     *
     */
    public fun exportAllTables(csvFileName: String) {
        val file = File(exportDir, csvFileName)
        try {

            file.createNewFile()
            val csvWrite = CSVWriter(FileWriter(file))
            for (i in 0..tables.size - 1) {
                if (!tables[i].equals("android_metadata", true) && !tables[i].equals("room_master_table", true)) {
                    exportTable(tables[i], csvWrite)
                }
            }
            csvWrite.close()
            exporterListener.success("$csvFileName successfully Exported")
        } catch (sqlEx: Exception) {
            exporterListener.fail("Export $csvFileName fail", sqlEx.message.toString())
        }
    }


    private fun exportTable(tableName: String, csvWrite: CSVWriter) {
        var curCSV: Cursor? = null
        curCSV = database!!.rawQuery("SELECT $tableName.* FROM $tableName", null)
        csvWrite.writeNext(curCSV!!.getColumnNames())
        val arrStr = Array(curCSV.columnCount) { "it = $it" }

        while (curCSV.moveToNext()) {
            //Which column you want to exprort
            for (i in 0..curCSV.columnCount) {
                if (curCSV.getType(i) == Cursor.FIELD_TYPE_INTEGER) {
                    arrStr[i] = curCSV.getInt(i).toString()
                } else if (curCSV.getType(i) == Cursor.FIELD_TYPE_STRING) {
                    arrStr[i] = (curCSV.getString(i))
                }
            }
            csvWrite.writeNext(arrStr)

        }

        curCSV.close()
    }

    /**
     * @param appDBPath = db path of you app. see sample for detail
     *
     */
    fun exportDb(appDBPath: String) {
        try {
            val externalStorageDir = File(Environment.getExternalStorageDirectory(), directoryName)
            if (!externalStorageDir.exists()) {
                externalStorageDir.mkdirs()
            }
            val internalStorageDir = Environment.getDataDirectory()
//            val appDBPath = "/data/com.android.dbexporterlibrary/databases/"    //getDatabasePath(DATABASE_NAME).absolutePath;

            //.db file
            val currentDB = File(internalStorageDir, appDBPath + dbName)
            val backupDB = File(externalStorageDir, dbName)
            val source = FileInputStream(currentDB).channel
            val destination = FileOutputStream(backupDB).channel
            destination.transferFrom(source, 0, source.size())
            source.close()
            destination.close()

            //-wal file
            val currentWalDB = File(internalStorageDir, "$appDBPath$dbName-wal")
            val backupWalDB = File(externalStorageDir, "$dbName-wal")
            val sourceWal = FileInputStream(currentWalDB).channel
            val destinationWal = FileOutputStream(backupWalDB).channel
            destinationWal!!.transferFrom(sourceWal, 0, sourceWal!!.size())
            sourceWal.close()
            destinationWal.close()

            //-shm file
            val currentShmDB = File(internalStorageDir, "$appDBPath$dbName-shm")
            val backupShmDB = File(externalStorageDir, "$dbName-shm")
            val sourceShm = FileInputStream(currentShmDB).channel
            val destinationShm = FileOutputStream(backupShmDB).channel
            destinationShm!!.transferFrom(sourceShm, 0, sourceShm!!.size())
            sourceShm.close()
            destinationShm.close()
            exporterListener.success("DB successfully Exported")
        } catch (e: Exception) {
            exporterListener.fail("Export DB fail", e.message.toString())
        }
    }

    /**
     * @param appDBPath = db path of you app. see sample for detail
     *
     */

    fun importDBFile(appDBPath: String) {
        try {
            val externalStorageDirPath = Environment.getExternalStorageDirectory().absolutePath + "/" + directoryName
            val internalStorageDir = Environment.getDataDirectory()
//            val appDBPath = "/data/com.android.dbexporterlibrary/databases/"
            val appDBDirectory = File(internalStorageDir, appDBPath)

            if (!appDBDirectory.exists())
                appDBDirectory.mkdirs()

            //.db file
            val backupDB = File(externalStorageDirPath, dbName)
            val source = FileInputStream(backupDB).channel
            val destination = FileOutputStream("$appDBDirectory/$dbName").channel
            destination!!.transferFrom(source, 0, source!!.size())
            source.close()
            destination.close()

            //-wal file
            val backupWalDB = File(externalStorageDirPath, "$dbName-wal")
            val sourceWal = FileInputStream(backupWalDB).channel
            val destinationWal = FileOutputStream("$appDBDirectory/$dbName-wal").channel
            destinationWal!!.transferFrom(sourceWal, 0, sourceWal!!.size())
            sourceWal.close()
            destinationWal.close()

            //-shm file
            val backupShmDB = File(externalStorageDirPath, "$dbName-shm")
            val sourceShm = FileInputStream(backupShmDB).channel
            val destinationShm = FileOutputStream("$appDBDirectory/$dbName-shm").channel
            destinationShm!!.transferFrom(sourceShm, 0, sourceShm!!.size())
            sourceShm.close()
            destinationShm.close()
            exporterListener.success("DB successfully Imported")
        } catch (e: IOException) {
            e.printStackTrace()
            exporterListener.fail("Couldn't import DB!", e.message.toString())
        }


    }

    /**
     * @param dirName = directory name where tou want to check db exist or not
     *
     */

    fun isBackupExist(dirName: String): Boolean {
        val externalStorageDirPath = Environment.getExternalStorageDirectory().absolutePath + "/" + dirName
        val backupDB = File(externalStorageDirPath, dbName)
        return backupDB.exists()
    }


}