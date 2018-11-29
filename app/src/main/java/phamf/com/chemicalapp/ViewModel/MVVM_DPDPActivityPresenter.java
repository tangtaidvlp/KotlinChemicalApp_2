package phamf.com.chemicalapp.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.widget.Toast;

import java.util.Collection;

import phamf.com.chemicalapp.CustomView.LessonViewCreator;
import phamf.com.chemicalapp.DPDPActivity;
import phamf.com.chemicalapp.Model.SupportModel.DPDPDataGetter;
import phamf.com.chemicalapp.RO_Model.RO_DPDP;
import phamf.com.chemicalapp.RO_Model.RO_Isomerism;
import phamf.com.chemicalapp.RO_Model.RO_OrganicMolecule;

import static phamf.com.chemicalapp.CustomView.LessonViewCreator.ViewCreator.BIG_TITLE;
import static phamf.com.chemicalapp.CustomView.LessonViewCreator.ViewCreator.COMPONENT_DEVIDER;
import static phamf.com.chemicalapp.CustomView.LessonViewCreator.ViewCreator.CONTENT;
import static phamf.com.chemicalapp.CustomView.LessonViewCreator.ViewCreator.IMAGE;
import static phamf.com.chemicalapp.CustomView.LessonViewCreator.ViewCreator.SMALL_TITLE;

/** Send Data
 * @see DPDPActivity
 * implement this listener
 */

public class MVVM_DPDPActivityPresenter extends AndroidViewModel  {

    public MutableLiveData<Collection<RO_OrganicMolecule>> ldt_ro_organicMolecule = new MutableLiveData<>();
    public MutableLiveData<String> ldt_content_data = new MutableLiveData<>();
    public MutableLiveData<String> ldt_dpdp_name_data = new MutableLiveData<>();

    public MVVM_DPDPActivityPresenter(@NonNull Application application) {
        super(application);
    }

    public void loadData(@NonNull DPDPActivity activity) {
        RO_DPDP dpdp = new DPDPDataGetter(activity).getData();
        if (dpdp != null) {
            ldt_ro_organicMolecule.setValue(dpdp.getOrganicMolecules());
            convertDPDPToTextData(dpdp);
        } else {
            Toast.makeText(activity, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Symbol "<>" : TAG_DEVIDER for devide image's info
     * @see LessonViewCreator.ViewCreator
     */
    public void convertContent(RO_OrganicMolecule orM) {
        StringBuilder content = new StringBuilder();
        content.append(COMPONENT_DEVIDER).append(BIG_TITLE).append(orM.getId() + ") ")
                .append(orM.getMolecule_formula())
                .append(" - " + orM.getName()).append(COMPONENT_DEVIDER)
                .append(CONTENT).append(". - Tên thay thế: " + orM.getReplace_name()).append(COMPONENT_DEVIDER)
                .append(CONTENT).append(". - Công thức cấu tạo : ")
                .append(IMAGE + orM.getStructure_image_id() + "<>" + 200 + "<>" + 250).append(COMPONENT_DEVIDER)
                .append(CONTENT).append(". - Công thức cấu tạo thu gọn: " + orM.getCompact_structure_image_id()).append(COMPONENT_DEVIDER)
                .append(CONTENT).append(". - Số đồng phân: " + orM.getIsomerisms().size()).append(COMPONENT_DEVIDER);

        for (RO_Isomerism iso : orM.getIsomerisms()) {

            content.append(CONTENT).append((orM.getIsomerisms().indexOf(iso) + 1)+ ") " + iso.getReplace_name()).append(COMPONENT_DEVIDER)
                    .append(CONTENT).append(".   - Tên thường: " + orM.getNormal_name()).append(COMPONENT_DEVIDER)
                    .append(CONTENT).append(".   - Công thức cấu tạo: " + iso.getCompact_structure_image_id()).append(COMPONENT_DEVIDER)
                    .append(CONTENT).append(".   - Công thức cấu tạo thu gọn: ");
        }

        ldt_content_data.setValue(content.toString());
    }

    //** Needing it
    void convertDPDPToTextData (RO_DPDP dpdp) {
        StringBuilder content = new StringBuilder();

        String title = dpdp.getName();

        for (RO_OrganicMolecule orM : dpdp.getOrganicMolecules()) {
            content.append(COMPONENT_DEVIDER).append(BIG_TITLE).append(orM.getId() + ") ")
                    .append(orM.getMolecule_formula()).append(COMPONENT_DEVIDER)
                    .append(CONTENT).append(" - Tên thông thường: " + orM.getNormal_name()).append(COMPONENT_DEVIDER)
                    .append(CONTENT).append(" - Tên thay thế: " + orM.getReplace_name()).append(COMPONENT_DEVIDER)
                    .append(CONTENT).append(" - Công thức cấu tạo: ").append(COMPONENT_DEVIDER)
                    .append(IMAGE).append(orM.getStructure_image_id() + "<>400<>400").append(COMPONENT_DEVIDER)
                    .append(CONTENT).append(" - Công thức cấu tạo thu gọn: " + orM.getCompact_structure_image_id()).append(COMPONENT_DEVIDER)
                    .append(CONTENT).append(" - Số đồng phân: " + orM.getIsomerisms().size()).append(COMPONENT_DEVIDER);

            for (RO_Isomerism iso : orM.getIsomerisms()) {
                content.append(SMALL_TITLE).append("   " + iso.getReplace_name()).append(COMPONENT_DEVIDER)
                        .append(CONTENT).append("   - Công thức cấu tạo: ").append(COMPONENT_DEVIDER)
                        .append(IMAGE).append(iso.getStructure_image_id() + "<>400<>400").append(COMPONENT_DEVIDER)
                        .append(CONTENT).append("   - Công thức cấu tạo thu gọn: ").append(COMPONENT_DEVIDER);
            }
        }

        /** Send Data
         * @see DPDPActivity
         * implement this listener
         */
            ldt_content_data.setValue(content.toString());
            ldt_dpdp_name_data.setValue(title);
    }

}

