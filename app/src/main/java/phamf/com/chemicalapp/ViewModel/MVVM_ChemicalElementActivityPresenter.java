package phamf.com.chemicalapp.ViewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import phamf.com.chemicalapp.ChemicalElementActivity;
import phamf.com.chemicalapp.Model.SupportModel.ChemicalElementDataGetter;
import phamf.com.chemicalapp.RO_Model.RO_Chemical_Element;

/**
 * @see phamf.com.chemicalapp.ChemicalElementActivity
 */
public class MVVM_ChemicalElementActivityPresenter extends ViewModel {

    public MutableLiveData<RO_Chemical_Element> ldt_chemical_element = new MutableLiveData<>();


    public MVVM_ChemicalElementActivityPresenter() {

    }

    public void loadData (ChemicalElementActivity activity) {
        // Don't check null to show error when we forget to register to this listener
        ldt_chemical_element.setValue(new ChemicalElementDataGetter(activity).getData());
    }

}

