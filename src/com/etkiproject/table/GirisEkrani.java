package com.etkiproject.table;


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.Toast;

public class GirisEkrani extends ActionBarActivity {
	private String[] meals,beverages,desserts;
	private String[] quantityMeal={"1","2","3","4","5"};
	menuItem[] item;
	restaurantTable[] rTable,restTable;
	Button eksi,arti;
	String[] tableName=null,menuName = null,mealList=null,beveragesList=null,dessertList=null;
	PopupWindow popupWindow;
	menuItem[] mealsMenu,beveragesMenu,dessertsMenu;
	Spinner role,tableSpinner;
	ListView list;
	Bundle bundle= new Bundle();

    private static final String SOAP_ACTION_getTableListData = "http://tempuri.org/getTableListData";
    private static final String SOAP_ACTION_addOrder = "http://tempuri.org/addOrder";

    private static final String OPERATION_NAME_getTableListData = "getTableListData";
    
    private static final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";
    
    private static final String SOAP_ADDRESS = "http://etkiproject-001-site1.anytempurl.com/mywebservice.asmx";
    
    
    TabHost host;
    TabHost.TabSpec spec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.girisekrani);
        //arkadan db verilerini çekiyo
        AsyncCallWS task = new AsyncCallWS();
        task.execute();
        
    }

	
    private restaurantTable[] getRestaurantTable(){
		//menuItem Objelerimiz array halinde
		
		
		SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME_getTableListData);
    	/* aşağıdakiler webservice method parametreleri
    	 * 
    	 */

    	
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE  androidHttpTransport = new HttpTransportSE(SOAP_ADDRESS);
    	try {
    		
           androidHttpTransport.call(SOAP_ACTION_getTableListData, envelope);

        
        SoapObject a = (SoapObject) envelope.bodyIn;
        Object property = a.getProperty(0);
		Log.d("table........ ",property.toString()+"");
		// burdanda içine ulaşabiliyon uzunluk yada variableları görebilirsin
		SoapObject info = (SoapObject) property;
        int RCount=info.getPropertyCount();
        Log.d("table RCount",RCount+"");
        rTable = new restaurantTable[RCount+1];
            if (property instanceof SoapObject) {
                Object tableID =  info.getProperty(0);
                SoapObject infoID = (SoapObject) tableID;
                Object tableName =  info.getProperty(1);
                SoapObject infoTableName = (SoapObject) tableName;
                
        		
        		int j=0;
                int Count=infoID.getPropertyCount();
                Log.d("table Count",Count+"");
                rTable = new restaurantTable[Count+1];
                for (j = 0; j < Count; j++) {
                    if (property instanceof SoapObject) {
                        int tID = Integer.parseInt(infoID.getProperty(j).toString());
                        String tName = infoTableName.getProperty(j).toString();
                        rTable[j] = new restaurantTable(tID,tName);
                       Log.d("table  itemName   ","ıd:"+rTable[j].getTableID()+" name:"+rTable[j].getTableName());
                    }
                } 
            }
         } catch (Exception e) {
           e.printStackTrace();
           //Toast.makeText(getBaseContext(), e.getMessage()+" ddd", Toast.LENGTH_LONG).show();
           Log.i("", e.getMessage()+" ddd");
         }
		
		return rTable;     
    }
	
	private class AsyncCallWS extends AsyncTask<Void, Void, Void> {
		ProgressDialog progDailog;
        protected Void doInBackground(Void... params) {
            Log.i("", "doInBackground");
            	//responseWaiter(1);
            
            //servis methodları data çekildi db den 
            restTable=getRestaurantTable();
            
            runOnUiThread(new Runnable() {// viewde değişiklik için ana thread kullanman lazım bu şekilde kullandık.
			     @SuppressWarnings("deprecation")
				@Override
			     public void run() {
			    	 //restaurantTable classından string arreyine name atar
			    	 int k =0;
			    	 tableName = new String[restTable.length];
				    	k=0;
				            while(k<restTable.length-1){
				            	tableName[k]=restTable[k].getTableName();
				            	k++;
				            }
			            //table listesi
			            list=(ListView) findViewById(R.id.tableList);
			            ArrayAdapter<String> adapter2=new ArrayAdapter<String>(GirisEkrani.this,R.layout.single_row, R.id.textView1, tableName);
			            list.setAdapter(adapter2);			           			            
			            list.setOnItemClickListener(new onItemClickListener() {
			           	 
			                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
			                	String[] separated = list.getItemAtPosition(position).toString().trim().split(" ");
					            int tableID=Integer.parseInt(separated[1]); 
					            Log.i("tableID",""+tableID);
			                	//addOrder(tableID);
			                	Toast.makeText(getBaseContext(), list.getItemAtPosition(position).toString().trim(), Toast.LENGTH_SHORT).show();
			                	Intent myintent=new Intent(arg0.getContext(), MainActivity.class);
			    				bundle.putString("tableName", list.getItemAtPosition(position).toString().trim());
			    				myintent.putExtras(bundle);
			    				startActivityForResult(myintent, 0);
			                }
			             });
			            
			            
			     }
			});
            
            return null;
        }
        protected void onPreExecute() {
            Log.i("", "onPreExecute");
            progDailog = new ProgressDialog(GirisEkrani.this);
            progDailog.setMessage("Loading...");
            progDailog.setIndeterminate(false);
            progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDailog.setCancelable(true);
            progDailog.show();
        }
        protected void onPostExecute(Void result) {
            Log.i("", "onPostExecute");
            progDailog.dismiss();
        }

        

        protected void onProgressUpdate(Void... values) {
            Log.i("", "onProgressUpdate");
        }


    }
}