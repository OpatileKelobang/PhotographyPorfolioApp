package za.co.bogote.blackcarpetphotography;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    public void createPhoneIntent(View view)
    {
        Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
        phoneIntent.setData(Uri.parse("tel:0217014896"));
        startActivity(phoneIntent);
    }

    public void createMapIntent(View view)
    {
        Uri uriAddress = Uri.parse("geo:0,0?q=12 Chasmante Close Steenberg, Cape Town 7947");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, uriAddress);
        mapIntent.setPackage("com.google.android.apps.maps");

        if(mapIntent.resolveActivity(getPackageManager()) != null)
        {
            startActivity(mapIntent);
        }
    }

    public void createEmailIntent(View view)
    {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);

        String uriText = "mailto:" + Uri.encode("info@blackcarpet.co.za") +
                "?subject=" + Uri.encode("Photography Query") +
                "&body=" + Uri.encode("\n\n\n\nSent From \nBlack Carpet Photography \nAndroid App");
        Uri uri = Uri.parse(uriText);
        emailIntent.setData(uri);
        startActivity(Intent.createChooser(emailIntent, "Send Email"));
    }


}
