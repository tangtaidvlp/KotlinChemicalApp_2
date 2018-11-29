package phamf.com.chemicalapp.Model.SupportModel;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import phamf.com.chemicalapp.Manager.AppThemeManager;

import static android.content.Context.MODE_PRIVATE;

public class ThemeUpdater {

    Application application;

    public ThemeUpdater(Application application) {
        this.application = application;
    }

    public void saveTheme() {
        SharedPreferences sharedPreferences = application.getSharedPreferences(AppThemeManager.APP_THEME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Toast.makeText(application, AppThemeManager.isOnNightMode + "", Toast.LENGTH_SHORT).show();
        editor.putBoolean(AppThemeManager.IS_ON_NIGHT_MODE, AppThemeManager.isOnNightMode);

        if (AppThemeManager.isCustomingTheme) {

            editor.putInt(AppThemeManager.BACKGROUND_COLOR, AppThemeManager.backgroundColor);
            editor.putInt(AppThemeManager.BACKGROUND_DRAWABLE, AppThemeManager.backgroundDrawable);
            editor.putInt(AppThemeManager.TEXT_COLOR, AppThemeManager.textColor);
            editor.putInt(AppThemeManager.WIDGET_COLOR, AppThemeManager.widgetColor);
            editor.putBoolean(AppThemeManager.IS_USING_AVAILABLE_THEMES, AppThemeManager.isUsingAvailableThemes);
            editor.putBoolean(AppThemeManager.IS_CUSTOMING, AppThemeManager.isCustomingTheme);

        } else {
            Log.e("Theme doesn't changes", "!!!");
        }

        editor.apply();
    }

    /**
     * @return true if Theme has a change, else false
     */
    public boolean loadTheme () {

        SharedPreferences sharedPreferences = application.getSharedPreferences(AppThemeManager.APP_THEME, MODE_PRIVATE);

        AppThemeManager.isCustomingTheme = sharedPreferences.getBoolean(AppThemeManager.IS_CUSTOMING, false);
        AppThemeManager.isUsingAvailableThemes = sharedPreferences.getBoolean(AppThemeManager.IS_USING_AVAILABLE_THEMES, false);
        AppThemeManager.isOnNightMode = sharedPreferences.getBoolean(AppThemeManager.IS_ON_NIGHT_MODE, false);
        Toast.makeText(application, AppThemeManager.isOnNightMode + "", Toast.LENGTH_SHORT).show();

        if (AppThemeManager.isCustomingTheme) {
            AppThemeManager.isUsingColorBackground = sharedPreferences.getBoolean(AppThemeManager.IS_USING_COLOR_BACKGROUND, true);
            AppThemeManager.widgetColor = sharedPreferences.getInt(AppThemeManager.WIDGET_COLOR, 0);
            AppThemeManager.textColor = sharedPreferences.getInt(AppThemeManager.TEXT_COLOR, 0);
            AppThemeManager.backgroundColor= sharedPreferences.getInt(AppThemeManager.BACKGROUND_COLOR, 0);
            AppThemeManager.backgroundDrawable= sharedPreferences.getInt(AppThemeManager.BACKGROUND_DRAWABLE, 0);
            return true;
        } else if (AppThemeManager.isUsingAvailableThemes) {
            AppThemeManager.setTheme(sharedPreferences.getInt(AppThemeManager.CURRENT_THEME, 0));
            return true;
        }

        return false;
    }

    public void setThemeDefault() {
        AppThemeManager.setTheme(AppThemeManager.NORMAL_THEME);
    }
}
