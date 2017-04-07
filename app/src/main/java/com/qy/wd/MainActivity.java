package com.qy.wd;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.qy.wd.test.service.ApiService;
import com.qy.wd.test.service.RetrofitClient;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.adapter.rxjava.Result;


public class MainActivity extends AppCompatActivity {


    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RetrofitClient client = new RetrofitClient();

        ApiService service = client.create(ApiService.class);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("method","upload");
        map.put("username","z12589");
        map.put("password","8052335");
        map.put("codetype","3005");
        map.put("appid","3333");
        map.put("appkey","377d9d6c633b1380afe3bd538d0c9cab");
        upLoad(service,map);
    }


    /**
     * 上传图片
     * create by weiang at 2016/5/20 17:33.
     */
    private void upLoad(ApiService service,Map map) {
        String filePath = "file:///android_asset/aaaaa.png";
        File file = new File(filePath);//filePath 图片地址
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);//表单类型
//                .addFormDataPart(ParamKey.TOKEN, token);//ParamKey.TOKEN 自定义参数key常量类，即参数名
        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        builder.addFormDataPart("file", file.getName(), imageBody);//imgfile 后台接收图片流的参数名

        List<MultipartBody.Part> parts = builder.build().parts();


        service.uploadMemberIcon("api.php?",map,parts).enqueue(new Callback<Result<String>>() {//返回结果
            @Override
            public void onResponse(Call<Result<String>> call, Response<Result<String>> response) {
                Toast.makeText(MainActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Result<String>> call, Throwable t) {
                Toast.makeText(MainActivity.this,"失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
