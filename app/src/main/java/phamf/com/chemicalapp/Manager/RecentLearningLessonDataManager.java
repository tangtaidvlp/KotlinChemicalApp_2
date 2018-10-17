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


    public RecentLearningLessonDataManager (OfflineDatabaseManager offlineDatabaseManager) {
        this.offlineDB_manager = offlineDatabaseManager;
        recent_learningLessons = offlineDatabaseManager.readOneOf(Recent_LearningLessons.class);
        recent_lessons = recent_learningLessons.getRecent_learning_lessons();
    }

    public void bringToTop (RO_Lesson lesson) {
        Log.e("Bring to top", "");
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
        Log.e("Update", "");
        offlineDB_manager.addOrUpdateDataOf(Recent_LearningLessons.class, this.recent_learningLessons);
    }
}
