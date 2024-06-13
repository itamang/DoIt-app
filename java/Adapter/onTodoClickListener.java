package Adapter;

import model.ToDoModel;

public interface onTodoClickListener {   //Interface have method without the body and implemented at a class level

    void onTodoClick(ToDoModel task);
    void onTodoCheckBoxClick(ToDoModel task);
}
