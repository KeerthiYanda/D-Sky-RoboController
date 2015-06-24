package com.example.socketclient;


import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

 TextView textResponse;
 EditText editTextAddress, editTextPort, editTextCommand;
 Button buttonConnect, buttonClear,buttonClearText, buttonGo;
		 //buttonBackward,buttonForward,buttonLeft,buttonRight,buttonStop,buttonSmile;
 protected static final int RESULT_SPEECH = 1;
 ImageButton btnSpeak;
 String command;
 Boolean checkupdate=false;
 @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_main);

  editTextAddress = (EditText)findViewById(R.id.address);
  editTextPort = (EditText)findViewById(R.id.port);
  buttonConnect = (Button)findViewById(R.id.connect);
  buttonClear = (Button)findViewById(R.id.clear);
  textResponse = (TextView)findViewById(R.id.response);
  btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);

	 btnSpeak.setOnClickListener(new View.OnClickListener() {

		 @Override
		 public void onClick(View v) {
			 Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
			 intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
			 try {
				 startActivityForResult(intent, RESULT_SPEECH);
				 //txtText.setText("");
			 } catch (ActivityNotFoundException a) {
				 Toast t = Toast.makeText(getApplicationContext(),
						 "Opps! Your device doesn't support Speech to Text",
						 Toast.LENGTH_SHORT);
				 t.show();
			 }
		 }
	 });



  buttonClearText = (Button)findViewById(R.id.clearText);
  buttonGo=(Button) findViewById(R.id.go);
  editTextCommand = (EditText)findViewById(R.id.inputTextCommand);

  buttonGo.setOnClickListener(new OnClickListener(){
	  @Override
		public void onClick(View arg0) {
		 // TODO Auto-generated method stub
		 command=editTextCommand.getText().toString();
		 checkupdate=true;
		}
  });

  buttonConnect.setOnClickListener(buttonConnectOnClickListener);

  buttonClear.setOnClickListener(new OnClickListener(){
	   @Override
	   public void onClick(View v) {
	   textResponse.setText("");
   }});

  buttonClearText.setOnClickListener(new OnClickListener(){
		@Override
		public void onClick(View v) {
		editTextCommand.setText("");
    }});

 }

/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
*/

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
			case RESULT_SPEECH: {
				if (resultCode == RESULT_OK && null != data) {

					ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
					checkupdate=true;
					command = text.get(0);
				}
				break;
			}

		}
	}

	OnClickListener buttonConnectOnClickListener =
   new OnClickListener(){
    @Override
    public void onClick(View arg0) {
     MyClientTask myClientTask = new MyClientTask(
       editTextAddress.getText().toString(),
       Integer.parseInt(editTextPort.getText().toString()));
     myClientTask.execute();
    }};

 public class MyClientTask extends AsyncTask<Void, Void, Void> {

  String dstAddress;
  int dstPort;
  String response = "";

  MyClientTask(String addr, int port){
   dstAddress = addr;
   dstPort = port;
  }
  @Override
	protected Void doInBackground(Void... arg0) {

		OutputStream outputStream;
		Socket socket = null;

		try {
			socket = new Socket(dstAddress, dstPort);
			Log.d("MyClient Task", "Destination Address : " + dstAddress);
			Log.d("MyClient Task", "Destination Port : " + dstPort + "");
			outputStream = socket.getOutputStream();
			PrintStream printStream = new PrintStream(outputStream);
            response = "Connected. Have fun!!!";
			while (true) {
				if(checkupdate)
				{
					Log.d("Command", command);
					Log.d("checkUpdate", checkupdate.toString());
					printStream.print(command);
					printStream.flush();
					Log.d("Socekt connection", socket.isClosed()+"");
					checkupdate=false;
				}
			}

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response = "UnknownHostException: " + e.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response = "IOException: " + e.toString();
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}


  @Override
  protected void onPostExecute(Void result) {
   textResponse.setText(response);
   super.onPostExecute(result);
  }

 }

}
