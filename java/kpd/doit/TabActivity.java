package kpd.doit;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import Fragment.bottomSheetFragment;
import model.SharedViewModel;
import model.TaskViewModel;
import model.ToDoModel;
import tasksSeperation.viewPageFragmentAdapter;



public class TabActivity extends AppCompatActivity{

    private static final String TAG ="ITEM" ;
    TaskViewModel taskViewModel;
    viewPageFragmentAdapter viewPageFragmentAdapter;
    TabLayout tabLayout;
    ViewPager2 viewPager2 ;
    private String[] titles=new String[]{"Daily Task","Assignment","Exam"};
    private int counter;
    bottomSheetFragment bottomSheetFragment;
    private SharedViewModel sharedViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        getSupportActionBar().hide();
        viewPager2 = findViewById(R.id.viewPager2);
        tabLayout = findViewById(R.id.tabLayout);




        viewPageFragmentAdapter = new viewPageFragmentAdapter(this);
        viewPager2.setAdapter(viewPageFragmentAdapter);


       new TabLayoutMediator(tabLayout, viewPager2, ((tab, position) -> tab.setText(titles[position]))).attach();
       tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
           @Override
           public void onTabSelected(TabLayout.Tab tab) {
               viewPager2.setCurrentItem(tab.getPosition());
           }

           @Override
           public void onTabUnselected(TabLayout.Tab tab) {

           }

           @Override
           public void onTabReselected(TabLayout.Tab tab) {

           }
       });
       //Hosting bottomSheetFragment
        bottomSheetFragment=new bottomSheetFragment();
        ConstraintLayout constraintLayout=findViewById(R.id.bottomSheet);
        BottomSheetBehavior<ConstraintLayout>bottomSheetBehavior =BottomSheetBehavior.from(constraintLayout);
        bottomSheetBehavior.setPeekHeight(BottomSheetBehavior.STATE_HIDDEN);



        taskViewModel=new ViewModelProvider.AndroidViewModelFactory(
                TabActivity.this.getApplication()).create(TaskViewModel.class);
        FloatingActionButton fab=findViewById(R.id.fab);

        //to retrive data from database
        taskViewModel.getAllTasks().observe(this, toDoModels -> {
            for (ToDoModel task: toDoModels){
                Log.d(TAG, "onCreate:" + task.getTaskId());
            }

        });
        //LIve data gives changes in User interface automatically
        fab.setOnClickListener(view -> {


            //TaskViewModel.insert(task);
            showBottomSheetDialog();
            sharedViewModel.setIsEdit(false);


        });
        //For editing and sending data back to bottom sheet
        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        sharedViewModel.getSelectedItem().observe(this,item->{


            showBottomSheetDialog();
        });

    }
    //shows the hidden bottomSheet
    private void showBottomSheetDialog() {
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }
    public void toHome(View view) {
        startActivity(new Intent(TabActivity.this, MainActivity.class));
    }





}
