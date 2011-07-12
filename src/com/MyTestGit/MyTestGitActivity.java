package com.MyTestGit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.DefaultHttpClientConnection;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Path.Direction;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MyTestGitActivity extends Activity {
    /** Called when the activity is first created. */
	private boolean isAuthenticated;
	private ArrayList<BasicNameValuePair> pairs;
	private DefaultHttpClient httpclient;
	private HttpPost httppost;
	private InputStream content;
	private String returnConnection;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        TextView tv=new TextView(this); 
        Map vars=new HashMap();  
        vars.put("mydata", "Hello,Android"); 
        ParameterHttp("http://5billion.com.cn/post.php",vars); 
        doPost(); 
        tv.setText(this.returnConnection);  
        this.setContentView(tv);
      
    }
    private void ParameterHttp(String url, Map vars) {
    	 this.httpclient=new DefaultHttpClient();  
    	 this.httppost=new HttpPost(url);  
    	 this.pairs=new ArrayList();
    	  if(vars!=null){  
    		  Set keys=vars.keySet();  
    		  for(Iterator i=keys.iterator();i.hasNext();){   
    			  String key=(String)i.next();    
    			  pairs.add(new BasicNameValuePair(key,(String) vars.get(key)));    
    			  }  
    		  }
    	  }
    private String doPost() {  
    	try{  
    	UrlEncodedFormEntity p_entity=new UrlEncodedFormEntity(pairs,"ISO-8859-1");    	httppost.setEntity(p_entity);
    	HttpResponse response=httpclient.execute(httppost);    
    	HttpEntity entity=response.getEntity();    
    	content=entity.getContent();    
    	this.returnConnection=convertStreamToString(content);    
    	Log.d("HttpPostConnection",">>>>>>>>>"+returnConnection);    
    	int status_code=response.getStatusLine().getStatusCode();    
    	if(status_code>=300){     
    	this.isAuthenticated=false;    
    	}else{     
    	this.isAuthenticated=true;    
    	}      
    }catch (Exception e) {   
     	// TODO: handle exception  
     }   
    	return returnConnection;
    }
    private static String convertStreamToString(InputStream is) {        
    	/*         
    	* To convert the InputStream to String we use the BufferedReader.readLine()        
    	* method. We iterate until the BufferedReader return null which means       
    	* there's no more data to read. Each line will appended to a StringBuilder     
    	* and returned as String.     
    	*/     
    		BufferedReader reader = new BufferedReader(new InputStreamReader(is));        
    		StringBuilder sb = new StringBuilder();
    	        String line = null;        
    		try {            
    		while ((line = reader.readLine()) != null) {                
    		sb.append(line + "\n");            
    		}        
    	} catch (IOException e) {            
    		e.printStackTrace();        
    		} finally {            
    		try {                
    		is.close();            
    		} catch (IOException e) {                
    		e.printStackTrace();            
    		}        
    	}
    	        return sb.toString();    
    	}
}



