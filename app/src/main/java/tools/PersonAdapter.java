package tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tl.hnust.R;

import java.util.List;

/**
 * Created by gesangdianzi on 2016/10/27.
 */
public class PersonAdapter extends ArrayAdapter<Person>{
    private int resourceId;
    private String[] image;
    private Context context;
    public  PersonAdapter(Context context, int textViewResourceId, List<Person> objects,String[] image){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
        this.context=context;
        this.image = image;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Person person=getItem(position);
        View view;
        if(convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,null);

        }else{
            view=convertView;
        }
        ImageView personImage=(ImageView)view.findViewById(R.id.person_image);
        TextView personName=(TextView)view.findViewById(R.id.person_name);
        TextView personId=(TextView)view.findViewById(R.id.person_id);
        personName.setText(person.getName());
        String s = Integer.toString(person.getId());
        personId.setText(s);
        Glide
                .with(context)
                .load(image[position])
                .error(R.mipmap.ic_launcher)
                .crossFade()
                .into(personImage);
        return view;
    }
}
