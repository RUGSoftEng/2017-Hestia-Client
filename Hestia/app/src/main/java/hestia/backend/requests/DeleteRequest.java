package hestia.backend.requests;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * This class does a DELETE request. It extends Request class, but does not do
 * additional IO actions, therefore the methods will be left empty.
 * @see Request
 */

public class DeleteRequest extends Request {

    public DeleteRequest(String path) {
        super("DELETE", path);
    }

    @Override
    protected void setDoIO(HttpURLConnection connector) {}

    @Override
    protected void performIOAction(HttpURLConnection connector) throws IOException {}
}
