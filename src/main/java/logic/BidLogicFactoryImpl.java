package logic;

import user.UserBlackListService;

public class BidLogicFactoryImpl implements BidLogicFactory {
    private final UserBlackListService userBlackListService;

    public BidLogicFactoryImpl(UserBlackListService userBlackListService) {
        this.userBlackListService = userBlackListService;
    }

    public BidLogic getByStrategy(BidStrategy bidStrategy) {
        switch (bidStrategy.getLogicType()){
            case CONST_BID:
                return new ConstBidLogic();
            case USER_BLACKLIST:
                return new UserBlackListLogic(userBlackListService);


            default:throw new RuntimeException("Unmatched logic type - check your code");
        }

    }
}
