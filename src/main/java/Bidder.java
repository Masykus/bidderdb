import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import logic.*;
import messages.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Bidder extends UntypedActor {
    private static final Logger logger = LoggerFactory.getLogger(Bidder.class);

    private final BidStrategy bidStrategy;
    private final BidLogicFactory bidLogicFactory;

    public Bidder(BidStrategy bidStrategy, BidLogicFactory bidLogicFactory) {
        this.bidStrategy = bidStrategy;
        this.bidLogicFactory = bidLogicFactory;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        ActorRef sender = getSender();

        if (message instanceof Messages.BidRequestWrapper) {
            Messages.BidRequestWrapper bidRequestWrapper = (Messages.BidRequestWrapper) message;
            getBidByFactory(sender, bidRequestWrapper);
        }

        // Remove that actor
        getContext().stop(getSelf());
    }

    private void getBidByFactory(ActorRef sender, Messages.BidRequestWrapper bidRequestWrapper) throws Exception {
        try {

            BidLogic bidLogic = bidLogicFactory.getByStrategy(bidStrategy);
            BidOrError bidOrError = bidLogic.getBid(bidStrategy, bidRequestWrapper);

            if (bidOrError.hasBidResult()) {
                Long bidResult = bidOrError.getBidResult();
                sendResult(bidResult, bidRequestWrapper, sender);

            } else {
                RequestErrorCode biddingError = bidOrError.getBiddingError();
                sendErrorMessage(new Messages.RequestFail(biddingError), sender);
            }
        } catch (Exception e) {
            logger.error("Error getting bid", e);
            throw e;
        }
    }

    private void sendErrorMessage(Messages.RequestFail error, ActorRef sender) {
        sender.tell(error, getSelf());
    }

    private void sendResult(long bid, Messages.BidRequestWrapper bidRequestWrapper, ActorRef sender) {
        Messages.BidderResponse bidderResponse = new Messages.BidderResponse(bid, bidRequestWrapper.getId());
        sender.tell(bidderResponse, getSelf());
    }

}


