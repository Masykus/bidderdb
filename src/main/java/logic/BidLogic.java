package logic;

import messages.Messages;

public interface BidLogic {
    BidOrError getBid(BidStrategy bidStrategy, Messages.BidRequestWrapper bidRequestWrapper);
}
