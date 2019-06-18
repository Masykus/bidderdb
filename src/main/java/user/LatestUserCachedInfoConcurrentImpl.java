package user;

//TODO
/*
This approach also have drawbacks, because we need to use synchronised methods.
To change it we need redesign project. I don't have full logic, so I can't do it now.
As for me better solution will be have long living actor objects instead of one time actors(they terminates after just one call).
Using hash function we can sent records with same user id to same actor. Each actor can have his own list of cached users, this list could be non thread safe in that case.
 */
public class LatestUserCachedInfoConcurrentImpl extends LatestUserCachedInfoImpl {
    synchronized public Boolean isInBlackList(String key) {
        return super.isInBlackList(key);
    }

    synchronized public void putToBlackListCache(String key) {
        super.putToBlackListCache(key);
    }

    synchronized public void putToWhiteListCache(String key) {
        super.putToWhiteListCache(key);
    }
}
