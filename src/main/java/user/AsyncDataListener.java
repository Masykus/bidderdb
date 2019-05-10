package user;

/**
 * Created by AxS on 27/12/2015.
 */
public interface AsyncDataListener<Result> {
    void onSuccess(Result result);

    void onFailure(String exception);
}
