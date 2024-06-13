package Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import Adapter.RecyclerVIewAdapter;
import Adapter.onTodoClickListener;
import kpd.doit.R;
import model.Priority;
import model.SharedViewModel;
import model.TaskViewModel;
import model.ToDoModel;

/**
 * A simple {@link Fragment} subclass.

 * create an instance of this fragment.
 */
public class dailyTask extends Fragment implements onTodoClickListener{
    private TaskViewModel taskViewModel;
    private RecyclerView recyclerView;
    private RecyclerVIewAdapter recyclerVIewAdapter;
    private SharedViewModel sharedViewModel;
    private onTodoClickListener listener;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_daily_task, container, false);

        recyclerView= view.findViewById(R.id.tasksRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager((new LinearLayoutManager(getContext())));

        taskViewModel=new ViewModelProvider.AndroidViewModelFactory(
                dailyTask.this.getActivity().getApplication()).create(TaskViewModel.class);

        taskViewModel.getAllTasks().observe(getViewLifecycleOwner(), toDoModels -> {
            recyclerVIewAdapter=new RecyclerVIewAdapter(toDoModels,this);
            recyclerView.setAdapter(recyclerVIewAdapter);



        });



            return view;
    }

    @Override
    public void onTodoClick(ToDoModel task) {

        sharedViewModel.selectItem(task);
        sharedViewModel.setIsEdit(true);

       // Log.d("Click","onTODOCLick"+task.getTask());




    }
    @Override
    public void onTodoCheckBoxClick(ToDoModel task) {
        Log.d("Click","onTODOCLick"+task.getTask());
        TaskViewModel.delete(task);
        recyclerVIewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedViewModel= new ViewModelProvider(requireActivity()).get(SharedViewModel.class);


    }
}