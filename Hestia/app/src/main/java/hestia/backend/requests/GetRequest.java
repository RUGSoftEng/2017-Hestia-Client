package hestia.backend.requests;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;

/**
 * This is an abstract class used to do a GET request.
 * @param <T> the type of the requested object
 * @see Request
 */

abstract class GetRequest<T> extends Request {
    private T returnValue;

    GetRequest(String path) {
        super("GET", path);
    }

    @Override
    protected void setDoIO(HttpURLConnection connector) {
        connector.setDoInput(true);
    }

    /**
     * Retrieves a JSON file from the Server containing the object of the requested type T.
     * @param connector The connector between the Client and the Server
     * @throws IOException exception is caught in the body of the Request superclass.
     * @see Request
     */
    @Override
    protected void performIOAction(HttpURLConnection connector) throws IOException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        this.setRegisterTypeAdapter(gsonBuilder);
        Gson gson = gsonBuilder.create();

        BufferedReader reader = new BufferedReader(new InputStreamReader(connector.getInputStream()));
        Type returnType = this.getReturnType();
        this.returnValue = gson.fromJson(gson.newJsonReader(reader), returnType);
        Log.d("GetRequest", "RETURN VALUE IS: \n" + returnValue.toString());
        reader.close();
    }

    /**
     * Sets the register type adapter of gsonBuilder object to accommodate the type
     * of the expected object
     * @param gsonBuilder the object that will convert the JSON file to the expected type
     */
    protected abstract void setRegisterTypeAdapter(GsonBuilder gsonBuilder);

    /**
     * Returns the type of the expected object, i.e. the class of the object.
     * @return Type of the expected object
     */
    protected abstract Type getReturnType();

    @Override
    protected abstract void onPostExecute(HttpURLConnection connector);

    protected T getReturnValue() {
        return this.returnValue;
    }
}
