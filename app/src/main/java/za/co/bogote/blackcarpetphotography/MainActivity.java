package za.co.bogote.blackcarpetphotography;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int NUM_COLS = 2;
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> mImageName = new ArrayList<>();
    private FirebaseAnalytics mFirebaseAnalytics;
    public static final String SHARE_DESCRIPTION = "Connect with me on my amazing Photography Portfolio app. Now Available on @GooglePlay Store - ";
    public static final String HASHTAG_PHOTOGRAPHY = " #Photography #Portfolio #NextLevel #GooglePlay";
    String mAppUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        if(checkNetworkState())
        {
            initImageBitmaps();
        }
        else
        {
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.ic_signal)
                    .setTitle("Internet Connection Alert")
                    .setMessage("You are not connected to the Internet. \nPlease check connection and restart app!")
                    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    })
                    .show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.profile:
                Intent profileIntent = new Intent(this, ProfileActivity.class);
                startActivity(profileIntent);
                return true;

            case R.id.email:
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);

                String uriText = "mailto:" + Uri.encode("info@blackcarpet.co.za") +
                        "?subject=" + Uri.encode("Photography Query") +
                        "&body=" + Uri.encode("\n\n\n\nSent From \nBlack Carpet Photography \nAndroid App");
                Uri uri = Uri.parse(uriText);
                emailIntent.setData(uri);
                startActivity(Intent.createChooser(emailIntent, "Send Email"));
                return true;

            case R.id.share:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String shareString = SHARE_DESCRIPTION + " " + mAppUrl + HASHTAG_PHOTOGRAPHY;
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareString);
                startActivity(shareIntent);
                return true;

            case R.id.preferences:
                new PreferenceManager(this).clearPreference();
                startActivity(new Intent(this, WelcomeActivity.class));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initImageBitmaps()
    {
        // TODO: Remove initImageBitmaps
        Log.d(TAG, "initBitmaps: preparing image bitmaps");

        mImageUrls.add("https://www.blackcarpet.co.za/photos/Maserati.jpg");
        mImageName.add("Maserati Trident");

        mImageUrls.add("https://www.blackcarpet.co.za/photos/Dark-Knight.jpg");
        mImageName.add("The Dark Knight");

        mImageUrls.add("https://www.blackcarpet.co.za/photos/IMG_8297.jpg");
        mImageName.add("Silver Fox - 1");

        mImageUrls.add("https://www.blackcarpet.co.za/photos/Shakur.jpg");
        mImageName.add("Baby Shakur");

        mImageUrls.add("https://www.blackcarpet.co.za/photos/IMG_8496.jpg");
        mImageName.add("Summer Tidings");

        mImageUrls.add("https://www.blackcarpet.co.za/photos/IMG_8540.jpg");
        mImageName.add("Crown Jewels");

        mImageUrls.add("https://www.blackcarpet.co.za/photos/IMG_8290.jpg");
        mImageName.add("Silver Fox - 2");

        mImageUrls.add("https://www.blackcarpet.co.za/photos/IMG_0158.jpg");
        mImageName.add("Baby Dale");

        mImageUrls.add("https://www.blackcarpet.co.za/photos/IMG_3831.jpg");
        mImageName.add("Merc Precision");

        mImageUrls.add("https://www.blackcarpet.co.za/photos/IMG_4055.jpg");
        mImageName.add("Lauren Kieser");

        mImageUrls.add("https://www.blackcarpet.co.za/photos/IMG_6514.jpg");
        mImageName.add("Big World");

        mImageUrls.add("https://www.blackcarpet.co.za/photos/IMG_6678.jpg");
        mImageName.add("Beyon The Horizon");

        mImageUrls.add("https://www.blackcarpet.co.za/photos/IMG_6954.jpg");
        mImageName.add("Daryl and Lyndsey");

        mImageUrls.add("https://www.blackcarpet.co.za/photos/IMG_8024.jpg");
        mImageName.add("Goldilocks");

        mImageUrls.add("https://www.blackcarpet.co.za/photos/IMG_7940.jpg");
        mImageName.add("The Long Trip");

        mImageUrls.add("https://www.blackcarpet.co.za/photos/IMG_7218.jpg");
        mImageName.add("Cinderella");

        mImageUrls.add("https://www.blackcarpet.co.za/photos/IMG_6429.jpg");
        mImageName.add("Rust Cafe");

        mImageUrls.add("https://www.blackcarpet.co.za/photos/IMG_6423.jpg");
        mImageName.add("Love Java");

        mImageUrls.add("https://www.blackcarpet.co.za/photos/IMG_8238.jpg");
        mImageName.add("Summer Blossoms");

        mImageUrls.add("https://www.blackcarpet.co.za/photos/IMG_5311.jpg");
        mImageName.add("Mmino wa Setso");

        initRecyclerView();
    }

    private void initRecyclerView() {
        // TODO: Remove initImageBitmaps
        Log.d(TAG, "initRecyclerView: Initializing Staggered RecyclerView");
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        StaggeredRecyclerViewAdapter staggeredRecyclerViewAdapter = new StaggeredRecyclerViewAdapter(this, mImageName, mImageUrls);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(NUM_COLS, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(staggeredRecyclerViewAdapter);
    }

    private boolean checkNetworkState()
    {
        boolean HAS_WIFI = false;
        boolean HAS_MOBILE_DATA = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();

        for(NetworkInfo info : networkInfos)
        {
            if(info.getTypeName().equalsIgnoreCase("Wifi"))
            {
                if(info.isConnected())
                {
                    HAS_WIFI =true;
                }
            }
            if(info.getTypeName().equalsIgnoreCase("Mobile"))
            {
                if(info.isConnected()){
                    HAS_MOBILE_DATA =true;
                }
            }
        }
        return HAS_WIFI || HAS_MOBILE_DATA;
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
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }


}
