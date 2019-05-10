package logic;

import messages.Messages;

public class ConstBidLogic implements BidLogic {


    public BidOrError getBid(BidStrategy bidStrategy, Messages.BidRequestWrapper bidRequestWrapper) {
        return new BidOrError(bidStrategy.getConstBid());
    }
}
