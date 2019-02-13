package in.dtnl;

import okhttp3.*;

import java.io.IOException;
import java.util.Map;

/**
 * @author : yyyxxxxxx
 * @since : 2019-02-13
 */
public class HttpUtil {
    public static String post(String url, Map<String, String> param) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        param.forEach(builder::add);
        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();

        Call call = okHttpClient.newCall(request);
        Response response = call.execute();
        return response.body().string();
    }

    public static String get(String url) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        Response response = call.execute();
        return response.body().string();
    }

    public static int head(String url) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url).method("HEAD", null)
                .build();
        Call call = okHttpClient.newCall(request);
        Response response = call.execute();
        return response.code();
    }
}
