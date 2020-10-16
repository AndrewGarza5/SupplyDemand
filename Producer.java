package SupplyDemand;

/**
 * Producer class implements and fulfills all of the methods of the publisher interface. It adds attributes & operations for giving producer objects a name it can identify with
 */
public class Producer implements IPublisher {
	
	/**
	 * Name of producer/company
	 */
	private String producerName;

	/**
	 * constructor
	 */
	public Producer(String producer) {
		producerName = producer;
	}

	/**
	 * @see SupplyDemand.IPublisher#publish(String, String)
	 */
	public void publish(String brand, String prodCat) {
		//begin
		Broker.publishCommand(producerName, prodCat, brand);
		//end
	}


	/**
	 * returns the given name of the publisher object as a String
	 */
	public String getPublisherName() {
		return producerName;
		//return null;
	}

}
