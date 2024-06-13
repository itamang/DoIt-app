package kpd.doit;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Context;
import android.content.Intent;


import android.graphics.Color;

import android.os.Bundle;

import android.util.Log;
import android.view.View;

import android.widget.Toast;


import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;


import java.util.ArrayList;
import java.util.Calendar;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import Adapter.RecyclerVIewAdapter;
import Adapter.onTodoClickListener;

import model.TaskViewModel;
import model.ToDoModel;


public class MainActivity extends AppCompatActivity{
    CompactCalendarView compactCalendarView;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM-YYYY", Locale.getDefault());
    private TaskViewModel taskViewModel;
    private RecyclerView recyclerView;
    private RecyclerVIewAdapter recyclerVIewAdapter;

    private onTodoClickListener listener=new onTodoClickListener() {
        @Override
        public void onTodoClick(ToDoModel task) {
            Context context=getApplicationContext();
            Toast.makeText(context, "Update in Todo Page", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onTodoCheckBoxClick(ToDoModel task) {
            Context context=getApplicationContext();
            Toast.makeText(context, "Update in Todo Page", Toast.LENGTH_SHORT).show();


        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(false);
        actionbar.setTitle(null);
        taskViewModel = new ViewModelProvider.AndroidViewModelFactory(
                this.getApplication()).create(TaskViewModel.class);
        compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        recyclerView=(RecyclerView)findViewById(R.id.eventRecyclerVIew);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager((new LinearLayoutManager(this)));

        compactCalendarView.setUseThreeLetterAbbreviation(true);
        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);

        getHighlightedEvents();
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> events = compactCalendarView.getEvents(dateClicked);
                Log.d("test", "Month was scrolled to: " + events);
                List<ToDoModel> toDoModels=new ArrayList<>();
                for (int i = 0; i < events.size(); i++){
                    ToDoModel task = (ToDoModel) events.get(i).getData();
                    toDoModels.add(task);
                }
                recyclerVIewAdapter=new RecyclerVIewAdapter(toDoModels,listener);
                recyclerView.setAdapter(recyclerVIewAdapter);

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {

                actionbar.setTitle(dateFormatMonth.format(firstDayOfNewMonth));
                Log.d("test", "Month was scrolled to: " + firstDayOfNewMonth);
            }
        });




    }


    public void toTasks(View view) {
        startActivity(new Intent(MainActivity.this, TabActivity.class));
    }

    public void getHighlightedEvents(){
        taskViewModel.getAllTasks().observe(this, toDoModels -> {
            for (int i = 0; i < toDoModels.size(); i++) {

                Long date = toDoModels.get(i).getDate().getTime();
                Event ev5 = new Event(Color.YELLOW, date,toDoModels.get(i));
                compactCalendarView.addEvent(ev5);
                compactCalendarView.getEvents(toDoModels.get(i).getDate());

            }


        });
        taskViewModel.getAssignment().observe(this, toDoModels -> {
            for (int i = 0; i < toDoModels.size(); i++) {

                Long date = toDoModels.get(i).getDate().getTime();

                Event ev5 = new Event(Color.BLUE, date,toDoModels.get(i));
                compactCalendarView.addEvent(ev5);
                compactCalendarView.getEvents(toDoModels.get(i).getDate());

            }


        });

        taskViewModel.getExams().observe(this, toDoModels -> {
            for (int i = 0; i < toDoModels.size(); i++) {

                Long date = toDoModels.get(i).getDate().getTime();
                Event ev5 = new Event(Color.RED, date,toDoModels.get(i));
                compactCalendarView.addEvent(ev5);
                compactCalendarView.getEvents(toDoModels.get(i).getDate());
            }


        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        compactCalendarView.removeAllEvents();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getHighlightedEvents();


    }
}
