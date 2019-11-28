package za.co.bogote.blackcarpetphotography;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;

import java.util.ArrayList;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class StaggeredRecyclerViewAdapter extends RecyclerView.Adapter<StaggeredRecyclerViewAdapter.ViewHolder> {

    //TODO: Remove log tag when done
    private static final String TAG = "StaggeredRecyclerViewAd";

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImagesUrls = new ArrayList<>();
    private Context mContext;

    public StaggeredRecyclerViewAdapter(Context context, ArrayList<String> names, ArrayList<String> imagesUrls) {
        mNames = names;
        mImagesUrls = imagesUrls;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_grid_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //TODO: Remove onBindViewHolder Log Tag
        Log.d(TAG, "onBindViewHolder: Method Called");

        DrawableCrossFadeFactory factory =
                new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build();

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_image_24dp);

        Glide.with(mContext)
                .load(mImagesUrls.get(position))
                .transition(withCrossFade(factory))
                .thumbnail(0.1f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(requestOptions)
                .into(holder.image);

        holder.name.setText(mNames.get(position));

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Log.d(TAG, "onClick: Clicked on Image " + mNames.get(position));
                Toast.makeText(mContext, mNames.get(position), Toast.LENGTH_SHORT).show();*/
                Intent fullScreenIntent = new Intent(mContext, FullScreenActivity.class);
                fullScreenIntent.putExtra("image_url", mImagesUrls.get(position));
                fullScreenIntent.putExtra("image_name", mNames.get(position));
                mContext.startActivity(fullScreenIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mImagesUrls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            this.image = itemView.findViewById(R.id.image);
            this.name = itemView.findViewById(R.id.name);
        }
    }
}
