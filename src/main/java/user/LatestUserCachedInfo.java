package user;

public interface LatestUserCachedInfo {
    Boolean isInBlackList(String key);
    void putToBlackListCache(String key);
    void putToWhiteListCache(String key);
}
