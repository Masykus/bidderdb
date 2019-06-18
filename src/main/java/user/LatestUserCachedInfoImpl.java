package user;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LatestUserCachedInfoImpl implements LatestUserCachedInfo {
    long lastInitTime = (new Date()).getTime();
    final long MAX_CAPACITY = 10000;
    final float REMOVE_AFTER_CLEANING_INDEX = 0.4F;

    Map<String, Long> whiteList = new HashMap<>();
    Map<String, Long> blackList = new HashMap<>();

    public Boolean isInBlackList(String key) {
        if(whiteList.containsKey(key)){
            whiteList.put(key, (new Date()).getTime());
            return false;
        }else if(blackList.containsKey(key)) {
            blackList.put(key, (new Date()).getTime());
            return true;
        }else{
            return null;
        }
    }

    public void putToBlackListCache(String key) {
        blackList.put(key, (new Date()).getTime());
        checkAndFixCapacityOverhead();
    }

    public void putToWhiteListCache(String key) {
        whiteList.put(key, (new Date()).getTime());
        checkAndFixCapacityOverhead();
    }

    private void checkAndFixCapacityOverhead(){
        if((whiteList.size() + blackList.size()) > MAX_CAPACITY){
            long currentTime = (new Date()).getTime();
            long thTime = currentTime - Math.round((currentTime - lastInitTime) * REMOVE_AFTER_CLEANING_INDEX);
            whiteList.values().removeIf(value -> value < thTime);
            blackList.values().removeIf(value -> value < thTime);
        }
    }
}
