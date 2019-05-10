package logic;

public interface BidLogicFactory {
    BidLogic getByStrategy(BidStrategy bidStrategy);
}
