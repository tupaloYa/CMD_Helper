package yaroslavtupalo.ua.commandshelper.open_helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

/**
 * Created by Yaroslav on 02/29/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DB_PATH = "/data/data/yaroslavtupalo.ua.commandshelper/databases/";
    private static String DB_NAME = "commands.db";
    private static final int SCHEMA = 1; // версия базы данных
    public static final String TABLE ="windows_commands";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_COMMANDS_NAME = "commands_name";
    public static final String COLUMN_COMMANDS_DESCRIPTION = "commands_descriptions";
    public SQLiteDatabase database;
    private Context myContext;

    public DatabaseHelper(Context context){
        super(context,DB_NAME,null,SCHEMA);
        this.myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // Запишите в журнал обновление версии.
        Log.w("TaskDBAdapter", "Upgrading from version" + oldVersion + " to " + newVersion + ", which will be destroy all old data");

        // Обновите существующую базу данных в соответствии
        // с новой версией. Обработка всех предыдущих версий может быть
        // проделана путем сравнения значений oldVersion и newVersion.
        //В простейшем случае удаляется старая таблица и создается новая.
        db.execSQL("DROP TABLE IF IT EXISTS " + TABLE);
        // Создайте новую таблицу.
        onCreate(db);
    }

    public void create_db(){
        InputStream myInput = null;
        OutputStream myOutput = null;
        try {
            File file = new File(DB_PATH + DB_NAME);
            if(!file.exists()){
                this.getReadableDatabase();
                //получаем локальную бд как поток
                myInput = myContext.getAssets().open(DB_NAME);
                // Путь к новой бд
                String outFileName = DB_PATH + DB_NAME;
                // Открываем пустую бд
                myOutput = new FileOutputStream(outFileName);
                // побайтово копируем данные
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0){
                    myOutput.write(buffer, 0, length);
                }
                myOutput.flush();
                myOutput.close();
                myInput.close();
            }
        }catch (IOException ex){

        }
    }

    public void open() throws SQLException{
        String path = DB_PATH + DB_NAME;
        database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close() {
        if (database != null) {
            database.close();
        }
        super.close();
    }
}
