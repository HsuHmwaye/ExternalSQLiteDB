package onthego.com.externalsqlitedb;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private TextView txtMsg;
    private Button btnSend,btnRestore,btnShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        this.listView = (ListView) findViewById(R.id.listView);
        this.txtMsg = (TextView) findViewById(R.id.txtMsg);
        this.btnSend = (Button) findViewById(R.id.btnSend);
        this.btnRestore = (Button) findViewById(R.id.btnRestore);
        this.btnShow = (Button) findViewById(R.id.btnShow);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);

        databaseAccess.open();

     //   List<String> quotes = databaseAccess.getQuotes();
        List<ItemInfo>  ItemList = databaseAccess.getItemList();
        databaseAccess.close();


        ArrayAdapter<ItemInfo> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ItemList);
        this.listView.setAdapter(adapter);
      //  String aa = getDatabasePath("OnTheGoUniDatabase.db").toString();
       txtMsg.setText("Item Count :" + ItemList.size());

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    send();
                    Toast.makeText(MainActivity.this, "DB Exported to Download folder!", Toast.LENGTH_LONG).show();
                    //  this.exportDB();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btnRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                exportDB();


            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ShowList();

            }
        });

        databaseAccess.open();

        //quotes = databaseAccess.getQuotes();
        databaseAccess.close();


        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ItemList);
        this.listView.setAdapter(adapter);

    }

    private void ShowList() {



    }

    private void exportDB(){

        File sd = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File data = Environment.getDataDirectory();
        FileChannel source=null;
        FileChannel destination=null;
        String currentDBPath = "OnTheGoUniDatabase.db";
        String backupDBPath = "data/"+ "onthego.com.externalsqlitedb" +"/databases/"+"OnTheGoUniDatabase.db";
        File currentDB = new File(sd, currentDBPath);
        File backupDB = new File(data, backupDBPath);
        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
            Toast.makeText(this, "DB Exported!", Toast.LENGTH_LONG).show();



        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void send() throws IOException {

        try {


            File sd = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            if (sd.canWrite()) {
                DBHelper mydb = new DBHelper(this);
                String currentDBPath =getDatabasePath(mydb.getDatabaseName()).toString();
                String backupDBPath = "OtgDB_backup.db";
                File currentDB = new File(currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = null;
                    try {
                        src = new FileInputStream(currentDB).getChannel();
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    }
                    FileChannel dst = null;
                    try {
                        dst = new FileOutputStream(backupDB).getChannel();
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    }
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}