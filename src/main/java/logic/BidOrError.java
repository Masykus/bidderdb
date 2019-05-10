package logic;

public class BidOrError {

    public static BidOrError NO_BID_ERROR = error(RequestErrorCode.NO_BID);

    public static BidOrError error(RequestErrorCode biddingError) {
        return new BidOrError(biddingError);
    }


    private final Long bidResult;
    private final RequestErrorCode biddingError;

    public BidOrError(long bidResult) {
        this.bidResult = bidResult;
        this.biddingError = null;
    }

    public BidOrError(RequestErrorCode biddingError) {
        this.bidResult = null;
        this.biddingError = biddingError;
    }

    public boolean hasBidResult() {
        return bidResult != null;
    }

    public Long getBidResult() {
        return bidResult;
    }

    public RequestErrorCode getBiddingError() {
        return biddingError;
    }

    public static BidOrError bid(long bid) {
        return new BidOrError(bid);
    }
}
