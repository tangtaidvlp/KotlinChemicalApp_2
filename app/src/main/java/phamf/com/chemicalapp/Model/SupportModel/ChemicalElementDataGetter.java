package phamf.com.chemicalapp.Model.SupportModel;

import android.app.Activity;
import android.os.Bundle;

import phamf.com.chemicalapp.BangTuanHoangActivity;
import phamf.com.chemicalapp.RO_Model.RO_Chemical_Element;

public class ChemicalElementDataGetter {
    Activity view;

    public ChemicalElementDataGetter (Activity view) {
        this.view = view;
    }

    public RO_Chemical_Element getData () {
        Bundle bundle = view.getIntent().getExtras();
        RO_Chemical_Element chem_element = bundle.getParcelable(BangTuanHoangActivity.CHEM_ELEMENT);
        return chem_element;
    }
}
