package phamf.com.chemicalapp.Presenter;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import io.realm.RealmList;
import phamf.com.chemicalapp.Abstraction.AbstractClass.Presenter;
import phamf.com.chemicalapp.Abstraction.Interface.IChemicalEquationActivity;
import phamf.com.chemicalapp.ChemicalEquationActivity;
import phamf.com.chemicalapp.Database.OfflineDatabaseManager;
import phamf.com.chemicalapp.Manager.RecentSearching_CE_Data_Manager;
import phamf.com.chemicalapp.RO_Model.RO_ChemicalEquation;

import static phamf.com.chemicalapp.ChemicalEquationActivity.CHEMICAL_EQUATION;

/**
 * @see ChemicalEquationActivity
 */
public class ChemicalEquationActivityPresenter extends Presenter<ChemicalEquationActivity> implements IChemicalEquationActivity.Presenter {

    private OnDataLoadedListener onDataLoadedListener;

    private OfflineDatabaseManager offlineDatabaseManager;

    public ChemicalEquationActivityPresenter(@NonNull ChemicalEquationActivity view) {
        super(view);
        offlineDatabaseManager = new OfflineDatabaseManager(view);
    }

    public void loadData () {

        onDataLoadedListener.onGettingChemicalEquation(new ChemicalEquationDataGetter(view).getData());
        // Decide load data in constructor to ensure that
        new RecentSearching_CE_Data_Manager(offlineDatabaseManager)
                .getData(recent_Ces ->
                        onDataLoadedListener.onDataLoadedFromDatabase(recent_Ces));
    }

    public void setOnDataLoadedListener(OnDataLoadedListener onDataLoadedListener) {
        this.onDataLoadedListener = onDataLoadedListener;
    }

    public interface OnDataLoadedListener {

        void onGettingChemicalEquation (RO_ChemicalEquation chemicalEquation);

        void onDataLoadedFromDatabase(ArrayList<RO_ChemicalEquation>chemicalEquations);
    }
}

class ChemicalEquationDataGetter {
    Activity view;

    public ChemicalEquationDataGetter(Activity view) {
        this.view = view;
    }

    public RO_ChemicalEquation getData () {
        Bundle bundle = view.getIntent().getExtras();
        RO_ChemicalEquation chemicalEquation = bundle.getParcelable(CHEMICAL_EQUATION);
        return chemicalEquation;
    }
}
