package tools;

/**
 * Created by gesangdianzi on 2016/10/29.
 */
public interface HttpCallbackListener {
    void onFinish(String response);

    void onError(Exception e);
}
