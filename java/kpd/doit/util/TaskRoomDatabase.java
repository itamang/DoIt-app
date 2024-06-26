package kpd.doit.util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kpd.doit.data.TaskDao;
import model.ToDoModel;

@Database(entities={ToDoModel.class},version = 1,exportSchema = false) //most important to create the database
@TypeConverters({Converter.class})
public abstract class TaskRoomDatabase extends RoomDatabase {
    public static final int NUMBER_OF_THREADS=5;
    public static final String DATABASE_NAME="todo_database";
    private static  volatile TaskRoomDatabase INSTANCE;
    public static final ExecutorService databaseWriterExecuter
            = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static final RoomDatabase.Callback sRoomDatabaseCallback=
            new RoomDatabase.Callback(){
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    databaseWriterExecuter.execute(()-> {
                        //invoke, Dao, Write
                        TaskDao taskDao= INSTANCE.taskDao();
                        taskDao.deleteAll(); //clean slate
                        //continue writing to table
                    });
                }
            };
    public static TaskRoomDatabase getDatabase(final Context context){
        if (INSTANCE==null){
            synchronized (TaskRoomDatabase.class){
                if(INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),
                            TaskRoomDatabase.class,DATABASE_NAME).addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }
    public abstract TaskDao taskDao();

}


