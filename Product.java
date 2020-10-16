package SupplyDemand;

import java.util.ArrayList;

/**
 * This class will only be used in the Broker class. It will be a list of all products that are for sale, and each product will have all of its subscribers associated with it 
 */
public class Product {

	/**
	 * List of all subscribers that currently subscribe to this product object
	 */
	private ArrayList<Retailer> subscribers;

	/**
	 * type of product that this object is. i.e. banana
	 */
	private String productCategory;

	/**
	 * Constructor
	 */
	public Product(String prodCat) {
		subscribers = new ArrayList<>();
		productCategory = prodCat;
	}

	/**
	 * Adds a new retailer to the subscription list of a certain kind of product
	 */
	public void addSubscriber(Retailer retailer) {
		//begin
		boolean alreadyInList = false;
		int i = 0;
		
		while(i < subscribers.size() && alreadyInList == false) {
			
			Retailer tempRetailer = subscribers.get(i);
			if((retailer.getSubscriberName()).compareTo(tempRetailer.getSubscriberName()) == 0) {
				alreadyInList = true;
			}
			i++;
		}
		
		if(alreadyInList == false) {
			subscribers.add(subscribers.size(), retailer);
		}
		//end
	}

	/**
	 * Removes a retailer from the subscription list of a certain product
	 */
	public void removeSubscriber(String retailer) {
		//begin
		int i = 0;
		
		while(i < subscribers.size()) {
			
			Retailer tempRetailer = subscribers.get(i);
			if(retailer.compareTo(tempRetailer.getSubscriberName()) == 0) {
				subscribers.remove(i);
				break;
			}
			i++;
		}
		//end
		
	}
	
	/**
	 * returns the type of product that this individual object is
	 */
	public String getProdCat() {
		return productCategory;
		//return null;
	}
	
	/**
	 * Returns the full list of all current subscribers to this product category
	 */
	public ArrayList<Retailer> getSubscriberList() {
		return subscribers;
		//return null;
	}

}
