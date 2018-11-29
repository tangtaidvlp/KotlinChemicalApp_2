package phamf.com.chemicalapp.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.Collection;

import phamf.com.chemicalapp.Fragment.LessonPartFragment;

import static phamf.com.chemicalapp.CustomView.LessonViewCreator.ViewCreator.BIG_TITLE;
import static phamf.com.chemicalapp.CustomView.LessonViewCreator.ViewCreator.COMPONENT_DEVIDER;

public class ViewPager_Lesson_Adapter extends FragmentPagerAdapter{
    private ArrayList <String> list;

    public ViewPager_Lesson_Adapter(android.support.v4.app.FragmentManager fm) {
        super(fm);
        list = new ArrayList<>();
    }

    public void setData (Collection<String> list) {
        this.list = (ArrayList<String>) list;
        notifyDataSetChanged();
    }

    public void addData (String... newContent) {
        for (String part : newContent) {
            list.add(part);
        }
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return LessonPartFragment.newInstance(list.get(position));
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public ArrayList<String> getTitles () {
        ArrayList<String> titles = new ArrayList<>();

        for (String part : list) {
            int title_start_pos = part.indexOf(BIG_TITLE) + 22;
            int title_end_pos = part.indexOf(COMPONENT_DEVIDER);
            String title = part.substring(title_start_pos, title_end_pos);
            titles.add(title);
        }

        return titles;
    }
}
