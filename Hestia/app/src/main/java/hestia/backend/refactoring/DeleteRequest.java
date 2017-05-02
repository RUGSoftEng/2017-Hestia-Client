package hestia.backend.refactoring;

import java.net.HttpURLConnection;

import hestia.backend.BackendInteractor;

public class DeleteRequest extends Request {

    public DeleteRequest(String path) {
        super("DELETE", path);
    }

    @Override
    protected void perform(HttpURLConnection urlConnection) {

    }

    @Override
    protected void onPostExecute(HttpURLConnection urlConnection) {
        BackendInteractor.getInstance().updateDevices();
    }
}
