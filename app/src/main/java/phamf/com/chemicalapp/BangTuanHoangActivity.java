package phamf.com.chemicalapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import phamf.com.chemicalapp.Abstraction.Interface.IBangTuanHoangActivity;
import phamf.com.chemicalapp.Adapter.BangTuanHoang_GrV_Adapter;
import phamf.com.chemicalapp.Adapter.Search_Chem_Element_Adapter;
import phamf.com.chemicalapp.CustomView.VirtualKeyBoardSensor;
import phamf.com.chemicalapp.Manager.AppThemeManager;
import phamf.com.chemicalapp.RO_Model.RO_Chemical_Element;
import phamf.com.chemicalapp.Supporter.UnitConverter;

import static android.view.View.GONE;

/**
 * Presenter
 * Not created yet
 */

public class BangTuanHoangActivity extends FullScreenActivity implements IBangTuanHoangActivity.View {

    public static final String CHEM_ELEMENT = "chem_element";

    @BindView(R.id.grv_bang_tuan_hoan) GridView grv_bang_tuan_hoan;
    BangTuanHoang_GrV_Adapter grv_bth_adapter;

    @BindView(R.id.rcv_bangtuanhoan_chem_element_search) RecyclerView rcv_element_search;
    Search_Chem_Element_Adapter rcv_element_search_adapter;

    @BindView(R.id.ln_bth_gridview_Parent) LinearLayout grv_bth_parent;

    @BindView(R.id.parent_bangtuanhoan_activity) HorizontalScrollView base_view;

    @BindView(R.id.bg_night_mode_bang_tuan_hoan) TextView bg_night_mode;

    @BindView(R.id.btn_bangtuanhoan_back) Button btn_back;

    @BindView(R.id.btn_bottom_bangtuanhoan_search) Button btn_bottom_turn_on_search;

    @BindView(R.id.btn_top_bangtuanhoan_turn_off_search) Button btn_top_turnOff_search;

    @BindView(R.id.edt_bangtuanhoan_search) VirtualKeyBoardSensor edt_search;

    @BindView(R.id.bangtuanhoan_search_chem_element_view_parent) RelativeLayout search_view_parent;

    ArrayList<RO_Chemical_Element> grv_bth_list = new ArrayList<>();

    public ArrayList<String> list = new ArrayList<>();

    private Animation fade_out, fade_in;

    InputMethodManager virtualKeyboardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bang_tuan_hoang);

        ButterKnife.bind(this);

        setTheme();

        loadAnim();

        setUpManagers();

        addData();

        addControl();

        addEvent();

        setPeriodicTableWidth();

    }

    private void setUpManagers() {
        virtualKeyboardManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
    }

    private void addData () {

        grv_bth_list.add(new RO_Chemical_Element(1, "Hidro", "H", 1, 1, 0, 2, "Không xác định", getColor(R.color.alkali_metal_color)));

        grv_bth_list.add(new RO_Chemical_Element());
        grv_bth_list.add(new RO_Chemical_Element());
        grv_bth_list.add(new RO_Chemical_Element());
        grv_bth_list.add(new RO_Chemical_Element());
        grv_bth_list.add(new RO_Chemical_Element());
        grv_bth_list.add(new RO_Chemical_Element());
        grv_bth_list.add(new RO_Chemical_Element());
        grv_bth_list.add(new RO_Chemical_Element());
        grv_bth_list.add(new RO_Chemical_Element());
        grv_bth_list.add(new RO_Chemical_Element());
        grv_bth_list.add(new RO_Chemical_Element());
        grv_bth_list.add(new RO_Chemical_Element());
        grv_bth_list.add(new RO_Chemical_Element());
        grv_bth_list.add(new RO_Chemical_Element());
        grv_bth_list.add(new RO_Chemical_Element());
        grv_bth_list.add(new RO_Chemical_Element());

        grv_bth_list.add(new RO_Chemical_Element(2, "Heli", "He", 2, 2, 2, 4, "Khí trơ", getColor(R.color.alkali_metal_color)));
        grv_bth_list.add(new RO_Chemical_Element(3, "Liti", "Li", 3, 3, 4, 7, "Kiềm", getColor(R.color.alkali_metal_color)));
        grv_bth_list.add(new RO_Chemical_Element(4, "Beri", "Be", 4, 4, 5, 9, "Kiềm thổ", getColor(R.color.alkali_metal_color)));

        grv_bth_list.add(new RO_Chemical_Element());
        grv_bth_list.add(new RO_Chemical_Element());
        grv_bth_list.add(new RO_Chemical_Element());
        grv_bth_list.add(new RO_Chemical_Element());
        grv_bth_list.add(new RO_Chemical_Element());
        grv_bth_list.add(new RO_Chemical_Element());
        grv_bth_list.add(new RO_Chemical_Element());
        grv_bth_list.add(new RO_Chemical_Element());
        grv_bth_list.add(new RO_Chemical_Element());
        grv_bth_list.add(new RO_Chemical_Element());

        grv_bth_list.add(new RO_Chemical_Element(5, "Bo", "B", 5, 4, 6, 11, "Bo", getColor(R.color.nonmetal_color)));
        grv_bth_list.add(new RO_Chemical_Element(6, "Cacbon", "C", 5, 4, 6, 12, "Cacbon", getColor(R.color.nonmetal_color)));
        grv_bth_list.add(new RO_Chemical_Element(7, "Nitơ", "N", 5, 4, 7, 14, "Nito", getColor(R.color.nonmetal_color)));
        grv_bth_list.add(new RO_Chemical_Element(8, "Oxi", "O", 5, 4, 8, 16, "Oxi", getColor(R.color.nonmetal_color)));
        grv_bth_list.add(new RO_Chemical_Element(9, "Flo", "F", 5, 4, 10, 19, "Flo", getColor(R.color.nonmetal_color)));
        grv_bth_list.add(new RO_Chemical_Element(10, "Neon", "Ne", 5, 4, 6, 20, "Khí trơ", getColor(R.color.nonmetal_color)));

        grv_bth_list.add(new RO_Chemical_Element(11, "Natri", "Na", 3, 3, 12, 23, "Kiềm", getColor(R.color.alkali_metal_color)));
        grv_bth_list.add(new RO_Chemical_Element(12, "Magie", "Mg", 3, 3, 12, 24, "Kiềm thổ", getColor(R.color.alkali_metal_color)));

        grv_bth_list.add(new RO_Chemical_Element());
        grv_bth_list.add(new RO_Chemical_Element());
        grv_bth_list.add(new RO_Chemical_Element());
        grv_bth_list.add(new RO_Chemical_Element());
        grv_bth_list.add(new RO_Chemical_Element());
        grv_bth_list.add(new RO_Chemical_Element());
        grv_bth_list.add(new RO_Chemical_Element());
        grv_bth_list.add(new RO_Chemical_Element());
        grv_bth_list.add(new RO_Chemical_Element());
        grv_bth_list.add(new RO_Chemical_Element());

        grv_bth_list.add(new RO_Chemical_Element(13, "Nhôm", "Al", 5, 4, 14, 27, "Bo", getColor(R.color.nonmetal_color)));
        grv_bth_list.add(new RO_Chemical_Element(14, "Silic", "Si", 5, 4, 14, 28, "Cacbon", getColor(R.color.nonmetal_color)));
        grv_bth_list.add(new RO_Chemical_Element(15, "Photpho", "P", 5, 4, 16, 31, "Nito", getColor(R.color.nonmetal_color)));
        grv_bth_list.add(new RO_Chemical_Element(16, "Lưu huỳnh", "S", 5, 4, 16, 32, "Oxi", getColor(R.color.nonmetal_color)));
        grv_bth_list.add(new RO_Chemical_Element(17, "Clo", "Cl", 5, 4, 10, 35, "Flo", getColor(R.color.nonmetal_color)));
        grv_bth_list.add(new RO_Chemical_Element(18, "Argon", "Ar", 5, 4, 6, 40, "Khí trơ", getColor(R.color.nonmetal_color)));

        grv_bth_list.add(new RO_Chemical_Element(19, "Kali", "K", 3, 3, 12, 39, "Kiềm", getColor(R.color.alkali_metal_color)));
        grv_bth_list.add(new RO_Chemical_Element(20, "Canxi", "Ca", 3, 3, 12, 40, "Kiềm thổ", getColor(R.color.alkali_metal_color)));

        grv_bth_list.add(new RO_Chemical_Element(21, "Scanđi", "Sc", 3, 3, 24, 45, "IIIB", getColor(R.color.Group_B_metal)));
        grv_bth_list.add(new RO_Chemical_Element(22, "Titan", "Ti", 3, 3, 12, 48, "IIIB", getColor(R.color.Group_B_metal)));
        grv_bth_list.add(new RO_Chemical_Element(23, "Vanađi", "V", 3, 3, 12, 51, "IIIB", getColor(R.color.Group_B_metal)));
        grv_bth_list.add(new RO_Chemical_Element(24, "Crôm", "Cr", 3, 3, 12, 52, "IIIB", getColor(R.color.Group_B_metal)));
        grv_bth_list.add(new RO_Chemical_Element(25, "Mangan", "Mn", 3, 3, 12, 55, "IIIB", getColor(R.color.Group_B_metal)));
        grv_bth_list.add(new RO_Chemical_Element(26, "Sắt", "Fe", 3, 3, 12, 56, "IIIB", getColor(R.color.Group_B_metal)));
        grv_bth_list.add(new RO_Chemical_Element(27, "Coban", "Co", 3, 3, 12, 59, "IIIB", getColor(R.color.Group_B_metal)));
        grv_bth_list.add(new RO_Chemical_Element(28, "Niken", "Ni", 3, 3, 12, 59, "IIIB", getColor(R.color.Group_B_metal)));
        grv_bth_list.add(new RO_Chemical_Element(29, "Đồng", "Cu", 3, 3, 12, 64, "IIIB", getColor(R.color.Group_B_metal)));
        grv_bth_list.add(new RO_Chemical_Element(30, "Kẽm", "Zn", 3, 3, 12, 65, "IIIB", getColor(R.color.Group_B_metal)));

        grv_bth_list.add(new RO_Chemical_Element(31, "Gali", "Ga", 5, 4, 14, 70, "Bo", getColor(R.color.nonmetal_color)));
        grv_bth_list.add(new RO_Chemical_Element(32, "Gemani", "Ge", 5, 4, 14, 73, "Cacbon", getColor(R.color.nonmetal_color)));
        grv_bth_list.add(new RO_Chemical_Element(33, "Asen", "As", 5, 4, 16, 75, "Nito", getColor(R.color.nonmetal_color)));
        grv_bth_list.add(new RO_Chemical_Element(34, "Selen", "Se", 5, 4, 16, 79, "Oxi", getColor(R.color.nonmetal_color)));
        grv_bth_list.add(new RO_Chemical_Element(35, "Brom", "Br", 5, 4, 10, 80, "Flo", getColor(R.color.nonmetal_color)));
        grv_bth_list.add(new RO_Chemical_Element(36, "Kripton", "Kr", 5, 4, 6, 84, "Khí trơ", getColor(R.color.nonmetal_color)));

        grv_bth_list.add(new RO_Chemical_Element(37, "Rubiđi", "Rb", 3, 3, 12, 86, "Kiềm", getColor(R.color.alkali_metal_color)));
        grv_bth_list.add(new RO_Chemical_Element(38, "Stronti", "Sr", 3, 3, 12, 88, "Kiềm thổ", getColor(R.color.alkali_metal_color)));

        grv_bth_list.add(new RO_Chemical_Element(39, "Ytri", "Y", 3, 3, 24, 89, "IIIB", getColor(R.color.Group_B_metal)));
        grv_bth_list.add(new RO_Chemical_Element(40, "Ziriconi", "Zr", 3, 3, 12, 91, "IIIB", getColor(R.color.Group_B_metal)));
        grv_bth_list.add(new RO_Chemical_Element(41, "Niobi", "Nb", 3, 3, 12, 93, "IIIB", getColor(R.color.Group_B_metal)));
        grv_bth_list.add(new RO_Chemical_Element(42, "Molipđen", "Mo", 3, 3, 12, 96, "IIIB", getColor(R.color.Group_B_metal)));
        grv_bth_list.add(new RO_Chemical_Element(43, "Tecnexi", "Tc", 3, 3, 12, 99, "IIIB", getColor(R.color.Group_B_metal)));
        grv_bth_list.add(new RO_Chemical_Element(44, "Ruteni", "Ru", 3, 3, 12, 101, "IIIB", getColor(R.color.Group_B_metal)));
        grv_bth_list.add(new RO_Chemical_Element(45, "Rođi", "Rh", 3, 3, 12, 103, "IIIB", getColor(R.color.Group_B_metal)));
        grv_bth_list.add(new RO_Chemical_Element(46, "Palađi", "Pd", 3, 3, 12, 106, "IIIB", getColor(R.color.Group_B_metal)));
        grv_bth_list.add(new RO_Chemical_Element(47, "Bạc", "Ag", 3, 3, 12, 108, "IIIB", getColor(R.color.Group_B_metal)));
        grv_bth_list.add(new RO_Chemical_Element(48, "Cađimi", "Cd", 3, 3, 12, 113, "IIIB", getColor(R.color.Group_B_metal)));

        grv_bth_list.add(new RO_Chemical_Element(49, "Inđi", "In", 5, 4, 14, 115, "Bo", getColor(R.color.nonmetal_color)));
        grv_bth_list.add(new RO_Chemical_Element(50, "Thiếc", "Sn", 5, 4, 14, 118, "Cacbon", getColor(R.color.nonmetal_color)));
        grv_bth_list.add(new RO_Chemical_Element(51, "Antimon", "An", 5, 4, 16, 122, "Nito", getColor(R.color.nonmetal_color)));
        grv_bth_list.add(new RO_Chemical_Element(52, "Telu", "Te", 5, 4, 16, 128, "Oxi", getColor(R.color.nonmetal_color)));
        grv_bth_list.add(new RO_Chemical_Element(53, "Iot", "I", 5, 4, 10, 127, "Flo", getColor(R.color.nonmetal_color)));
        grv_bth_list.add(new RO_Chemical_Element(54, "Xenon", "Xe", 5, 4, 6, 131, "Khí trơ", getColor(R.color.nonmetal_color)));

        grv_bth_list.add(new RO_Chemical_Element(55, "Xesi", "Cs", 3, 3, 12, 86, "Kiềm", getColor(R.color.alkali_metal_color)));
        grv_bth_list.add(new RO_Chemical_Element(56, "Bari", "Ba", 3, 3, 12, 88, "Kiềm thổ", getColor(R.color.alkali_metal_color)));

        grv_bth_list.add(new RO_Chemical_Element(57, "Lantan", "La", 3, 3, 24, 89, "IIIB", getColor(R.color.Group_B_metal)));
        grv_bth_list.add(new RO_Chemical_Element(58, "Hafini", "Hf", 3, 3, 12, 91, "IIIB", getColor(R.color.Group_B_metal)));
        grv_bth_list.add(new RO_Chemical_Element(59, "Tantan", "Ta", 3, 3, 12, 93, "IIIB", getColor(R.color.Group_B_metal)));
        grv_bth_list.add(new RO_Chemical_Element(61, "Volfam", "W", 3, 3, 12, 96, "IIIB", getColor(R.color.Group_B_metal)));
        grv_bth_list.add(new RO_Chemical_Element(62, "Reni", "Re", 3, 3, 12, 99, "IIIB", getColor(R.color.Group_B_metal)));
        grv_bth_list.add(new RO_Chemical_Element(63, "Osimi", "Os", 3, 3, 12, 101, "IIIB", getColor(R.color.Group_B_metal)));
        grv_bth_list.add(new RO_Chemical_Element(64, "Iriđi", "Ir", 3, 3, 12, 103, "IIIB", getColor(R.color.Group_B_metal)));
        grv_bth_list.add(new RO_Chemical_Element(65, "Platin", "Pt", 3, 3, 12, 106, "IIIB", getColor(R.color.Group_B_metal)));
        grv_bth_list.add(new RO_Chemical_Element(66, "Vàng", "Au", 3, 3, 12, 108, "IIIB", getColor(R.color.Group_B_metal)));
        grv_bth_list.add(new RO_Chemical_Element(67, "Thủy ngân", "Hg", 3, 3, 12, 113, "IIIB", getColor(R.color.Group_B_metal)));

        grv_bth_list.add(new RO_Chemical_Element(68, "Tali", "Tl", 5, 4, 14, 115, "Bo", getColor(R.color.nonmetal_color)));
        grv_bth_list.add(new RO_Chemical_Element(69, "Chì", "Pb", 5, 4, 14, 118, "Cacbon", getColor(R.color.nonmetal_color)));
        grv_bth_list.add(new RO_Chemical_Element(70, "Bitmut", "Bi", 5, 4, 16, 122, "Nito", getColor(R.color.nonmetal_color)));
        grv_bth_list.add(new RO_Chemical_Element(71, "Poloni", "Po", 5, 4, 16, 128, "Oxi", getColor(R.color.nonmetal_color)));
        grv_bth_list.add(new RO_Chemical_Element(72, "Atatin", "At", 5, 4, 10, 127, "Flo", getColor(R.color.nonmetal_color)));
        grv_bth_list.add(new RO_Chemical_Element(73, "Rađon", "Rn", 5, 4, 6, 131, "Khí trơ", getColor(R.color.nonmetal_color)));

        grv_bth_list.add(new RO_Chemical_Element(74, "Franxi", "Fr", 3, 3, 12, 86, "Kiềm", getColor(R.color.alkali_metal_color)));
        grv_bth_list.add(new RO_Chemical_Element(75, "Rađi", "Ra", 3, 3, 12, 88, "Kiềm thổ", getColor(R.color.alkali_metal_color)));

        grv_bth_list.add(new RO_Chemical_Element(76, "Actini", "Ac", 3, 3, 24, 89, "IIIB", getColor(R.color.Group_B_metal)));

    }

    public void addControl () {
        grv_bth_adapter = new BangTuanHoang_GrV_Adapter( this);
        grv_bth_adapter.adaptFor(grv_bang_tuan_hoan);
        grv_bth_adapter.setData(grv_bth_list);

        rcv_element_search_adapter = new Search_Chem_Element_Adapter(this);
        rcv_element_search_adapter.adaptFor(rcv_element_search);
        rcv_element_search_adapter.setData(grv_bth_list);
        rcv_element_search_adapter.observe(edt_search);

        if (AppThemeManager.isOnNightMode) {
            bg_night_mode.setVisibility(View.VISIBLE);
        } else {
            bg_night_mode.setVisibility(View.INVISIBLE);
        }
    }

    public void addEvent () {

        btn_back.setOnClickListener(v -> finish());

        grv_bth_adapter.setOnItemClickListener(chem_element -> {
            Intent intent = new Intent(BangTuanHoangActivity.this, ChemicalElementActivity.class);
            intent.putExtra(CHEM_ELEMENT, chem_element);
            startActivity(intent);
            hideVirtualKeyboard();
        });

        rcv_element_search_adapter.setOnItemClickListener(element -> {
            search_view_parent.setVisibility(GONE);
            Intent intent = new Intent(BangTuanHoangActivity.this, ChemicalElementActivity.class);
            intent.putExtra(CHEM_ELEMENT, element);
            startActivity(intent);
            hideVirtualKeyboard();
        });

        btn_bottom_turn_on_search.setOnClickListener(v -> {
            search_view_parent.startAnimation(fade_in);
            edt_search.requestFocus();
            showVirtualKeyboard();
        });

        btn_top_turnOff_search.setOnClickListener(v -> {
            search_view_parent.startAnimation(fade_out);
            hideVirtualKeyboard();
            edt_search.setText("");
        });
    }

    public void loadAnim () {
        fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        fade_in.setFillAfter(false);

        fade_out = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        fade_out.setFillAfter(false);

        fade_out.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                search_view_parent.setVisibility(View.GONE);
            }
            public void onAnimationStart(Animation animation) {
            }
            public void onAnimationRepeat(Animation animation) {
            }
        });

        fade_in.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
                search_view_parent.setVisibility(View.VISIBLE);
            }
            public void onAnimationEnd(Animation animation) {
            }
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    private void hideVirtualKeyboard () {
        virtualKeyboardManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    private void showVirtualKeyboard () {
        virtualKeyboardManager.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT);
    }

    private final int CHILD_ELEMENT_LAYOUT_WIDTH_IN_XML_FILE = 105; /* 105 dp*/
    public void setPeriodicTableWidth () {
        grv_bth_parent.getLayoutParams().width = 18 * UnitConverter.DpToPixel(CHILD_ELEMENT_LAYOUT_WIDTH_IN_XML_FILE) + 17 * UnitConverter.DpToPixel(3);
    }

    public void setTheme () {
        if (AppThemeManager.isCustomingTheme) {
            if (AppThemeManager.isUsingColorBackground)
                base_view.setBackgroundColor(AppThemeManager.getBackgroundColor());
            else
                base_view.setBackground(AppThemeManager.getBackgroundDrawable());
        }
    }


}
