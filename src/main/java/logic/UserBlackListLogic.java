package logic;

import com.google.common.util.concurrent.SettableFuture;
import messages.Messages;
import user.AsyncDataListener;
import user.UserData;
import user.UserBlackListService;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class UserBlackListLogic implements BidLogic {
    private final UserBlackListService userBlackListService;

    public UserBlackListLogic(UserBlackListService userBlackListService) {
        this.userBlackListService = userBlackListService;
    }


    public BidOrError getBid(BidStrategy bidStrategy, Messages.BidRequestWrapper bidRequestWrapper) {

        SettableFuture<BidOrError> bidOrErrorSettableFuture = SettableFuture.create();
        userBlackListService.get(bidRequestWrapper.getUserId(), new AsyncDataListener<Optional<UserData>>() {
            @Override
            public void onSuccess(Optional<UserData> optionalUserData) {
                if (optionalUserData.isPresent()) {
                    bidOrErrorSettableFuture.set(BidOrError.error(RequestErrorCode.USER_BLACKLISTED));
                } else {
                    bidOrErrorSettableFuture.set(BidOrError.bid(bidStrategy.getConstBid()));
                }
            }

            @Override
            public void onFailure(String exception) {
                bidOrErrorSettableFuture.set(BidOrError.error(RequestErrorCode.AUCTION_NO_BID_DATA));
            }
        });

        try {
            return bidOrErrorSettableFuture.get(100, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
