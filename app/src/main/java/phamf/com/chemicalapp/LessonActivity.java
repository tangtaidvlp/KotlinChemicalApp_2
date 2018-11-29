package phamf.com.chemicalapp;

import android.arch.lifecycle.ViewModelProviders;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import phamf.com.chemicalapp.Abstraction.Interface.ILessonActivity;
import phamf.com.chemicalapp.Adapter.ViewPager_Lesson_Adapter;
import phamf.com.chemicalapp.CustomView.LessonViewCreator;
import phamf.com.chemicalapp.CustomView.ViewPagerIndicator;
import phamf.com.chemicalapp.Manager.AppThemeManager;
import phamf.com.chemicalapp.Presenter.LessonActivityPresenter;
import phamf.com.chemicalapp.ViewModel.MVVM_LessonActivityPresenter;

import static java.util.concurrent.Executors.newFixedThreadPool;
import static phamf.com.chemicalapp.CustomView.LessonViewCreator.PART_DEVIDER;
import static phamf.com.chemicalapp.Supporter.UnitConverter.DpToPixel;


/**
 * @see LessonActivityPresenter
 */
public class LessonActivity extends FullScreenActivity implements ILessonActivity.View{

    @BindView(R.id.txt_lesson_title) TextView txt_title;

    @BindView(R.id.btn_back_lesson) Button btn_back;

    @BindView(R.id.parent_lesson_activity) ConstraintLayout base_view;

    @BindView(R.id.bg_night_mode_lesson) TextView bg_night_mode;

    @BindView(R.id.bg_hide_content_to_show_indicator) ConstraintLayout bg_hide_content_to_show_indicator;

    @BindView(R.id.vpg_indicator) ViewPagerIndicator vpg_indicator;

    @BindView(R.id.vpg_lesson) ViewPager vpg_lesson;
    ViewPager_Lesson_Adapter vpg_lesson_adapter;

//    private LessonActivityPresenter presenter;

    private MVVM_LessonActivityPresenter viewModel;

    Animation fade_in, fade_out;

    boolean isOn = false;

    static volatile int time = 500;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lesson);

        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(MVVM_LessonActivityPresenter.class);

        viewModel.ldt_ro_lesson.observe(this, ro_lesson -> {
            //        String s_content = "<co_divi>" +
//                "<<b_title>>I – TÍNH CHẤT VẬT LÝ VÀ TRẠNG THÁI TỰ NHIÊN<co_divi>" +
//                "" +
//                "<<content>>•Glucozo là chất rắn, tinh thể không màu, dễ tan trong nước, có vị ngọt nhưng không ngọt bằng đường mía<co_divi>" +
//                "" +
//                "<<content>>•Có nhiều trong trái cây chín (nho)<co_divi>" +
//                "" +
//                "<<b_title>>II – TÍNH CHẤT VẬT LÝ VÀ TRẠNG THÁI TỰ NHIÊN<co_divi>" +
//                " " +
//                "<<content>>-Công thức phân tử : C6H12O6<co_divi>" +
//                " " +
//                "<<content>>-Có phản ứng tráng bạc và bị oxi hóa bởi Brom <co_divi>" +
//                "tạo thành axit gluconic => phân tử có nhóm CH=O <<content>>-Tác dụng với Cu(OH)2 cho dd màu xanh lam => có nhiều nhóm OH liền kề<co_divi>" +
//                " <<content>>-Chứa 5 nhóm OH<co_divi>" +
//                " <<content>>-Phân tử gồm 6 nguyên tử Cacbon tạo thành mạch không nhánh<co_divi>" +
//                " <<content>>-Glucozo có công thức cấu tạo : <co_divi>" +
//                "   <<picture<>Lessons/lesson1/ctpt_glucozose.jpg<>400<>30<co_divi>" +
//                "  <<content>>hay CH2OH [CHOH]4 CHO<co_divi>" +
//                "  <<content>>-Tồn tại chủ yếu ở dạng alpha-glucose và beta-glucose<co_divi>" +
//                "  <<b_title>>III – TÍNH CHẤT HÓA HỌC<co_divi>" +
//                " <<content>>Glucozo mang tính chất của andehit đơn chất và ancol đa chức (poliancol) <co_divi>" +
//                " <<s_title>>1) Tính chất của ancol đa chức<co_divi>" +
//                " <<smlr_tt>>      a.Tác dụng với Cu(OH)2<co_divi>" +
//                " <<content>>Ở nhiệt độ thường, glucozo phản ứng với Cu(OH)2 cho phức đồng glucozo Cu(C6H11O6)2 tương tự glixerol tạo thành dung dịch màu xanh lam<co_divi>" +
//                "            2C6H12O6 + Cu(OH)2 -> (C6H11O6)2Cu + 2H2O<co_divi>" +
//                "  <<smlr_tt>>      b.Phản ứng tạo este<co_divi>" +
//                "  <<content>>C6H7O(OH)5 + 5(CH3CO)2O -> C6H7O(OOCCH3)5 + 5CH3COOH (đk : piridin) <co_divi>" +
//                "  <<s_title>>2) Tính chất của andehit<co_divi>" +
//                " <<smlr_tt>>      a.Tác dụng với AgNO3/NH3 (phản ứng tráng bạc) <co_divi>" +
//                "  <<content>>Dd AgNO3 trong NH3 oxi hóa glucozo tạo thành muối Amoni Gluconat và Ag<co_divi>" +
//                "  <<content>>HOCH2[CHOH]4CHO + 2AgNO3 + 3NH3 + H2O -> -> HOCH2[CHOH]4COONH4 + 2Ag\uF0AF + 2NH4NO3<co_divi>" +
//                "  <<smlr_tt>>      b.Oxi hóa glucozo bằng Cu(OH)2<co_divi>" +
//                "  <<content>>Trong môi trường kiềm, Cu(OH)2 oxi hóa glucozo tạo thành muối Natri Gluconat, Cu2O màu đỏ gạch và Nước<co_divi>" +
//                "  <<smlr_tt>>      c.Khử glucozo bằng Hidro<co_divi>" +
//                "  <<content>>Dung dịch Glucozo đun nóng tác dụng với khí Hidro, xúc tác Niken thu được poliancol mang tên sobitol<co_divi>" +
//                "  <<content>>CH2OH [CHOH]4 CHO + H2 -> CH2OH [CHOH]4 CH2OH (xt : nhiệt độ, Niken) <co_divi>" +
//                "  <<s_title>>3) Phản ứng lên men<co_divi>" +
//                " <<content>>Khi có enzim xúc tác, glucozo trong dung dịch lên men cho rượu ancol etylic (C6H5OH) và khí Cacbonic (CO2) <co_divi>" +
//                "  <<picture<>Lessons/lesson1/pu_len_men_glucozo.jpg<>300<>50<co_divi>" +
//                "  <<b_title>>IV – ĐIỀU CHẾ VÀ ỨNG DỤNG<co_divi>" +
//                " <<s_title>>1) Điều chế<co_divi>" +
//                " <<content>>Thủy phân tinh bột xúc tác Axit Chlohidric loãng hoặc Enzym<co_divi>" +
//                " <<s_title>>2) Ứng dụng<co_divi>" +
//                " <<content>>-Chất dinh dưỡng và thuốc tăng lực cho người già, trẻ em và người ốm<co_divi>" +
//                " <<content>>-Trong CN dùng để tráng gương<co_divi>" +
//                " <<content>>-Sản phẩm trung gian điều chế Ancol Etylic từ tinh bột và Xenlulose<co_divi>" +
//                "";
            separatePart_And_BindDataToViewPG(ro_lesson.getContent());

            if (vpg_indicator.isHasTitle()) vpg_indicator.setTitle_list(vpg_lesson_adapter.getTitles());

            vpg_indicator.setDot_count(vpg_lesson_adapter.getCount());
            txt_title.setText(ro_lesson.getName());
        });

        setTheme();

        addControl();

        setUpViewCreator();

        loadAndSetEventForAnimation();

        addEvent();

        viewModel.loadData(this);
    }

    public void addControl () {
        FragmentManager fragmentManager = getSupportFragmentManager();
        vpg_lesson_adapter = new ViewPager_Lesson_Adapter(fragmentManager);
        vpg_lesson.setAdapter(vpg_lesson_adapter);
    }

    public void addEvent() {

        btn_back.setOnClickListener(v -> finish());

        vpg_lesson.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (!isOn) {
                    bg_hide_content_to_show_indicator.startAnimation(fade_in);
                    isOn = true;
                }
                time = 1000;
            }

            @Override
            public void onPageSelected(int position) {
                vpg_indicator.hightLightDotAt(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });

    }

    public void setTheme () {
        if (AppThemeManager.isOnNightMode) {
            bg_night_mode.setVisibility(View.VISIBLE);
        } else {
            bg_night_mode.setVisibility(View.INVISIBLE);
        }

        if (AppThemeManager.isCustomingTheme) {
            if (AppThemeManager.isUsingColorBackground)
                base_view.setBackgroundColor(AppThemeManager.getBackgroundColor());
            else
                base_view.setBackground(AppThemeManager.getBackgroundDrawable());
        }
    }

    public void separatePart_And_BindDataToViewPG (String content) {

        String [] part_list = content.split(PART_DEVIDER);

        //Add to Viewpager, then ViewPager send it to Fragment and process
        /**
         * @see phamf.com.chemicalapp.Fragment.LessonPartFragment
         */
        vpg_lesson_adapter.addData(part_list);
    }

    public void setUpViewCreator () {

        if (!LessonViewCreator.isSetUp) {
            LessonViewCreator.ViewCreator.setMarginBigTitle(DpToPixel(10),DpToPixel(7),DpToPixel(10),0);
            LessonViewCreator.ViewCreator.setMarginSmallTitle(DpToPixel(13),DpToPixel(4),DpToPixel(10),0);
            LessonViewCreator.ViewCreator.setMarginSmallerTitle(DpToPixel(15),DpToPixel(4),DpToPixel(10),0);
            LessonViewCreator.ViewCreator.setMarginContent(DpToPixel(20),DpToPixel(4),DpToPixel(10),0);
            LessonViewCreator.ViewCreator.setBig_title_text_size(DpToPixel(2.2));
            LessonViewCreator.ViewCreator.setSmall_title_text_size(DpToPixel(2));
            LessonViewCreator.ViewCreator.setSmaller_title_text_size(DpToPixel(1.8));
            LessonViewCreator.ViewCreator.setContent_text_size(DpToPixel(1.5));
            LessonViewCreator.isSetUp = true;
        }

    }

    public void loadAndSetEventForAnimation () {
        ExecutorService threadPool =  Executors.newFixedThreadPool(1);

        Runnable count_time_to_hide = () -> {
            while (time > 0) {
                try {
                    Thread.currentThread().sleep(100);
                    time = time - 100;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            runOnUiThread(() ->
                    bg_hide_content_to_show_indicator.startAnimation(fade_out));
        };

        fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        fade_in.setDuration(200);

        fade_out = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        fade_out.setDuration(200);

        fade_out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                isOn = false;
            }

            public void onAnimationStart(Animation animation) {

            }

            public void onAnimationRepeat(Animation animation) {

            }
        });


        fade_in.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                threadPool.execute(count_time_to_hide);
            }

            public void onAnimationStart(Animation animation) {

            }
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

}
