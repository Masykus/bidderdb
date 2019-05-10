package user;


import java.util.Optional;

public interface UserBlackListService {
    void get(String key, AsyncDataListener<Optional<UserData>> asyncDataListener);
}
