package phamf.com.chemicalapp;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import phamf.com.chemicalapp.Adapter.Search_Chem_Element_Adapter;
import phamf.com.chemicalapp.CustomView.VirtualKeyBoardSensor;
import phamf.com.chemicalapp.Presenter.ChemicalElementActivityPresenter;
import phamf.com.chemicalapp.RO_Model.RO_Chemical_Element;
import phamf.com.chemicalapp.ViewModel.MVVM_ChemicalElementActivityPresenter;

/**
 * Presenter
 * @see ChemicalElementActivityPresenter
 */
public class ChemicalElementActivity extends FullScreenActivity {

    @BindView(R.id.txt_chem_element_name) TextView txt_element_name;

    @BindView(R.id.txt_chem_element_symbol) TextView txt_element_symbol;

    @BindView(R.id.txt_chem_element_e_p_partical_count) TextView txt_e_p_count;

    @BindView(R.id.txt_chem_element_notron_count) TextView txt_notron_count;

    @BindView(R.id.txt_chem_element_element_type) TextView txt_element_type;

    @BindView(R.id.txt_specified_weight) TextView txt_specific_weight;

    @BindView(R.id.bg_chem_element_escape_search_mode) TextView bg_escape_search_mode;

    @BindView(R.id.btn_chem_element_back) Button btn_back;

    @BindView(R.id.btn_top_chemical_element_turn_Off_search) Button btn_top_turn_off_search_mode;

    @BindView(R.id.btn_bottom_chemical_element_turn_On_search) Button btn_bottom_turn_on_search_mode;

    @BindView(R.id.edt_chem_element_search) VirtualKeyBoardSensor edt_search;

    @BindView(R.id.chem_element_search_chemical_element_view_parent) RelativeLayout search_view_parent;

    @BindView(R.id.rcv_chem_element_search) RecyclerView rcv_chem_element_search;
    Search_Chem_Element_Adapter rcv_chem_element_search_adapter;
    ArrayList<RO_Chemical_Element> rcv_chem_element_search_list;

    Animation fade_out, fade_in;

    InputMethodManager softKeyboardManager;

    MVVM_ChemicalElementActivityPresenter viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chemical_element);

        ButterKnife.bind(this);

        setUpViewModel();

        createNeccesaryInfo();

        loadAnimation();

        addControl();

        addEvent();

        viewModel.loadData(this);

    }

    private void setUpViewModel () {
        viewModel = ViewModelProviders.of(this).get(MVVM_ChemicalElementActivityPresenter.class);

        viewModel.ldt_chemical_element.observe(this, chem_element -> showChem_Element_Info(chem_element));
    }

    public void createNeccesaryInfo() {
        softKeyboardManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
    }

    public void addControl() {
        addData();
        rcv_chem_element_search_adapter = new Search_Chem_Element_Adapter(this);
        rcv_chem_element_search_adapter.adaptFor(rcv_chem_element_search);
        rcv_chem_element_search_adapter.setData(rcv_chem_element_search_list);
        rcv_chem_element_search_adapter.observe(edt_search);
    }

    public void addEvent() {
        btn_back.setOnClickListener(v -> finish());

        btn_bottom_turn_on_search_mode.setOnClickListener(v -> search_view_parent.startAnimation(fade_in));

        btn_top_turn_off_search_mode.setOnClickListener(v -> {
            search_view_parent.startAnimation(fade_out);
            hideSoftKeyboard();
        });

        rcv_chem_element_search_adapter.setOnItemClickListener(element -> {
            showChem_Element_Info(element);
            search_view_parent.startAnimation(fade_out);
            hideSoftKeyboard();
        });

        edt_search.setOnClickListener(v -> {
            search_view_parent.startAnimation(fade_in);
        });

        bg_escape_search_mode.setOnClickListener(v -> {
            search_view_parent.startAnimation(fade_out);
            edt_search.setText("");
        });
    }

    public void showSoftKeyboard () {
        softKeyboardManager.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT);
    }

    public void hideSoftKeyboard () {
        softKeyboardManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    public void loadAnimation () {
        fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        fade_out = AnimationUtils.loadAnimation(this, R.anim.fade_out);

        fade_in.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                search_view_parent.setVisibility(View.VISIBLE);
            }

            public void onAnimationEnd(Animation animation) {
            }
            public void onAnimationRepeat(Animation animation) {
            }
        });
        fade_out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                search_view_parent.setVisibility(View.GONE);
            }

            public void onAnimationStart(Animation animation) {
            }
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void showChem_Element_Info (RO_Chemical_Element chem_element) {
        txt_element_name.setText(chem_element.getName() + "");
        txt_element_symbol.setText(chem_element.getSymbol());
        txt_e_p_count.setText(String.valueOf(chem_element.getProton_count()));
        txt_notron_count.setText(String.valueOf(chem_element.getProton_count()));
        txt_specific_weight.setText(String.valueOf(chem_element.getMass()));
        txt_element_type.setText(chem_element.getType());
    }

    private void addData () {
        rcv_chem_element_search_list = new ArrayList<>();

        rcv_chem_element_search_list.add(new RO_Chemical_Element(1, "Hidro", "H", 1, 1, 0, 2, "Không xác định", getColor(R.color.alkali_metal_color)));

        rcv_chem_element_search_list.add(new RO_Chemical_Element(2, "Heli", "He", 2, 2, 2, 4, "Khí trơ", getColor(R.color.alkali_metal_color)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(3, "Liti", "Li", 3, 3, 4, 7, "Kiềm", getColor(R.color.alkali_metal_color)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(4, "Beri", "Be", 4, 4, 5, 9, "Kiềm thổ", getColor(R.color.alkali_metal_color)));

        rcv_chem_element_search_list.add(new RO_Chemical_Element(5, "Bo", "B", 5, 4, 6, 11, "Bo", getColor(R.color.nonmetal_color)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(6, "Cacbon", "C", 5, 4, 6, 12, "Cacbon", getColor(R.color.nonmetal_color)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(7, "Nitơ", "N", 5, 4, 7, 14, "Nito", getColor(R.color.nonmetal_color)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(8, "Oxi", "O", 5, 4, 8, 16, "Oxi", getColor(R.color.nonmetal_color)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(9, "Flo", "F", 5, 4, 10, 19, "Flo", getColor(R.color.nonmetal_color)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(10, "Neon", "Ne", 5, 4, 6, 20, "Khí trơ", getColor(R.color.nonmetal_color)));

        rcv_chem_element_search_list.add(new RO_Chemical_Element(11, "Natri", "Na", 3, 3, 12, 23, "Kiềm", getColor(R.color.alkali_metal_color)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(12, "Magie", "Mg", 3, 3, 12, 24, "Kiềm thổ", getColor(R.color.alkali_metal_color)));

        rcv_chem_element_search_list.add(new RO_Chemical_Element(13, "Nhôm", "Al", 5, 4, 14, 27, "Bo", getColor(R.color.nonmetal_color)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(14, "Silic", "Si", 5, 4, 14, 28, "Cacbon", getColor(R.color.nonmetal_color)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(15, "Photpho", "P", 5, 4, 16, 31, "Nito", getColor(R.color.nonmetal_color)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(16, "Lưu huỳnh", "S", 5, 4, 16, 32, "Oxi", getColor(R.color.nonmetal_color)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(17, "Clo", "Cl", 5, 4, 10, 35, "Flo", getColor(R.color.nonmetal_color)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(18, "Argon", "Ar", 5, 4, 6, 40, "Khí trơ", getColor(R.color.nonmetal_color)));

        rcv_chem_element_search_list.add(new RO_Chemical_Element(19, "Kali", "K", 3, 3, 12, 39, "Kiềm", getColor(R.color.alkali_metal_color)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(20, "Canxi", "Ca", 3, 3, 12, 40, "Kiềm thổ", getColor(R.color.alkali_metal_color)));

        rcv_chem_element_search_list.add(new RO_Chemical_Element(21, "Scanđi", "Sc", 3, 3, 24, 45, "IIIB", getColor(R.color.Group_B_metal)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(22, "Titan", "Ti", 3, 3, 12, 48, "IIIB", getColor(R.color.Group_B_metal)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(23, "Vanađi", "V", 3, 3, 12, 51, "IIIB", getColor(R.color.Group_B_metal)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(24, "Crôm", "Cr", 3, 3, 12, 52, "IIIB", getColor(R.color.Group_B_metal)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(25, "Mangan", "Mn", 3, 3, 12, 55, "IIIB", getColor(R.color.Group_B_metal)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(26, "Sắt", "Fe", 3, 3, 12, 56, "IIIB", getColor(R.color.Group_B_metal)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(27, "Coban", "Co", 3, 3, 12, 59, "IIIB", getColor(R.color.Group_B_metal)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(28, "Niken", "Ni", 3, 3, 12, 59, "IIIB", getColor(R.color.Group_B_metal)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(29, "Đồng", "Cu", 3, 3, 12, 64, "IIIB", getColor(R.color.Group_B_metal)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(30, "Kẽm", "Zn", 3, 3, 12, 65, "IIIB", getColor(R.color.Group_B_metal)));

        rcv_chem_element_search_list.add(new RO_Chemical_Element(31, "Gali", "Ga", 5, 4, 14, 70, "Bo", getColor(R.color.nonmetal_color)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(32, "Gemani", "Ge", 5, 4, 14, 73, "Cacbon", getColor(R.color.nonmetal_color)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(33, "Asen", "As", 5, 4, 16, 75, "Nito", getColor(R.color.nonmetal_color)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(34, "Selen", "Se", 5, 4, 16, 79, "Oxi", getColor(R.color.nonmetal_color)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(35, "Brom", "Br", 5, 4, 10, 80, "Flo", getColor(R.color.nonmetal_color)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(36, "Kripton", "Kr", 5, 4, 6, 84, "Khí trơ", getColor(R.color.nonmetal_color)));

        rcv_chem_element_search_list.add(new RO_Chemical_Element(37, "Rubiđi", "Rb", 3, 3, 12, 86, "Kiềm", getColor(R.color.alkali_metal_color)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(38, "Stronti", "Sr", 3, 3, 12, 88, "Kiềm thổ", getColor(R.color.alkali_metal_color)));

        rcv_chem_element_search_list.add(new RO_Chemical_Element(39, "Ytri", "Y", 3, 3, 24, 89, "IIIB", getColor(R.color.Group_B_metal)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(40, "Ziriconi", "Zr", 3, 3, 12, 91, "IIIB", getColor(R.color.Group_B_metal)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(41, "Niobi", "Nb", 3, 3, 12, 93, "IIIB", getColor(R.color.Group_B_metal)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(42, "Molipđen", "Mo", 3, 3, 12, 96, "IIIB", getColor(R.color.Group_B_metal)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(43, "Tecnexi", "Tc", 3, 3, 12, 99, "IIIB", getColor(R.color.Group_B_metal)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(44, "Ruteni", "Ru", 3, 3, 12, 101, "IIIB", getColor(R.color.Group_B_metal)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(45, "Rođi", "Rh", 3, 3, 12, 103, "IIIB", getColor(R.color.Group_B_metal)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(46, "Palađi", "Pd", 3, 3, 12, 106, "IIIB", getColor(R.color.Group_B_metal)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(47, "Bạc", "Ag", 3, 3, 12, 108, "IIIB", getColor(R.color.Group_B_metal)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(48, "Cađimi", "Cd", 3, 3, 12, 113, "IIIB", getColor(R.color.Group_B_metal)));

        rcv_chem_element_search_list.add(new RO_Chemical_Element(49, "Inđi", "In", 5, 4, 14, 115, "Bo", getColor(R.color.nonmetal_color)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(50, "Thiếc", "Sn", 5, 4, 14, 118, "Cacbon", getColor(R.color.nonmetal_color)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(51, "Antimon", "An", 5, 4, 16, 122, "Nito", getColor(R.color.nonmetal_color)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(52, "Telu", "Te", 5, 4, 16, 128, "Oxi", getColor(R.color.nonmetal_color)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(53, "Iot", "I", 5, 4, 10, 127, "Flo", getColor(R.color.nonmetal_color)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(54, "Xenon", "Xe", 5, 4, 6, 131, "Khí trơ", getColor(R.color.nonmetal_color)));

        rcv_chem_element_search_list.add(new RO_Chemical_Element(55, "Xesi", "Cs", 3, 3, 12, 86, "Kiềm", getColor(R.color.alkali_metal_color)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(56, "Bari", "Ba", 3, 3, 12, 88, "Kiềm thổ", getColor(R.color.alkali_metal_color)));

        rcv_chem_element_search_list.add(new RO_Chemical_Element(57, "Lantan", "La", 3, 3, 24, 89, "IIIB", getColor(R.color.Group_B_metal)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(58, "Hafini", "Hf", 3, 3, 12, 91, "IIIB", getColor(R.color.Group_B_metal)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(59, "Tantan", "Ta", 3, 3, 12, 93, "IIIB", getColor(R.color.Group_B_metal)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(61, "Volfam", "W", 3, 3, 12, 96, "IIIB", getColor(R.color.Group_B_metal)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(62, "Reni", "Re", 3, 3, 12, 99, "IIIB", getColor(R.color.Group_B_metal)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(63, "Osimi", "Os", 3, 3, 12, 101, "IIIB", getColor(R.color.Group_B_metal)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(64, "Iriđi", "Ir", 3, 3, 12, 103, "IIIB", getColor(R.color.Group_B_metal)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(65, "Platin", "Pt", 3, 3, 12, 106, "IIIB", getColor(R.color.Group_B_metal)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(66, "Vàng", "Au", 3, 3, 12, 108, "IIIB", getColor(R.color.Group_B_metal)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(67, "Thủy ngân", "Hg", 3, 3, 12, 113, "IIIB", getColor(R.color.Group_B_metal)));

        rcv_chem_element_search_list.add(new RO_Chemical_Element(68, "Tali", "Tl", 5, 4, 14, 115, "Bo", getColor(R.color.nonmetal_color)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(69, "Chì", "Pb", 5, 4, 14, 118, "Cacbon", getColor(R.color.nonmetal_color)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(70, "Bitmut", "Bi", 5, 4, 16, 122, "Nito", getColor(R.color.nonmetal_color)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(71, "Poloni", "Po", 5, 4, 16, 128, "Oxi", getColor(R.color.nonmetal_color)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(72, "Atatin", "At", 5, 4, 10, 127, "Flo", getColor(R.color.nonmetal_color)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(73, "Rađon", "Rn", 5, 4, 6, 131, "Khí trơ", getColor(R.color.nonmetal_color)));

        rcv_chem_element_search_list.add(new RO_Chemical_Element(74, "Franxi", "Fr", 3, 3, 12, 86, "Kiềm", getColor(R.color.alkali_metal_color)));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(75, "Rađi", "Ra", 3, 3, 12, 88, "Kiềm thổ", getColor(R.color.alkali_metal_color)));

        rcv_chem_element_search_list.add(new RO_Chemical_Element(76, "Actini", "Ac", 3, 3, 24, 89, "IIIB", getColor(R.color.Group_B_metal)));

    }

}
