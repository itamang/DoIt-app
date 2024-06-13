package tasksSeperation;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import Fragment.*;

public class viewPageFragmentAdapter extends FragmentStateAdapter {
    private String[] titles=new String[]{"Daily Task","Assignment","Exam"};
    public viewPageFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new dailyTask();
            case 1:
                return new assignment();
            case 2:
                return new Exam();
            default:
                return null;

        }

    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}
