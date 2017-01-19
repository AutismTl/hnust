package tools;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.example.tl.hnust.R;

/**
 * Created by gesangdianzi on 2016/12/11.
 */
public class Title1 extends LinearLayout {
    public  Title1(Context context, AttributeSet attrs){
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.title1,this);
    }
}