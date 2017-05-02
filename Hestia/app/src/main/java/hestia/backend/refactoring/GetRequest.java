package hestia.backend.refactoring;

public class GetRequest<T> extends Request<T> {

    public GetRequest(String requestType, String path) {
        super(requestType, path);
    }

    @Override
    protected T doInBackground(Void... params) {
        T result = super.doInBackground(params);

        return result;
    }

    @Override
    protected void onPostExecute(T result) {

    }
}
