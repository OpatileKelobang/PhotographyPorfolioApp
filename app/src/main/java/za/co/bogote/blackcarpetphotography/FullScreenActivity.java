package za.co.bogote.blackcarpetphotography;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class FullScreenActivity extends AppCompatActivity {

    private static final String TAG = "FullScreenActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);
        Log.d(TAG, "OnCreate: Started ");

        getIncomingData();
    }

   private void getIncomingData()
   {
       Log.d(TAG, "Get Incoming Data From Intent: Checking Data");
       if(getIntent().hasExtra("image_url") && getIntent().hasExtra("image_name"))
       {
            Log.d(TAG, "getIncomingData: Found Extras");
            String imageUrl = getIntent().getStringExtra("image_url");
            String imageName = getIntent().getStringExtra("image_name");

            setImageDetails(imageUrl, imageName);
       }
   }

   private void setImageDetails(String imageUrl, String imageName)
   {
       Log.d(TAG, "Set Image Details to view");
       TextView picName = findViewById(R.id.description);
       picName.setText(imageName);

       ImageView image = findViewById(R.id.image_preview);

       Glide.with(this)
               .asBitmap()
               .load(imageUrl)
               .into(image);
   }
}
