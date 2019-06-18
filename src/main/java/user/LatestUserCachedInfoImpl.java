package user;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LatestUserCachedInfoImpl implements LatestUserCachedInfo {
    long lastInitTime = (new Date()).getTime();
    final long MAX_CAPACITY = 10000;
    final float REMOVE_AFTER_CLEANING_INDEX = 0.4F;
    final long LATENCY_MSEC = 1000 * 60 * 5;

    Map<String, Long> whiteList = new HashMap<>();
    Map<String, Long> blackList = new HashMap<>();

    public Boolean isInBlackList(String key) {
        checkAndFixCapacityOverhead();
        if(whiteList.containsKey(key)){
            return false;
        }else if(blackList.containsKey(key)) {
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
        long currentTime = (new Date()).getTime();
        if((whiteList.size() + blackList.size()) > MAX_CAPACITY){
            filterByTH(currentTime - Math.round((currentTime - lastInitTime) * REMOVE_AFTER_CLEANING_INDEX));
        }else if((currentTime - lastInitTime) > LATENCY_MSEC){
            filterByTH(currentTime - LATENCY_MSEC);
        }
    }

    private void filterByTH(long thTime){
        whiteList.values().removeIf(value -> value < thTime);
        blackList.values().removeIf(value -> value < thTime);

        lastInitTime = (new Date()).getTime();
    }
}
