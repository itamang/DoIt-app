package Fragment;


import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.Date;

import kpd.doit.AlarmManager.AlarmReceiver;
import kpd.doit.R;
import kpd.doit.util.Utils;
import model.Priority;
import model.SharedViewModel;
import model.TaskViewModel;
import model.ToDoModel;


public class bottomSheetFragment extends BottomSheetDialogFragment {
    private EditText enterTodo;
    private ImageButton calenderButton;
    private TimePicker timePicker;
    private ImageButton priorityButton;
    private ImageButton timeButton;
    private ImageButton saveButton;
    private RadioGroup priorityRadioGroup;
    private RadioButton selectedRadioButton;
    private int selectedButtonId;
    private CalendarView calendarView;
    private Group calendarGroup;
    private Date dueDate;
    Calendar calender= Calendar.getInstance();
    private SharedViewModel sharedViewModel;
    private boolean isEdit;
    private Priority priority;
    private  Date dueTime;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    public bottomSheetFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createNotificationChannel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_bottom_sheet,container,false);
        calendarGroup = view.findViewById(R.id.calendar_group);
        calendarView=view.findViewById(R.id.calendar_view);
        enterTodo=view.findViewById(R.id.addNewTask);
        saveButton=view.findViewById(R.id.save_todo_button);
        calenderButton=view.findViewById(R.id.today_calendar_button);
        timeButton=view.findViewById(R.id.alarm_button);
        timePicker=view.findViewById(R.id.timePicker);
        priorityButton=view.findViewById(R.id.priority_todo_button);
        priorityRadioGroup=view.findViewById(R.id.radioGroup_priority);



        return view;

    }
   //when all the views is created from above method
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //instantiate sharedViewModel and must happen when all the view is created
        sharedViewModel=new ViewModelProvider(requireActivity()).get(SharedViewModel.class);


        calenderButton.setOnClickListener(view12 -> {
            calendarGroup.setVisibility(
                    calendarGroup.getVisibility()== View.GONE? View.VISIBLE : View.GONE );
            Utils.hideSoftKeyboard(view12);
            timePicker.setVisibility(timePicker.getVisibility()==View.VISIBLE? View.GONE : View.GONE);
            priorityRadioGroup.setVisibility(priorityRadioGroup.getVisibility()==View.VISIBLE?View.GONE : View.GONE);
        });
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                calender.clear();
                calender.set(year,month,dayOfMonth);
                dueDate=calender.getTime();


            }
        });
        priorityButton.setOnClickListener(view13 -> {
            Utils.hideSoftKeyboard(view13);
            priorityRadioGroup.setVisibility(
                    priorityRadioGroup.getVisibility()==View.GONE ? View.VISIBLE : View.GONE);
            priorityRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                    if (priorityRadioGroup.getVisibility()==View.VISIBLE){
                        selectedButtonId=checkedId;
                        selectedRadioButton =view.findViewById(selectedButtonId);
                        if (selectedRadioButton.getId()==R.id.radioButton_dailyDask){
                            priority = Priority.DAILY_TASK;
                        }else if (selectedRadioButton.getId()==R.id.radioButton_assignment){
                        priority=Priority.ASSIGNMENT;
                        }else if(selectedRadioButton.getId()==R.id.radioButton_exam){
                            priority=Priority.EXAM;

                        }else{
                            priority=Priority.DAILY_TASK;
                        }

                    }else{
                        priority=Priority.DAILY_TASK;
                    }

                }
            });
        });
        timeButton.setOnClickListener(view1 -> {
            Utils.hideSoftKeyboard(view1);
            calendarGroup.setVisibility(calendarGroup.getVisibility()==View.VISIBLE? View.GONE : View.GONE);
            timePicker.setVisibility(timePicker.getVisibility()==View.GONE?View.VISIBLE:View.GONE);
            priorityRadioGroup.setVisibility(priorityRadioGroup.getVisibility()==View.VISIBLE?View.GONE : View.GONE);

        });
         timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
             @Override
             public void onTimeChanged(TimePicker timePicker, int hour, int minute) {
                 Toast.makeText(getActivity().getApplicationContext(),hour+" " +minute,Toast.LENGTH_SHORT).show();
                 calender.set(Calendar.HOUR_OF_DAY,hour);
                 calender.set(Calendar.MINUTE,minute);
                 dueTime=calender.getTime();
             }
         });


        //For save button
        saveButton.setOnClickListener(view1 -> {
            String task=enterTodo.getText().toString().trim();
            if (!TextUtils.isEmpty(task) && dueDate!=null && priority !=null &&dueTime!=null){
                ToDoModel myTask=new ToDoModel(task,priority, dueDate
                        ,dueTime,false,Calendar.getInstance().getTime());

                if(isEdit){
                    ToDoModel updateTask = sharedViewModel.getSelectedItem().getValue();
                    updateTask.setTask(task);
                    updateTask.setDateCreated(Calendar.getInstance().getTime());
                    updateTask.setPriority(priority);
                    updateTask.setDate(dueDate);
                    updateTask.setTime(dueTime);
                    TaskViewModel.update(updateTask);
                    setAlarm();

                    sharedViewModel.setIsEdit(false);


                }
                else {
                    TaskViewModel.insert(myTask);
                    setAlarm();

                }
                enterTodo.setText("");
                if (this.isVisible()){
                    this.dismiss();
                }


            }else {
                //makes the task empty
                Snackbar.make(saveButton, R.string.emptyField,Snackbar.LENGTH_LONG).show();
            }


        });
    }

    @Override  //WHen the view is refreshed again
    public void onResume() {
        super.onResume();
        if (sharedViewModel.getSelectedItem().getValue() != null) {
            isEdit = sharedViewModel.getIsEdit();   //to differentiate the given task is for editing
            ToDoModel task = sharedViewModel.getSelectedItem().getValue();
            enterTodo.setText(task.getTask());


            Log.d("My", "onViewCreated" + task.getTask());

        }
    }
        private void createNotificationChannel() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CharSequence name = "DoItChannel";
                String description = "Channel for AlarmReceiver";
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel channel = new NotificationChannel("DoIt", name, importance);
                channel.setDescription(description);
                // Register the channel with the system; you can't change the importance
                // or other notification behaviors after this
                NotificationManager notificationManager =requireActivity().getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);
            }
        }

        private void setAlarm(){
            alarmManager = (AlarmManager) requireActivity().getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(this.requireActivity(), AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(this.getContext(),0,intent,0);



            long selectedDate=calender.getTimeInMillis();
            long oneDay=AlarmManager.INTERVAL_DAY;  //Converts 1 day into long
            long hour=AlarmManager.INTERVAL_HOUR;
            long reminderTime= selectedDate-oneDay;
            long nextReminder=selectedDate-hour;
            //sets reminder 1 day before
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,reminderTime,AlarmManager.INTERVAL_FIFTEEN_MINUTES,
                    pendingIntent);

            //sets 2nd reminder 1 hour before
            alarmManager.set(AlarmManager.RTC_WAKEUP,nextReminder,pendingIntent);
            Toast.makeText(this.getContext(), "Reminder set Successfully", Toast.LENGTH_SHORT).show();


        }
}