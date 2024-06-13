package Adapter;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;

import java.util.List;

import kpd.doit.R;
import kpd.doit.util.Utils;
import model.Priority;
import model.ToDoModel;

public class RecyclerVIewAdapter extends RecyclerView.Adapter<RecyclerVIewAdapter.ViewHolder> {
    private  List<ToDoModel> taskList;

    private final onTodoClickListener todoClickListener;


    public RecyclerVIewAdapter(List<ToDoModel> taskList, onTodoClickListener onTodoClickListener) {


        this.todoClickListener = onTodoClickListener;

        this.taskList=taskList;

    }


    @NonNull
    @Override
    //Inflate table row attached to recycler view
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task,parent,false);
        return new ViewHolder(view);
    }
    //bind view and object
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ToDoModel task =taskList.get(position);


            String formatted= Utils.formatDate(task.getDate());

            String formatTime=Utils.formatTime(task.getTime());

            holder.task.setText(task.getTask());
            holder.timeChip.setText(formatTime);
            holder.dateChip.setText(formatted);

            ColorStateList colorStateList=new ColorStateList(new int[][]{
                new int[]{-android.R.attr.state_enabled},   //default state of widget
                new int[]{android.R.attr.state_enabled}
        }, new int[]{           //override default state
                android.R.color.darker_gray,   //disabled state
                Utils.priorityColor(task)

        });

        holder.dateChip.setTextColor(Utils.priorityColor(task));
        holder.dateChip.setChipIconTint(colorStateList);
        holder.checkBox.setButtonTintList(colorStateList);
        holder.timeChip.setTextColor(Utils.priorityColor(task));
        holder.timeChip.setChipIconTint(colorStateList);

    }

    @Override
    //important to get count of items for internal mechanism
    public int getItemCount() {

            return taskList.size();
        }

    //holds the items view of item for for recyclerVIew
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public CheckBox checkBox;
        public TextView task;
        public Chip dateChip, timeChip;
        onTodoClickListener onTodoClickListener;

        public ViewHolder(@NonNull View itemView) {  //Constructor
            super(itemView);
            checkBox= itemView.findViewById(R.id.checkbox_todo);
            task= itemView.findViewById(R.id.text_todo);
            dateChip=itemView.findViewById(R.id.date_chip);
            timeChip=itemView.findViewById(R.id.time_chip);
            this.onTodoClickListener=todoClickListener;

            itemView.setOnClickListener(this);
            checkBox.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {  //pass imformation to our click listener
            int id = view.getId();

            ToDoModel currTask=taskList.get(getAbsoluteAdapterPosition());
            if (id== R.id.todo_row_layout){
                //passing adapter position
                onTodoClickListener.onTodoClick(currTask); //anyclassImplementing onToDoClickListener will have the currest task and recycler position
            }else if(id == R.id.checkbox_todo){

                onTodoClickListener.onTodoCheckBoxClick(currTask);



            }
        }
    }
}
