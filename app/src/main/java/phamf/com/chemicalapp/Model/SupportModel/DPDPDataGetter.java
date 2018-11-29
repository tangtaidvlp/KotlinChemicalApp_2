package phamf.com.chemicalapp.Model.SupportModel;

import android.app.Activity;
import android.content.Intent;

import phamf.com.chemicalapp.DPDPMenuActivity;
import phamf.com.chemicalapp.RO_Model.RO_DPDP;

public class DPDPDataGetter {
    Activity activity;

    public DPDPDataGetter(Activity activity) {
        this.activity = activity;
    }

    public RO_DPDP getData () {
        Intent intent = activity.getIntent();
        RO_DPDP dpdp = intent.getParcelableExtra(DPDPMenuActivity.DPDP_NAME);
        return dpdp;
    }
}
