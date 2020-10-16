package SupplyDemand;

import java.util.ArrayList;
import java.util.List;

/**
 * This class would be the point of interaction of your system - it accepts input and provides aggregated output.
 */
public class SupplyDemand {
	
	/**
	 * Keeps track of all the producers that exist
	 */
	private ArrayList<Producer> producerList;

	/**
	 * Keeps track of all the retailers that exist
	 */
	private ArrayList<Retailer> retailerList;
	
	/**
	 * Class constructor - you may set up any needed objects and data here. Specification of constructor is optional - it is acceptable if you leave it blank.
	 */
	public SupplyDemand() {
		//begin
		producerList = new ArrayList<>();
		retailerList = new ArrayList<>();
		//end
	}

	/**
	 * This method accepts a single command and carry out the instruction given. You do not need to (and probably shouldn't) do everything in this method - delegate responsibilities to other classes.
	 */
	public void processInput(String command) {
		//begin
		String word = "";
		int i = 0;
		boolean processingInput = true;
		
		//loop that runs through whole command and breaks it apart
		while(processingInput == true && i < command.length()) {
			
			if(command.charAt(i) == ',' && i < command.length()) {
				
				i += 2;
				word = word.toLowerCase();
				//word = word.trim();
				boolean hasError = false;
				
				if(word.compareTo("publish") == 0 && checkIfHasCorrectFormat(command, "publish") == true) {
					String producer = "";
					String prodCat = "";
					String brandName = "";
					//loop that gets producer name from command line
					while(i < command.length() && command.charAt(i) != ',') {
						
						producer += command.charAt(i);
						i++;
					}
					producer = producer.toLowerCase();
					//producer = producer.trim();
					i += 2;
					
					//loop that gets the product category from command line
					while(i < command.length() && command.charAt(i) != ',') {	
						prodCat += command.charAt(i);
						i++;
					}
					prodCat = prodCat.toLowerCase();
					//prodCat = prodCat.trim();
					i += 2;
					
					//loop that gets brand name from command line
					while(i < command.length()) {
						if(command.charAt(i) != ',') {
							brandName += command.charAt(i);
						    i++;
						}
						else {
							hasError = true;
							i++;
						}
						
					}
					brandName = brandName.toLowerCase();
					//brandName = brandName.trim();
					if(hasError == false) {
						//adds producer to producerList
						addProducer(producer);
						//performs a publish for the given producer
						i = 0;
						boolean foundMatch = false;
						while(i < producerList.size() && foundMatch == false) {
							Producer tempProducer = producerList.get(i);
							if(producer.compareTo(tempProducer.getPublisherName()) == 0) {
								foundMatch = true;
								tempProducer.publish(brandName, prodCat);
								producerList.set(i, tempProducer); 
							}
							i++;
						}
					}
				}
				else if(word.compareTo("subscribe") == 0 && checkIfHasCorrectFormat(command, "subscribe") == true) { 
					String retailer = "";
					String prodCat = "";
					
					//loop that gets the retailer name
					while(i < command.length() && command.charAt(i) != ',') {	
						retailer += command.charAt(i);
						i++;
					}
					retailer = retailer.toLowerCase();
					//retailer = retailer.trim();
					i += 2;
					
					//loop that gets the product category
					while(i < command.length() && i < command.length()) {
						if(command.charAt(i) != ',') {
							prodCat += command.charAt(i);	
						    i++;
						}
						else {
							hasError = true;
							i++;
						}
											
						
					}
					prodCat = prodCat.toLowerCase();
					//prodCat = prodCat.trim();
					if(hasError == false) {
						addRetailer(retailer);
						
						i = 0;
						boolean foundMatch = false;
						while(i < retailerList.size() && foundMatch == false) {
							Retailer tempRetailer = retailerList.get(i);
							if(retailer.compareTo(tempRetailer.getSubscriberName()) == 0) {
								foundMatch = true;
								tempRetailer.subscribe(prodCat);
								retailerList.set(i, tempRetailer); //NOT SURE IF I OR I + 1 OR I - 1 ****************************************8
							}
							i++;
						}
					}
					
					
					//broker.subscribeCommand(retailer, prodCat);
				}
				else if(word.compareTo("unsubscribe") == 0 && checkIfHasCorrectFormat(command, "unsubscribe") == true) {
					
					String retailer = "";
					String prodCat = "";
					
					
					//loop that gets the retailer name
					while(i < command.length() && command.charAt(i) != ',') {
						retailer += command.charAt(i);						
						i++;
					}
					retailer = retailer.toLowerCase();
					retailer = retailer.trim();
					i += 2;
					
					//loop that gets the product category
					while(i < command.length()) {
						if(command.charAt(i) != ',') {
							prodCat += command.charAt(i);	
						    i++;
						}
						else {
							hasError = true;
							i++;
						}
					}
					prodCat = prodCat.toLowerCase();
					//prodCat = prodCat.trim();
					
					if(hasError == false) {
						i = 0;
						boolean foundMatch = false;
						while(i < retailerList.size() && foundMatch == false) {
							Retailer tempRetailer = retailerList.get(i);
							if(retailer.compareTo(tempRetailer.getSubscriberName()) == 0) {
								foundMatch = true;
								tempRetailer.unsubscribe(prodCat);
								retailerList.set(i, tempRetailer); //NOT SURE IF I OR I + 1 OR I - 1 ****************************************8
							}
							i++;
						}
					}
				}
				
				processingInput = false;
			}
			else {
				word += command.charAt(i);				
			}
			i++;
		}
		
		//end
	}

	/**
	 * After each round of execution, this method would be called to fetch all output lines, if there are any. The lines must be ordered by the time they are received.
	 */
	public List<String> getAggregatedOutput() {
		//begin
		List<String> temp = Broker.getOutput();
		return temp;
		//end
		//return null;
	}

	/**
	 * Finally, this method would be called to clear all saved information in the system, so that information from previous round would not be carried to next round. After calling this method the system should be effectively starting anew.
	 */
	public void reset() {
		//begin
		producerList.clear();
		retailerList.clear();
		Broker.resetLists();
		//end
	}
	
	/**
	 * Checks to see if this producer is in our producerList. If it is, then nothing happens. If it does not, then we add it
	 */
	public void addProducer(String producer) {
		//begin
		int i = 0;
		boolean foundMatch = false;
		
		//checks if this producer already exists. If it does not, we add it to our list
		while(i < producerList.size() && foundMatch == false) {
			Producer tempProducer = producerList.get(i);
			
			if(producer.compareTo(tempProducer.getPublisherName()) == 0) {
				foundMatch = true;
			}
			i++;
		}
		if(foundMatch == false) {
			Producer newProducer = new Producer(producer);
			producerList.add(producerList.size(), newProducer);
		}
		//end
	}

	/**
	 * Checks to see if this retailer is in our retailerList. If it is, then nothing happens. If it does not, then we add it
	 */
	public void addRetailer(String retailer) {
		//begin
		boolean foundMatch = false;
		int i = 0;
		
		//checks if this retailer is in our database. If it is, we subscribe them to this product
		while(i < retailerList.size() && foundMatch == false) {
			Retailer tempRetailer = retailerList.get(i);
			if(retailer.compareTo(tempRetailer.getSubscriberName()) == 0) {
				foundMatch = true;
				//tempRetailer.subscribe(prodCat);	
				retailerList.set(i, tempRetailer);
			}
			i++;
		}
		
		//if that retailer does not exist in our database we add it to our list and subscribe them to the product
		if(foundMatch == false) {
			Retailer newRetailer = new Retailer(retailer);
			//newRetailer.subscribe(prodCat);
			retailerList.add(retailerList.size(), newRetailer); 
			
		}
		//end
	}
	
	/**
	 * Takes in the input command and makes sure that it has a valid format
	 */
	public boolean checkIfHasCorrectFormat(String command, String commandType) {
		//begin
		boolean isCorrect = true;
		int i = 0;
		int counter = 0;
		while(i < command.length()) {
			if(command.charAt(i) == ',') {
				counter++;
				if( i < command.length() - 2) {
					if(command.charAt(i + 1) == ',' || command.charAt(i + 2) == ',') {
						isCorrect = false;
					}
					
				}
			}
			i++;
		}
		
		if(commandType.compareTo("publish") == 0) {
			if(counter != 3) {
				isCorrect = false;
				
			}
		}
		else if(commandType.compareTo("subscribe") == 0) {
			if(counter != 2) {
				isCorrect = false;
				
			}
		}
		else if(commandType.compareTo("unsubscribe") == 0) {
			if(counter != 2) {
				isCorrect = false;
				
			}
		}
		
		return isCorrect;
		//end
		//return false;
	}

}
