package phamf.com.chemicalapp.Presenter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import phamf.com.chemicalapp.Abstraction.AbstractClass.Presenter;
import phamf.com.chemicalapp.CustomView.LessonViewCreator;
import phamf.com.chemicalapp.Abstraction.Interface.IDPDPActivity;
import phamf.com.chemicalapp.DPDPActivity;
import phamf.com.chemicalapp.DPDPMenuActivity;
import phamf.com.chemicalapp.RO_Model.RO_DPDP;
import phamf.com.chemicalapp.RO_Model.RO_Isomerism;
import phamf.com.chemicalapp.RO_Model.RO_OrganicMolecule;

import static phamf.com.chemicalapp.CustomView.LessonViewCreator.ViewCreator.*;

/** Send Data
 * @see DPDPActivity
 * implement this listener
 */

public class DPDPActivityPresenter extends Presenter<DPDPActivity> implements IDPDPActivity.Presenter{

    private DataLoadListener onDataLoadListener;

    public DPDPActivityPresenter(@NonNull DPDPActivity view) {
        super(view);
    }

    public void loadData() {
        Intent intent = view.getIntent();

        RO_DPDP dpdp = intent.getParcelableExtra(DPDPMenuActivity.DPDP_NAME);

        if (dpdp != null) {
            view.qc_organic_adapter.setData(dpdp.getOrganicMolecules());
            convertObjectToData(dpdp);
        } else {
            Toast.makeText(view, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Symbol "<>" : TAG_DEVIDER for devide image's info
     * @see LessonViewCreator.ViewCreator
     */
    public String convertContent (RO_OrganicMolecule orM) {
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

        return content.toString();
    }

    //** Needing it
    public void convertObjectToData (RO_DPDP dpdp) {
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
        if (onDataLoadListener != null)
            onDataLoadListener.onDataLoadSuccess(title, content.toString());
    }

    public void setOnDataLoadListener(DataLoadListener onDataLoadListener) {
        this.onDataLoadListener = onDataLoadListener;
    }

    public interface DataLoadListener {

        void onDataLoadSuccess (String title, String content);

    }
}
