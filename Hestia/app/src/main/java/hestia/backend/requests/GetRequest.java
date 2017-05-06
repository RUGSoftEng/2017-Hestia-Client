package hestia.backend.requests;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;

public abstract class GetRequest<T> extends Request {
    private T returnValue;
    private final String TAG = "GetRequest";

    public GetRequest(String path) {
        super("GET", path);
    }

    @Override
    protected void setDoIO(HttpURLConnection urlConnection) {
        urlConnection.setDoInput(true);
    }

    @Override
    protected void performIOAction(HttpURLConnection urlConnection) throws IOException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        this.registerTypeAdapter(gsonBuilder);
        Gson gson = gsonBuilder.create();

        BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        Type returnType = this.getReturnType();
        this.returnValue = gson.fromJson(gson.newJsonReader(reader), returnType);
        Log.d(TAG, "RETURN VALUE IS: " + returnValue.toString());
        reader.close();
    }

    protected abstract void registerTypeAdapter(GsonBuilder gsonBuilder);

    protected abstract Type getReturnType();

    @Override
    protected abstract void onPostExecute(HttpURLConnection urlConnection);

    protected T getReturnValue() {
        return this.returnValue;
    }
}
