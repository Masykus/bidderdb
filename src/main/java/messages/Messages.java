package messages;

import akka.actor.ActorRef;
import logic.RequestErrorCode;

public class Messages {
    public class BidRequestWrapper {
        private String id;
        private String userId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }

    public static class RequestFail {

        private final RequestErrorCode biddingError;

        public RequestFail(RequestErrorCode biddingError) {
            this.biddingError = biddingError;
        }
    }


    public static class BidderResponse {
        private final long bid;
        private final String requestId;

        public BidderResponse(long bid, String requestId) {
            this.bid = bid;
            this.requestId = requestId;
        }
    }


}
