package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.test.AndroidTestCase;
import android.text.TextUtils;
import android.util.Log;

import com.example.maayy.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by maayy on 26-07-2016.
 */
public class AsyncAndroidTest extends AndroidTestCase {
    CountDownLatch signal = null;
    String joke = null;
    Exception mError = null;

    public void testAsyncResponse() throws InterruptedException{

        EndpointsAsyncTask task = new EndpointsAsyncTask(getContext());
        task.setListener(new EndpointsAsyncTask.JokeGetTaskListener() {
            @Override
            public void onComplete(String jsonString, Exception e) {
                joke = jsonString;
                mError = e;
                signal.countDown();
            }
        }).execute();

        signal.await();

        assertNull(mError);
        assertFalse(TextUtils.isEmpty(joke));
    }


    @Override
    protected void setUp() throws Exception {
        signal = new CountDownLatch(1);
    }

    @Override
    protected void tearDown() throws Exception {
        signal.countDown();
    }
}

class EndpointsAsyncTask extends AsyncTask<Void, Void, String> {
    private MyApi myApiService = null;
    private Context mContext;

    private JokeGetTaskListener mListener = null;

    public EndpointsAsyncTask(Context context){
        mContext = context;
    }

    public EndpointsAsyncTask setListener(JokeGetTaskListener listener) {
        this.mListener = listener;
        return this;
    }

    public static interface JokeGetTaskListener {
        public void onComplete(String joke, Exception e);
    }

    @Override
    protected String doInBackground(Void... params) {
        if (myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl(mContext.getResources().getString(R.string.server_address))
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        try {
            return myApiService.tellJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        Log.e("resTest", result);
    }
}

