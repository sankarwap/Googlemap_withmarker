package bala.googlemap;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mindmade technologies on 13-06-2017.
 */
public class AppPermissions {
    private AppCompatActivity mActivity;

    public AppPermissions(AppCompatActivity activity) {
        mActivity = activity;
    }

    public AppPermissions(Activity activity) {
    }

    public boolean hasPermission(String permission) {
        return ActivityCompat.checkSelfPermission(mActivity, permission)
                == PackageManager.PERMISSION_GRANTED;
    }

    public boolean hasPermission(String[] permissionsList) {
        for (String permission : permissionsList) {
            if (ActivityCompat.checkSelfPermission(mActivity, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public void requestPermission(String permission, int requestCode) {
        if (ActivityCompat.checkSelfPermission(mActivity, permission)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mActivity, new String[] { permission }, requestCode);
        }
    }

    public void requestPermission(String[] permissionsList, int requestCode) {
        List<String> permissionNeeded = new ArrayList<>();
        for (String permission : permissionsList) {
            if (ActivityCompat.checkSelfPermission(mActivity, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                permissionNeeded.add(permission);
            }
        }
        if (permissionNeeded.size() > 0) {
            ActivityCompat.requestPermissions(mActivity, permissionNeeded.toArray(new String[permissionNeeded.size()]), requestCode);
        }
    }

    @Deprecated
    public boolean hasPermission(Activity activity, String permission) {
        return ActivityCompat.checkSelfPermission(activity, permission)
                == PackageManager.PERMISSION_GRANTED;
    }

    @Deprecated
    public boolean hasPermission(Activity activity, String[] permissionsList) {
        for (String permission : permissionsList) {
            if (ActivityCompat.checkSelfPermission(activity, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Deprecated
    public void requestPermission(Activity activity, String permission, int requestCode) {
        if (ActivityCompat.checkSelfPermission(activity, permission)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[] { permission }, requestCode);
        }
    }

    @Deprecated
    public void requestPermission(Activity activity, String[] permissionsList, int requestCode) {
        List<String> permissionNeeded = new ArrayList<>();
        for (String permission : permissionsList) {
            if (ActivityCompat.checkSelfPermission(activity, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                permissionNeeded.add(permission);
            }
        }
        if (permissionNeeded.size() > 0) {
            ActivityCompat.requestPermissions(activity,
                    permissionNeeded.toArray(new String[permissionNeeded.size()]), requestCode);
        }
    }



    public static final String[] ALL_PERMISSIONS = {
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
    };
    public static final int ALL_REQUEST_CODE = 0;

}
