package phamf.com.chemicalapp.Manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

public class RequireOverlayPermissionManager {
    Activity activity;

    Context context;

    public RequireOverlayPermissionManager (Activity activity) {
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
