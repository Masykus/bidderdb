package logic;

import com.google.common.util.concurrent.SettableFuture;
import messages.Messages;
import user.*;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class UserBlackListLogic implements BidLogic {
    private final UserBlackListService userBlackListService;
    private final LatestUserCachedInfo latestUserCachedInfo;

    public UserBlackListLogic(UserBlackListService userBlackListService) {
        this.userBlackListService = userBlackListService;
        this.latestUserCachedInfo = new LatestUserCachedInfoConcurrentImpl();
    }

    //TODO catch logic should be encapsulated into UserBlackListService. I can'd do it without actual implementation, only interface is available
    public BidOrError getBid(BidStrategy bidStrategy, Messages.BidRequestWrapper bidRequestWrapper) {
        SettableFuture<BidOrError> bidOrErrorSettableFuture = SettableFuture.create();

        Boolean isInBlackList = latestUserCachedInfo.isInBlackList(bidRequestWrapper.getUserId());

        if(isInBlackList == null){
            userBlackListService.get(bidRequestWrapper.getUserId(), new AsyncDataListener<Optional<UserData>>() {
                @Override
                public void onSuccess(Optional<UserData> optionalUserData) {
                    if (optionalUserData.isPresent()) {
                        latestUserCachedInfo.putToBlackListCache(bidRequestWrapper.getUserId());
                        bidOrErrorSettableFuture.set(BidOrError.error(RequestErrorCode.USER_BLACKLISTED));
                    } else {
                        latestUserCachedInfo.putToWhiteListCache(bidRequestWrapper.getUserId());
                        bidOrErrorSettableFuture.set(BidOrError.bid(bidStrategy.getConstBid()));
                    }
                }

                @Override
                public void onFailure(String exception) {
                    bidOrErrorSettableFuture.set(BidOrError.error(RequestErrorCode.AUCTION_NO_BID_DATA));
                }
            });
        }else if(isInBlackList){
            bidOrErrorSettableFuture.set(BidOrError.error(RequestErrorCode.USER_BLACKLISTED));
        }else{
            bidOrErrorSettableFuture.set(BidOrError.bid(bidStrategy.getConstBid()));
        }

        try {
            return bidOrErrorSettableFuture.get(100, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
