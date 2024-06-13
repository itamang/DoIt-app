package model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
//For independent communication between fragments and activities
public class SharedViewModel extends ViewModel {
    //mutable live data ==can change depending on situation
    private final MutableLiveData<ToDoModel> selectedItem= new MutableLiveData<>();
    private boolean isEdit;

    //pass data to whoever needs it
    public void selectItem(ToDoModel task){
        selectedItem.setValue(task);
    }
    public LiveData<ToDoModel> getSelectedItem(){
        return selectedItem;
    } //observer


    //To check weather the data is for new task or update task
    public void setIsEdit(boolean isEdit){
        this.isEdit=isEdit;
    }
    public boolean getIsEdit(){
        return isEdit;
    }
}
