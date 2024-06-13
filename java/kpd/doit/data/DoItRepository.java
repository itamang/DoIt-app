package kpd.doit.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import kpd.doit.util.TaskRoomDatabase;
import model.Priority;
import model.ToDoModel;

//code organization class
public class DoItRepository {  //helpful one central places for all database coming from room database, obscure place
    private final TaskDao taskDao;
    private final LiveData<List<ToDoModel>> dailyTask;
    private final LiveData<List<ToDoModel>> assignment;
    private final LiveData<List<ToDoModel>> exams;

    public DoItRepository(Application application) { //instanciate the database needs application context
        TaskRoomDatabase database= TaskRoomDatabase.getDatabase(application);
        taskDao = database.taskDao();
        dailyTask = taskDao.getTasks(Priority.DAILY_TASK);
        assignment=taskDao.getTasks(Priority.ASSIGNMENT);
        exams=taskDao.getTasks(Priority.EXAM);
    }

    public LiveData<List<ToDoModel>>getAllTasks(){
        return dailyTask;
    }
    public LiveData<List<ToDoModel>>getAssignment(){return assignment;}
    public LiveData<List<ToDoModel>>getExams(){return exams;}

    public void insert(ToDoModel task){
        TaskRoomDatabase.databaseWriterExecuter.execute(()->taskDao.insertTask(task));      //insertion behind user interface i.e background thread to not bug the application
    }

    public LiveData<ToDoModel> get(long id){return taskDao.get(id);}
    public void update(ToDoModel task){
        TaskRoomDatabase.databaseWriterExecuter.execute(()->taskDao.update(task));

    }
    public void delete(ToDoModel task){
        TaskRoomDatabase.databaseWriterExecuter.execute(()->taskDao.delete(task));
    }
}


