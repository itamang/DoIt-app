package kpd.doit.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;

import model.Priority;
import model.ToDoModel;

//Dao=data access object for invoking insert data into database, delete, update operation
@Dao
public interface TaskDao {
    @Insert
    void insertTask(ToDoModel task);

    @Query("DELETE FROM task_table")
    void deleteAll();

    //flexibility of live data to listen to event when data changes
    @Query("SELECT * FROM task_table  WHERE task_table.priority==:priority")
    LiveData<List<ToDoModel>>getTasks(Priority priority);

    //one task by passing id
    @Query("SELECT*FROM task_table WHERE task_table.task_id==:id")//id passed from below code, Compares the two Id to retrive or delete
    LiveData<ToDoModel> get(long id);
    @Update
    void update(ToDoModel task);
    @Delete
    void delete (ToDoModel task);


}


