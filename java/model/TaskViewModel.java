package model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;


import kpd.doit.data.DoItRepository;

public class TaskViewModel extends AndroidViewModel {
    public static DoItRepository repository;
    public final LiveData<List<ToDoModel>> dailyTasks; //practice
    public final LiveData<List<ToDoModel>> assignments;
    public final LiveData<List<ToDoModel>> exams;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        repository=new DoItRepository(application);
        dailyTasks=repository.getAllTasks(); //list of tasks from repository
        assignments= repository.getAssignment();
        exams=repository.getExams();
    }
    //mind different meanings for LiveData<List<ToDoModel>>
    public LiveData<List<ToDoModel>> getAllTasks(){return dailyTasks;}
    public LiveData<List<ToDoModel>> getAssignment(){return assignments;}
    public LiveData<List<ToDoModel>> getExams(){return exams;}
    public static void insert(ToDoModel task){repository.insert(task);}
    public LiveData<ToDoModel> get(long id){return repository.get(id);}
    public static void update(ToDoModel task){repository.update(task);}
    public static void delete(ToDoModel task){repository.delete(task);}


}


