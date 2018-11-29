package phamf.com.chemicalapp.ViewModel;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import phamf.com.chemicalapp.Abstraction.AbstractClass.Presenter;
import phamf.com.chemicalapp.Abstraction.Interface.IRecentLessonActivity;
import phamf.com.chemicalapp.Database.OfflineDatabaseManager;
import phamf.com.chemicalapp.Manager.RecentLearningLessonDataManager;
import phamf.com.chemicalapp.RO_Model.RO_Lesson;
import phamf.com.chemicalapp.RecentLessonsActivity;

/**
 * @see RecentLessonsActivity
 */
public class MVVM_RecentLessonActivityPresenter extends AndroidViewModel {

    OfflineDatabaseManager offline_DBManager;

    RecentLearningLessonDataManager recentLearningLessonDataManager;

    public MutableLiveData<ArrayList<RO_Lesson>> ldt_recent_lessons = new MutableLiveData<>();

    public MVVM_RecentLessonActivityPresenter(@NonNull Application view) {
        super(view);
        offline_DBManager = new OfflineDatabaseManager(view);
    }

    public void loadData() {
        recentLearningLessonDataManager = new RecentLearningLessonDataManager(offline_DBManager);
        recentLearningLessonDataManager.getData(recent_Ces -> {
            ldt_recent_lessons.setValue(recent_Ces);
        });
    }

    public void bringToTop(RO_Lesson item) {
        recentLearningLessonDataManager.bringToTop(item);
    }

    public void pushCachingData () {
        recentLearningLessonDataManager.updateDB();
    }

}