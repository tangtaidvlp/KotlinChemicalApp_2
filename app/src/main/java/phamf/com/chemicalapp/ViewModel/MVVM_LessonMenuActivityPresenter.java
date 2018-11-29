package phamf.com.chemicalapp.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import io.realm.RealmResults;
import phamf.com.chemicalapp.Abstraction.Interface.ILessonMenuActivity;
import phamf.com.chemicalapp.Database.OfflineDatabaseManager;
import phamf.com.chemicalapp.LessonMenuActivity;
import phamf.com.chemicalapp.Manager.RecentLearningLessonDataManager;
import phamf.com.chemicalapp.RO_Model.RO_Chapter;
import phamf.com.chemicalapp.RO_Model.RO_Lesson;

/**
 *@see LessonMenuActivity
 */
public class MVVM_LessonMenuActivityPresenter extends AndroidViewModel implements ILessonMenuActivity.Presenter{

    private OfflineDatabaseManager offlineDB_manager;

    private RecentLearningLessonDataManager recentLearningLessonDataManager;

    public MutableLiveData<RealmResults<RO_Chapter>> l_data = new MutableLiveData<>();

    private RealmResults<RO_Chapter> data;

    Application application;

    public MVVM_LessonMenuActivityPresenter(@NonNull Application application) {
        super(application);
    }


    public void loadData() {

        offlineDB_manager = new OfflineDatabaseManager(application);

        recentLearningLessonDataManager = new RecentLearningLessonDataManager(offlineDB_manager);
        // Call this function to create some data for RecentLearningLessonDataManager class,
        // look inside this method to get more info
        recentLearningLessonDataManager.getData(null);

        data = offlineDB_manager.readAsyncAllDataOf(RO_Chapter.class, ro_chapters ->
                {
                    l_data.setValue(data);
                }
        );

    }

    public void pushCachingDataToDB () {
        recentLearningLessonDataManager.updateDB();
    }

    public void clearAllListenerToDatabase () {
        data.removeAllChangeListeners();
    }

    /** Bring this lesson to top of recent learning lesson list in realm database  **/
    public void bringToTop (RO_Lesson ro_lesson) {
        recentLearningLessonDataManager.bringToTop(ro_lesson);
    }


    /** @see LessonMenuActivity
     */
    public interface DataLoadListener {

        void onDataLoadedSuccess (ArrayList<RO_Chapter> ro_chapters);

    }

}
