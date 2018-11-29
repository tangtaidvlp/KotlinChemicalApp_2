package phamf.com.chemicalapp.Model.SupportModel;

import android.content.Intent;

import phamf.com.chemicalapp.LessonActivity;
import phamf.com.chemicalapp.LessonMenuActivity;
import phamf.com.chemicalapp.RO_Model.RO_Lesson;

public class MVVM_LessonDataGetter {
    LessonActivity view;

    public MVVM_LessonDataGetter(LessonActivity view) {
        this.view = view;
    }

    public RO_Lesson getData () {
        Intent intent = view.getIntent();
        RO_Lesson ro_lesson = intent.getParcelableExtra(LessonMenuActivity.LESSON_NAME);
        return ro_lesson;
    }
}
