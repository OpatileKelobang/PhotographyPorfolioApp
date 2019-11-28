package za.co.bogote.blackcarpetphotography;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager mPager;
    private int[] layouts = {R.layout.first_slide, R.layout.second_slide};
    private MpagerAdapter mpagerAdapter;

    private LinearLayout Dots_Layout;
    private ImageView[] dots;

    private Button btnNext, btnSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (new PreferenceManager(this).checkPreference()) {
            loadHome();
        }

        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        setContentView(R.layout.activity_welcome);

        mPager = (ViewPager) findViewById(R.id.viewPager);
        mpagerAdapter = new MpagerAdapter(layouts, this);
        mPager.setAdapter(mpagerAdapter);

        Dots_Layout = findViewById(R.id.dotsLayout);
        btnNext =  findViewById(R.id.btnNext);
        btnSkip =  findViewById(R.id.btnSkip);
        btnNext.setOnClickListener(this);
        btnSkip.setOnClickListener(this);
        createDots(0);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                createDots(position);
                if (position == layouts.length - 1) {
                    btnNext.setText(getString(R.string.button_start));
                    btnSkip.setVisibility(View.INVISIBLE);
                } else {
                    btnNext.setText(getString(R.string.button_next));
                    btnSkip.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }


    private void createDots(int currentPosition) {
        if (Dots_Layout != null)
            Dots_Layout.removeAllViews();

        dots = new ImageView[layouts.length];

        for (int i = 0; i < layouts.length; i++) {
            dots[i] = new ImageView(this);
            if (i == currentPosition) {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.active_dots));
            } else {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.default_dots));
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(4, 0, 4, 0);

            Dots_Layout.addView(dots[i], params);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNext:
                loadNextSlide();
                break;

            case R.id.btnSkip:
                loadHome();
                new PreferenceManager(this).writePreference();
                break;
        }
    }

    private void loadHome() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void loadNextSlide() {
        int next_slide = mPager.getCurrentItem() + 1;

        if (next_slide < layouts.length) {
            mPager.setCurrentItem(next_slide);
        } else {
            loadHome();
            new PreferenceManager(this).writePreference();
        }
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Tap back again to exit!", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
