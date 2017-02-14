package com.etkiproject.table;

class restaurantTable{
	  int tableID;
	  String tableName;
	  
	  restaurantTable(int tableID, String tableName) {
		    this.tableID = tableID;
		    this.tableName = tableName;
		  }

	public int getTableID() {
		return tableID;
	}

	public void setTableID(int tableID) {
		this.tableID = tableID;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	}