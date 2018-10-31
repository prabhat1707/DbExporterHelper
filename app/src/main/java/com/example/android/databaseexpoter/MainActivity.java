package com.example.android.databaseexpoter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.dbexporterlibrary.ExportDbUtil;
import com.android.dbexporterlibrary.ExporterListener;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity implements ExporterListener {
    private ExportDbUtil exportDbUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        exportDbUtil = new ExportDbUtil(this, "sampleDbExporter", "library Test", this);


    }

    @Override
    public void success(@NotNull String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void fail(@NotNull String message, @NotNull String exception) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void exportToCsv(View view) {
        exportDbUtil.exportAllTables("test.csv");
    }

    public void exportSingleTable(View view) {
        exportDbUtil.exportSingleTable("firstTabel","test.csv");
    }

    public void importDb(View view) {
        if (exportDbUtil.isBackupExist("library Test")) {
            exportDbUtil.importDBFile("/data/com.example.android.databaseexpoter/databases/");
        } else {
            Toast.makeText(this, "no Backup", Toast.LENGTH_SHORT).show();

        }
    }

    public void exportDB(View view) {
        exportDbUtil.exportDb("/data/com.example.android.databaseexpoter/databases/");
    }
}
