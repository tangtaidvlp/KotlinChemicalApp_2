package phamf.com.chemicalapp.ViewModel;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;

import phamf.com.chemicalapp.Abstraction.Interface.IMainActivity;
import phamf.com.chemicalapp.Abstraction.Interface.OnThemeChangeListener;
import phamf.com.chemicalapp.Database.OfflineDatabaseManager;
import phamf.com.chemicalapp.Database.UpdateDatabaseManager;
import phamf.com.chemicalapp.MainActivity;
import phamf.com.chemicalapp.Manager.AppThemeManager;
import phamf.com.chemicalapp.Manager.RecentSearching_CE_Data_Manager;
import phamf.com.chemicalapp.Model.SupportModel.ThemeUpdater;
import phamf.com.chemicalapp.RO_Model.RO_ChemicalEquation;

import static android.content.Context.MODE_PRIVATE;

/**
 * @see MainActivity
 */
public class MVVM_MainActivityPresenter extends AndroidViewModel implements IMainActivity.Presenter {


    private UpdateDatabaseManager updateDB_Manager;

    private OfflineDatabaseManager offlineDB_manager;

    public MutableLiveData<ArrayList<RO_ChemicalEquation>> ldt_ro_chemicalEquation = new MutableLiveData<>();

    private OnThemeChangeListener onThemeChangeListener;

    private RecentSearching_CE_Data_Manager recentSearching_ce_data_manager;

    private MVVM_MainActivityPresenter.OnUpdateCheckedListener onUpdateChecked;

    private ThemeUpdater themeUpdater;

    public static final String APP_INFO = "app_info";

    public static final String DATABASE_VERSION = "db_vers";

    public static final String HAS_DEFAULT_VALUE = "has_def_val";

    public MVVM_MainActivityPresenter(@NonNull Application application) {
        super(application);

        updateDB_Manager = new UpdateDatabaseManager(application, getDataVersion());

        offlineDB_manager = new OfflineDatabaseManager(application);

        themeUpdater = new ThemeUpdater(application);

    }


    public void update (MVVM_MainActivityPresenter.OnUpdateSuccess onUpdateSuccess) {
        updateDB_Manager.setOnASectionUpdated((version, isLastVersion) -> {
            if (isLastVersion) {
                onUpdateSuccess.onUpdateSuccess();
            } else {
                saveDataVersion(version);
            }
        });

        updateDB_Manager.update();
    }

// Else

    public void loadTheme () {
        boolean isThemeChanging = themeUpdater.loadTheme();
        if (isThemeChanging) onThemeChangeListener.onThemeChange();

    }

    public long getDataVersion () {
        SharedPreferences databaseVersion = getApplication().getSharedPreferences(APP_INFO, MODE_PRIVATE);
        return databaseVersion.getLong(DATABASE_VERSION, 0);
    }

    public void saveDataVersion (long version) {
        SharedPreferences databaseVersion = getApplication().getSharedPreferences(APP_INFO, MODE_PRIVATE);
        SharedPreferences.Editor editor = databaseVersion.edit();
        editor.putLong(DATABASE_VERSION, version);
        editor.apply();
    }

    public void saveTheme() {
       themeUpdater.saveTheme();
    }


    public void loadData () {
        recentSearching_ce_data_manager = new RecentSearching_CE_Data_Manager(offlineDB_manager);
        recentSearching_ce_data_manager.getData(
                ces -> ldt_ro_chemicalEquation.setValue(ces));
    }

    public void turnOnNightMode () {
        AppThemeManager.isOnNightMode = true;
    }

    public void turnOffNightMode () {
        AppThemeManager.isOnNightMode = false;
    }

    public void setThemeDefaut() {
        themeUpdater.setThemeDefault();
        onThemeChangeListener.onThemeChange();
    }


    public void setOnThemeChangeListener (OnThemeChangeListener theme) {
        this.onThemeChangeListener = theme;
    }

    public void setOnUpdateStatusCheckedListener (MVVM_MainActivityPresenter.OnUpdateCheckedListener onUpdateChecked) {
        this.onUpdateChecked = onUpdateChecked;
    }

    /** Update recent searching chemical equation list into database **/
    public void pushCachingDataToDB () {
        recentSearching_ce_data_manager.updateDB();
    }

    /** Bring this Chemical Equation to top of recent learning lesson list in realm database  **/
    public void bringToTop(RO_ChemicalEquation ro_ce) {
        recentSearching_ce_data_manager.bringToTop(ro_ce);
    }

    @Override
    public void checkUpdateStatus() {
        updateDB_Manager.getLastestVersionUpdate(version -> {
            // if firebase database version is bigger than app version, there's at least
            // one update version available
            Log.e("App version", getDataVersion() + "");
            onUpdateChecked.onStatusChecked(version > getDataVersion(), version);
        });
    }


    /**
     * @see MainActivity
     * which implement this listeners to get Data
     */
//
//    public interface DataLoadListener {
//
//        void onDataLoadSuccess(ArrayList<RO_ChemicalEquation> ro_chemicalEquations);
//
//    }

    public interface OnUpdateCheckedListener {

        void onStatusChecked(boolean isAvailable, long lasted_version);

    }

    public interface OnUpdateSuccess {

        void onUpdateSuccess();

    }

    class RequireOverlayPermissionManager {
        Activity activity;

        Context context;

        RequireOverlayPermissionManager (Activity activity) {
            this.activity = activity;
            this.context = activity.getBaseContext();
        }

        public void requirePermission (int request_code) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(context)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + context.getPackageName()));
                activity.startActivityForResult(intent, request_code);
            }
        }

    }

}

