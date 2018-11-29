package phamf.com.chemicalapp;

import android.app.Activity;
import android.databinding.DataBindingUtil;
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
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import phamf.com.chemicalapp.Abstraction.Interface.IChemicalEquationActivity;
import phamf.com.chemicalapp.Adapter.Search_CE_RCV_Adapter;
import phamf.com.chemicalapp.CustomView.VirtualKeyBoardSensor;
import phamf.com.chemicalapp.Presenter.ChemicalEquationActivityPresenter;
import phamf.com.chemicalapp.RO_Model.RO_ChemicalEquation;

/**
 * Presenter
 * @see ChemicalEquationActivityPresenter
 */
public class ChemicalEquationActivity extends FullScreenActivity implements IChemicalEquationActivity.View, ChemicalEquationActivityPresenter.OnDataLoadedListener{

    public static final String CHEMICAL_EQUATION = "Chemical_Equation";

    // Chất tham gia
    @BindView(R.id.txt_chem_adding_chemicals) TextView txt_adding_chemicals;

    // Chất sản phẩm
    @BindView(R.id.txt_chem_product) TextView txt_product;

    // Điều kiện phản ứng
    @BindView(R.id.txt_chem_conditions) TextView txt_conditions;

    // Tổng hệ số cân bằng
    @BindView(R.id.txt_chem_total_balance_number) TextView txt_total_balance_number;

    // Phenonema : hiện tượng
    @BindView(R.id.txt_chem_phenomena) TextView txt_phenomena;

    @BindView(R.id.title_chem_adding_chems) TextView title_chems_adding;

    @BindView(R.id.title_chem_product_chems) TextView title_chems_product;

    @BindView(R.id.bg_chem_escape_search_mode) TextView bg_escape_search_mode;

    @BindView(R.id.btn_chemical_equation_back) Button btn_back;

    @BindView(R.id.edt_chem_search) VirtualKeyBoardSensor edt_search;

    @BindView(R.id.chemical_equation_search_equation_search_view_parent) RelativeLayout search_view_parent;

    @BindView(R.id.rcv_chem_search) RecyclerView rcv_search;
    private Search_CE_RCV_Adapter rcv_search_adapter;

    private ChemicalEquationActivityPresenter activityPresenter;

    private boolean hasHiddenNavAndStatusBar;

    private boolean isOnSearchMode;

    public InputMethodManager virtualKeyboardManager;

    private Animation fade_in, fade_out;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chemical_equation);

        ButterKnife.bind(this);

        setUpNecessaryInfos();

        loadAnim();

        activityPresenter = new ChemicalEquationActivityPresenter(this);

        activityPresenter.setOnDataLoadedListener(this);

        addControl();

        activityPresenter.loadData();

        addEvent();

    }

    public void setUpNecessaryInfos () {
        virtualKeyboardManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        setEdt_SearchAdvanceFunctions();
    }

    public void addControl () {
        rcv_search_adapter = new Search_CE_RCV_Adapter(this);
        rcv_search_adapter.adaptFor(rcv_search);
        rcv_search_adapter.observe(edt_search);
    }

    public void addEvent () {
        rcv_search_adapter.setOnItemClickListener((view, equation, position) -> {
            bindData(equation);
            search_view_parent.startAnimation(fade_out);
            rcv_search_adapter.isSearching(false);
            hideSoftKeyboard(ChemicalEquationActivity.this);
            isOnSearchMode = false;
        });

        bg_escape_search_mode.setOnClickListener(v -> {
            search_view_parent.startAnimation(fade_out);
            edt_search.setText("");
            rcv_search_adapter.isSearching(false);
            hideSoftKeyboard(ChemicalEquationActivity.this);
            isOnSearchMode = false;
        });

        btn_back.setOnClickListener(v -> finish());
    }

    private void bindData(RO_ChemicalEquation equation) {
        txt_adding_chemicals.setText(equation.getAddingChemicals());
        txt_product.setText(equation.getProduct());
        txt_conditions.setText(equation.getCondition());
        txt_total_balance_number.setText(equation.getTotal_balance_number());
    }

    public void loadAnim () {
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
            public void onAnimationEnd(Animation animation){
                search_view_parent.setVisibility(View.GONE);
            }

            public void onAnimationStart(Animation animation)  {
            }
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void setEdt_SearchAdvanceFunctions () {
        edt_search.setOnClickListener(v -> {
            if (!hasHiddenNavAndStatusBar)
            {
                makeFullScreenAfter(1000);
                hasHiddenNavAndStatusBar = true;
            }
            if (!isOnSearchMode) {
                search_view_parent.startAnimation(fade_in);
                rcv_search_adapter.isSearching(true);
                showSofKeyboard();
                isOnSearchMode = true;
            }
        });


        edt_search.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasHiddenNavAndStatusBar)
            {
                makeFullScreenAfter(1000);
                hasHiddenNavAndStatusBar = true;
            }
        });

        edt_search.setOnHideVirtualKeyboardListener(
                () -> {
                    makeFullScreen();
                    hasHiddenNavAndStatusBar = false;
                });

    }

    public void hideSoftKeyboard(Activity activity) {
        virtualKeyboardManager.hideSoftInputFromWindow(Objects.requireNonNull(activity.getCurrentFocus()).getWindowToken(), 0);
    }

    public void showSofKeyboard () {
        virtualKeyboardManager.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT);
    }

    @Override
    public void onGettingChemicalEquation(RO_ChemicalEquation chemicalEquation) {
        bindData(chemicalEquation);
    }

    @Override
    public void onDataLoadedFromDatabase (ArrayList<RO_ChemicalEquation> chemicalEquations) {
        rcv_search_adapter.setData(chemicalEquations);
    }
}
