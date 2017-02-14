package com.etkiproject.table;

class menuItem{
	  int menuItemID;
	  String itemName;
	  String imageName;
	  double price;
	  
	  menuItem(int menuItemID, String itemName,String imageName,double price) {
		    this.menuItemID = menuItemID;
		    this.itemName = itemName;
		    this.imageName = imageName;
		    this.price = price;
		  }
	  menuItem(String itemName) {
		    this.itemName = itemName;
		  }
	 
	  @Override
	  public String toString() {
	    return itemName+""+"("+menuItemID+")";
	  }

	public int getMenuItemID() {
		return menuItemID;
	}

	public void setMenuItemID(int menuItemID) {
		this.menuItemID = menuItemID;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	}