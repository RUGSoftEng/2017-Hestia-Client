package hestia.backend.refactoring;

import hestia.backend.BackendInteractor;

public class DeleteRequest extends Request<Integer> {

    public DeleteRequest(String path) {
        super("DELETE", path);
    }

    @Override
    protected void onPostExecute(Integer result) {
        BackendInteractor.getInstance().updateDevices();
    }
}
