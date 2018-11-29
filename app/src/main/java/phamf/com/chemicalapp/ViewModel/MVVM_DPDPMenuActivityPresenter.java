package phamf.com.chemicalapp.ViewModel;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import io.realm.RealmResults;
import phamf.com.chemicalapp.Database.OfflineDatabaseManager;
import phamf.com.chemicalapp.RO_Model.RO_DPDP;

import static phamf.com.chemicalapp.Supporter.ROConverter.toRO_DPDPs_ArrayList;

/**
 * @see phamf.com.chemicalapp.DPDPMenuActivity
 */
public class MVVM_DPDPMenuActivityPresenter extends AndroidViewModel {


    private OfflineDatabaseManager offline_DB_manager;

    private RealmResults<RO_DPDP> data;

    public MutableLiveData<ArrayList<RO_DPDP>> ldt_ro_dpdp = new MutableLiveData<>();

    public MVVM_DPDPMenuActivityPresenter(@NonNull Application view) {
        super(view);
        offline_DB_manager = new OfflineDatabaseManager(view.getBaseContext());
    }

    public void loadData () {
        data = offline_DB_manager.readAsyncAllDataOf(RO_DPDP.class, ro_dpdps ->
                ldt_ro_dpdp.setValue(toRO_DPDPs_ArrayList(ro_dpdps)));
    }

}