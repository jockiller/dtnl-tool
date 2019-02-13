package in.dtnl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 上传文件
 *
 * @author : yxx
 * @since : 2019-02-01
 */
public class UploadImgUtil {

    /**
     * 上传图片文件到 smms
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static String upload2SmMs(File file) throws IOException {
        String fileName = file.getName();
        String extension = FilenameUtils.getExtension(fileName);
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/" + extension), file);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("smfile", fileName, fileBody)
                .addFormDataPart("ssl", "1")
                .addFormDataPart("format", "json")
                .build();
        Request request = new Request.Builder()
                .url("https://sm.ms/api/upload")
                .post(requestBody)
                .removeHeader("User-Agent")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:0.9.4)")
                .build();

        final OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient = httpBuilder
                //设置超时
                .connectTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(150, TimeUnit.SECONDS)
                //TODO 设置proxy
                .build();
        Response execute = okHttpClient.newCall(request).execute();
        String resStr = execute.body().string();
        JSONObject res = JSON.parseObject(resStr);
        if ("success".equals(res.getString("code"))) {
            return res.getJSONObject("data").getString("url");
        } else {
            throw new RuntimeException(resStr);
        }
    }

}
