package phamf.com.chemicalapp.Abstraction.Interface;

import android.app.Activity;

import phamf.com.chemicalapp.Presenter.MainActivityPresenter;
import phamf.com.chemicalapp.RO_Model.RO_ChemicalEquation;

/**
 * @see phamf.com.chemicalapp.MainActivity
 */
public interface IMainActivity {

    interface View {

        void createNecessaryInfo();

        void addEvent ();

        void loadAnim ();

        void setFont ();

        void setTheme();

        void setEdt_SearchAdvanceFunctions ();

        void hideSoftKeyboard(Activity activity);

        void showSoftKeyboard ();
    }


    interface Presenter {

        void loadTheme ();

        void saveTheme();

        void setThemeDefaut();

        void loadData ();

        void turnOnNightMode ();

        void turnOffNightMode ();

        long getDataVersion ();

        void saveDataVersion (long version);

        void setOnThemeChangeListener (OnThemeChangeListener theme);

        /** Update recent searching chemical equation list into database **/
        void pushCachingDataToDB ();

        /** Bring this Chemical Equation to top of recent learning lesson list in realm database  **/
        void bringToTop(RO_ChemicalEquation ro_ce);

        void checkUpdateStatus ();
    }
}
