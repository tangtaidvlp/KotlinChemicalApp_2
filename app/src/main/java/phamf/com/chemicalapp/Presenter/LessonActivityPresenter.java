package phamf.com.chemicalapp.Presenter;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import io.realm.RealmResults;
import phamf.com.chemicalapp.Abstraction.AbstractClass.Presenter;
import phamf.com.chemicalapp.Abstraction.Interface.ILessonActivity;
import phamf.com.chemicalapp.LessonActivity;
import phamf.com.chemicalapp.LessonMenuActivity;
import phamf.com.chemicalapp.RO_Model.RO_Lesson;

public class LessonActivityPresenter extends Presenter<LessonActivity> implements ILessonActivity.Presenter{

    public LessonActivityPresenter(@NonNull LessonActivity view) {
        super(view);
    }

    private DataLoadListener onDataLoadListener;

    public void setOnDataLoadListener (DataLoadListener onDataLoadListener) {
        this.onDataLoadListener = onDataLoadListener;
    }

    /**
     * @see LessonActivity
     */
    public void loadData () {
        RO_Lesson ro_lesson = new LessonDataGetter(view).getData();
        if (ro_lesson != null)
          onDataLoadListener.OnDataLoadSuccess(ro_lesson.getName(), ro_lesson.getContent());
        else
          Log.e("Null lesson", "LessonActivityPresenter.java in loadData() function");
    }


    /**
     * @see LessonActivity
     */
    public interface DataLoadListener {

        void OnDataLoadSuccess(String title, String content);

    }
}

class LessonDataGetter {
    LessonActivity view;

    public LessonDataGetter(LessonActivity view) {
        this.view = view;
    }

    public RO_Lesson getData () {
        Intent intent = view.getIntent();
        RO_Lesson ro_lesson = intent.getParcelableExtra(LessonMenuActivity.LESSON_NAME);
        return ro_lesson;
    }
}

