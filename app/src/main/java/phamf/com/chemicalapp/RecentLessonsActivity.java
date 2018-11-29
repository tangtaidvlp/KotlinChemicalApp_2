package phamf.com.chemicalapp;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import phamf.com.chemicalapp.Abstraction.AbstractClass.RCV_Menu_Adapter;
import phamf.com.chemicalapp.Abstraction.Interface.IRecentLessonActivity;
import phamf.com.chemicalapp.Adapter.Lesson_Menu_Adapter;
import phamf.com.chemicalapp.Manager.AppThemeManager;
import phamf.com.chemicalapp.Manager.RecentSearching_CE_Data_Manager;
import phamf.com.chemicalapp.Presenter.RecentLessonActivityPresenter;
import phamf.com.chemicalapp.RO_Model.RO_Lesson;
import phamf.com.chemicalapp.ViewModel.MVVM_RecentLessonActivityPresenter;


/**
 * Presenter
 * @see RecentLessonActivityPresenter
 */

public class RecentLessonsActivity extends FullScreenActivity {


    @BindView(R.id.rcv_recent_lesson_menu) RecyclerView rcv_recent_lesson_menu;
    Lesson_Menu_Adapter rcv_recent_lesson_menu_adapter;

    @BindView(R.id.txt_recent_lesson_title) TextView txt_title;

    @BindView(R.id.parent_recent_learning_lesson_activity) ConstraintLayout base_view;

    @BindView(R.id.bg_night_mode_recent_learning_lesson) TextView bg_night_mode;

    @BindView(R.id.btn_recent_lesson_back) Button btn_back;

    MVVM_RecentLessonActivityPresenter viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_learning_lesson);

        ButterKnife.bind(this);

        setUpViewModel();

        setTheme();

        addControl();

        addEvent();

        viewModel.loadData();

    }

    @Override
    protected void onPause() {
        super.onPause();
        viewModel.pushCachingData();
    }

    private void setUpViewModel() {
        viewModel = ViewModelProviders.of(this).get(MVVM_RecentLessonActivityPresenter.class);
        viewModel.ldt_recent_lessons.observe(this, recent_lessons -> {
            rcv_recent_lesson_menu_adapter.setData(recent_lessons);
        });
    }

    public void addControl() {
        rcv_recent_lesson_menu_adapter = new Lesson_Menu_Adapter(this);
        rcv_recent_lesson_menu_adapter.adaptFor(rcv_recent_lesson_menu);

        if (AppThemeManager.isOnNightMode) {
            bg_night_mode.setVisibility(View.VISIBLE);
        } else {
            bg_night_mode.setVisibility(View.INVISIBLE);
        }
    }

    public void addEvent() {
        rcv_recent_lesson_menu_adapter.setOnItemClickListener(item -> {
            Intent intent = new Intent(RecentLessonsActivity.this, LessonActivity.class);
            intent.putExtra(LessonMenuActivity.LESSON_NAME, item);
            startActivity(intent);
            viewModel.bringToTop(item);
        });

        btn_back.setOnClickListener(v -> finish());
    }

    public void setTheme () {
        if (AppThemeManager.isCustomingTheme) {
            if (AppThemeManager.isUsingColorBackground)
                base_view.setBackgroundColor(AppThemeManager.getBackgroundColor());
            else
                base_view.setBackground(AppThemeManager.getBackgroundDrawable());
        }
    }

}
