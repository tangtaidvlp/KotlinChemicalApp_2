package phamf.com.chemicalapp.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import phamf.com.chemicalapp.LessonActivity;
import phamf.com.chemicalapp.Model.SupportModel.MVVM_LessonDataGetter;
import phamf.com.chemicalapp.RO_Model.RO_Lesson;

/**
 * @see LessonActivity
 */

public class MVVM_LessonActivityPresenter extends AndroidViewModel {

    public MutableLiveData<RO_Lesson> ldt_ro_lesson = new MutableLiveData<>();

    public MVVM_LessonActivityPresenter(@NonNull Application view) {
        super(view);
    }

    public void loadData (@NonNull LessonActivity activity) {
        RO_Lesson ro_lesson = new MVVM_LessonDataGetter(activity).getData();
        if (ro_lesson != null)
            ldt_ro_lesson.setValue(ro_lesson);
        else
            Log.e("Null lesson", "LessonActivityPresenter.java in loadData() function");
    }


}

