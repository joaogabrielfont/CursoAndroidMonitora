package br.com.monitoratec.app;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import br.com.monitoratec.app.domain.GitHubStatusApi;
import br.com.monitoratec.app.domain.entity.Status;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public ImageView temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        temp = (ImageView)findViewById(R.id.imageViewStatus);
        final Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(GitHubStatusApi.BASE_URL)
                .build();

        GitHubStatusApi statusApiImpl = retrofit.create(GitHubStatusApi.class);
        statusApiImpl.lastMessage().enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                if (response.isSuccessful()){
                    Status status = response.body();
                    if (status.status.equals("good")){
                        temp.setBackgroundColor(Color.GREEN);
                    }
                    Toast.makeText(MainActivity.this, status.status, Toast.LENGTH_LONG).show();
                }
                else {
                    //TODO tratar o errorBody
                }
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                //TODO tratar onFailure
            }
        });


        //comentario
    }
}
