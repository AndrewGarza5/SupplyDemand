package SupplyDemand;

/**
 * Retailer class implements and fulfills all of the methods of the subscriber interface. It adds attributes & operations for giving retailer objects a name it can identify with
 */
public class Retailer implements ISubscriber {

	/**
	 * Name of retailer/store
	 */
	private String retailerName;
	

	/**
	 * Class constructor
	 */
	public Retailer(String name) {
		retailerName = name;
	}


	/**
	 * @see SupplyDemand.ISubscriber#subscribe(String)
	 */
	public void subscribe(String prodCat) {
		//begin
		Broker.subscribeCommand(retailerName, prodCat);
		//end
		
	}


	/**
	 * @see SupplyDemand.ISubscriber#unsubscribe(String)
	 */
	public void unsubscribe(String prodCat) {
		//begin
		Broker.unsubscribeCommand(retailerName, prodCat);
		//end
	}


	/**
	 * Returns the given name of the individual subscriber object
	 */
	public String getSubscriberName() {
		//begin
		return retailerName;
		//end
		//return null;
	}

}
