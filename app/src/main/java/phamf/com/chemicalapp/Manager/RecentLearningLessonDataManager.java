package phamf.com.chemicalapp.Manager;

import android.util.Log;

import java.util.ArrayList;

import io.realm.RealmList;
import phamf.com.chemicalapp.Database.OfflineDatabaseManager;
import phamf.com.chemicalapp.RO_Model.RO_Lesson;
import phamf.com.chemicalapp.RO_Model.Recent_LearningLessons;

public class RecentLearningLessonDataManager {

    private Recent_LearningLessons recent_learningLessons;

    private RealmList<RO_Lesson> recent_lessons;

    private OfflineDatabaseManager offlineDB_manager;

    private final int MAX_RECENT_LEARNING_LESSON_COUNT = 12;


    public RecentLearningLessonDataManager (OfflineDatabaseManager offline_DBManager) {
        this.offlineDB_manager = offline_DBManager;


        // At the first time, check whether or not an object in realm db storing
        // a recent_learning_lessons object
        // If there's no object, create new one
        // If true, do nothing
        if (offline_DBManager.readOneOf(Recent_LearningLessons.class) == null) {
            Recent_LearningLessons recent_learningLessons = new Recent_LearningLessons();
            offline_DBManager.addOrUpdateDataOf(Recent_LearningLessons.class, recent_learningLessons);
        }
    }

    // This function works both data getter and recent learning lessons creator
    public void getData (OnGetDataSuccess onGetDataSuccess) {

        recent_learningLessons = offlineDB_manager.readAsyncOneOf(Recent_LearningLessons.class,
                                recent_learningLessons ->
                                {
                                    recent_lessons = recent_learningLessons.getRecent_learning_lessons();
                                    ArrayList<RO_Lesson> data = new ArrayList<>();
                                    data.addAll(recent_lessons);
                                    if (onGetDataSuccess != null) onGetDataSuccess.onLoadSuccess(data);
                                    else Log.e("Null onGetDataSuccess", "Is this in your intent ? (RecentLearningLessonDataManager.java/getData()");
                                });
    }

    public void bringToTop (RO_Lesson lesson) {
        offlineDB_manager.beginTransaction();

        if (recent_lessons.size() < MAX_RECENT_LEARNING_LESSON_COUNT ) {

            if (recent_lessons.contains(lesson)) {

                if (!recent_lessons.get(0).equals(lesson)) {
                    recent_lessons.remove(lesson);
                    recent_lessons.add(0, lesson);
                }

            } else {
                recent_lessons.add(0, lesson);
            }

        } else if (recent_lessons.size() == MAX_RECENT_LEARNING_LESSON_COUNT) {
            if (recent_lessons.contains(lesson)) {

                if (!recent_lessons.get(0).equals(lesson)) {
                    recent_lessons.remove(lesson);
                    recent_lessons.add(0, lesson);
                }

            } else {
                recent_lessons.remove(recent_lessons.size() - 1);
                recent_lessons.add(0, lesson);
            }
        }

        offlineDB_manager.commitTransaction();
    }

    public void updateDB() {
        offlineDB_manager.addOrUpdateDataOf(Recent_LearningLessons.class, this.recent_learningLessons);
    }

    public interface OnGetDataSuccess {
        void onLoadSuccess (ArrayList<RO_Lesson> recent_Ces);
    }
}
