package commin.pro.lectureschedule.widget;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by user on 2016-01-04.
 */
public class DialogProgress extends AsyncTask<DialogProgress.ProgressTaskIf, String, Object> {

    public interface ProgressTaskIf {

        public Object run() throws Exception;

    }

    private static ProgressDialog progressDialog;

    public static Object run(Context context, ProgressTaskIf task) throws Exception {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        Object result = null;
        try {
            result = new DialogProgress().execute(task).get();
            if (result != null && result instanceof Exception) {
                throw (Exception) result;
            }
            return result;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    protected void onPreExecute() {
        if (progressDialog != null) {
            progressDialog.show();
        }

        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(ProgressTaskIf... params) {
        if (params != null && params.length == 1) {
            try {
                return params[0].run();
            } catch (Exception e) {
                return e;
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Object o) {
        progressDialog.dismiss();
    }

}