package com.example.drawer;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.database.DataBaseManager;

public class PlaceholderFragment extends Fragment {
	
//	BluetoothConnection blueConn = new BluetoothConnection();
	
	/* BLUETOOTH CONNECTION */

	// Intent request codes
    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
	private static final int REQUEST_ENABLE_BT = 3;
	
	private BluetoothAdapter mBluetoothAdapter = null;

	private static final String ARG_SECTION_NUMBER = "section_number";

    private String title = "nada", album = "nada", artist = "nada";

    public String all = "nada";

    /**
     * Name of the connected device
     */
    private String mConnectedDeviceName = null;

    /**
     * Member object for the chat services
     */
    private BluetoothConnectionService mChatService = null;
    int mCurCheckPosition;

    public static PlaceholderFragment newInstance(int sectionNumber) {
		PlaceholderFragment fragment = new PlaceholderFragment();

		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public PlaceholderFragment() {
	}

	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
                // Restore last state for checked position.
                mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
            }
    }
	
	@Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", mCurCheckPosition);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewReturn = null;
        int position = getArguments().getInt(ARG_SECTION_NUMBER);
        switch (position) {
            case 1:
                viewReturn = inflater.inflate(R.layout.fragment_bluetooth_chat, container, false);
                tabModo(viewReturn);
                return viewReturn;
            case 2:
                viewReturn = inflater.inflate(R.layout.history_list, container, false);
                tabHistorial(viewReturn);
                return viewReturn;
            case 3:
                viewReturn = inflater.inflate(R.layout.fav_list, container, false);
                tabFav(viewReturn);
                return viewReturn;
        }
        return viewReturn;

    }
	
	// Codigo Java de la tab #1 Modo
	public void tabModo(View viewReturn) {
		
		Button mButton1 = (Button) viewReturn.findViewById(R.id.buttonActBluetooth);
	    mButton1.setOnClickListener(new OnClickListener() {
	        @Override
	        public void onClick(View v) {

	    		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

	    		if (!mBluetoothAdapter.isEnabled()) {
	    			Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
	    			startActivityForResult(enableIntent, REQUEST_ENABLE_BT);

	    		}
            }
	    });
	    
	    Button mButton2 = (Button) viewReturn.findViewById(R.id.buttonDeactBluetooth);
	    mButton2.setOnClickListener(new OnClickListener() {
	        @Override
	        public void onClick(View v) {
	    		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	    		if (mBluetoothAdapter.isEnabled()) {
	    			mBluetoothAdapter.disable();
		        	Toast.makeText(getActivity(), "Bluetooth desactivado", Toast.LENGTH_SHORT).show();
	    		}
	    		
	        }
	    });

        Button mSecConn = (Button) viewReturn.findViewById(R.id.secure_connect_scan);
        mSecConn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Send a message using content of the edit text widget
                Intent serverIntent = new Intent(getActivity(), DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);
            }
        });

        Button mInsecConn = (Button) viewReturn.findViewById(R.id.insecure_connect_scan);
        mInsecConn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Send a message using content of the edit text widget
                Intent serverIntent = new Intent(getActivity(), DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_INSECURE);
            }
        });

        Button mDisc = (Button) viewReturn.findViewById(R.id.discoverable);
        mDisc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Send a message using content of the edit text widget
                ensureDiscoverable();
            }
        });
	}

    //Variables fragment BD
    private DataBaseManager manager;
    private Cursor cursor;
    private ListView lista, lista2;
    private SimpleCursorAdapter adapter;
    
	// Codigo Java de la tab #2 Historial
	public void tabHistorial(View viewReturn) {
        manager = new DataBaseManager(getActivity());
        cursor = manager.cargarCursorCanciones();
        lista = (ListView) viewReturn.findViewById(R.id.listViewHistory);

        String[] from = new String[]{DataBaseManager.CN_TITLE, DataBaseManager.CN_ALBUM, DataBaseManager.CN_ARTIST, DataBaseManager.CN_FAV};
        int [] to = new int[]{R.id.lblTitulo,R.id.lblAlbum,R.id.lblArtist, R.id.lblFav};

        adapter = new SimpleCursorAdapter(getActivity(), R.layout.item_list,cursor,from,to,0);
        lista.setAdapter(adapter);

		lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3) {

				Object o = lista.getItemAtPosition(position);

				TextView album = (TextView) view.findViewById(R.id.lblAlbum);
				TextView title = (TextView) view.findViewById(R.id.lblTitulo);
				TextView artist = (TextView) view.findViewById(R.id.lblArtist);
				TextView fav = (TextView) view.findViewById(R.id.lblFav);
				ImageView heart = (ImageView) view.findViewById(R.id.likeheart);
				String favresul = fav.getText().toString();

				if (favresul.equals("1")) {
					manager.modificarTitulo(title.getText().toString(), album.getText().toString(), artist.getText().toString(), false);
					heart.setImageDrawable(getResources().getDrawable(R.drawable.heart_white));
					fav.setText("0");
				} else {
					manager.modificarTitulo(title.getText().toString(), album.getText().toString(), artist.getText().toString(), true);
					heart.setImageDrawable(getResources().getDrawable(R.drawable.heart_red));
					fav.setText("1");
				}


			}
		});
	}

	// Codigo Java de la tab #3 Favoritos
    public void tabFav(View viewReturnF) {
        manager = new DataBaseManager(getActivity());
		cursor = manager.cargarFavoritos();
		lista2 = (ListView) viewReturnF.findViewById(R.id.listViewFav);

        String[] from = new String[]{DataBaseManager.CN_TITLE, DataBaseManager.CN_ALBUM, DataBaseManager.CN_ARTIST, DataBaseManager.CN_FAV};
        int [] to = new int[]{R.id.lblTitulo,R.id.lblAlbum,R.id.lblArtist, R.id.lblFav};
        
        adapter = new SimpleCursorAdapter(getActivity(), R.layout.item_list,cursor,from,to,0);
        lista2.setAdapter(adapter);

		lista2.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3) {

				Object o = lista2.getItemAtPosition(position);

				TextView album = (TextView) view.findViewById(R.id.lblAlbum);
				TextView title = (TextView) view.findViewById(R.id.lblTitulo);
				TextView artist = (TextView) view.findViewById(R.id.lblArtist);
				TextView fav = (TextView) view.findViewById(R.id.lblFav);
				ImageView heart = (ImageView) view.findViewById(R.id.likeheart);

				String favresul = fav.getText().toString();

				if (favresul.equals("1")) {
					manager.modificarTitulo(title.getText().toString(), album.getText().toString(), artist.getText().toString(), false);
					heart.setImageDrawable(getResources().getDrawable(R.drawable.heart_white));
					fav.setText("0");
				} else {
					manager.modificarTitulo(title.getText().toString(), album.getText().toString(), artist.getText().toString(), true);
					heart.setImageDrawable(getResources().getDrawable(R.drawable.heart_red));
					fav.setText("1");
				}


			}
		});
    }
    
//	@Override
//	public void onViewCreated(View view, Bundle savedInstanceState) {
////		TextView textView1 = (TextView) view.findViewById(R.id.textView1);
////		int position = getArguments().getInt(ARG_SECTION_NUMBER);
////		textView1.setText("Posicion Swipe: "+position);
//	}

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        mConversationView = (ListView) view.findViewById(R.id.in);
//        mOutEditText = (EditText) view.findViewById(R.id.edit_text_out);
    }
	
    /* METODO ANTIGUO PARA QUE NO PETE AL VOLVER A LA TAB #1 */
//	@Override
//	public void onDestroyView() {
//		super.onDestroyView();
//		killFragment();
//	}
//	
//	@Override
//	public void onDetach() {
//		// TODO Auto-generated method stub
//		super.onDetach();
//		killFragment();
//	}
//
    /*
	private void killFragment() {
		Fragment f = (Fragment) getFragmentManager().findFragmentById(R.id.fragment1);
		if (f != null) {
//			FragmentManager fM = getFragmentManager();
			getFragmentManager().beginTransaction().remove(f).commit();
//			getFragmentManager().beginTransaction().remove(f).commit();
		}
	}
*/

//	@Override
//		public void onPause() {
//			// TODO Auto-generated method stub
//			super.onPause();
////			Toast.makeText(getActivity(), "VAMOS MAL", Toast.LENGTH_SHORT).show();
//
//			Fragment f = (Fragment) getFragmentManager()
//					.findFragmentById(R.id.fragment1);
//			if (f != null) 
//				getFragmentManager().beginTransaction().remove(f).commit();
//		}

//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_bluetooth_chat, container, false);
//    }


//    private com.example.database.DataBaseManager manager;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        // Get local Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
        	Activity activity = getActivity();
            Toast.makeText(activity, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            activity.finish();
        }

        IntentFilter iF = new IntentFilter();
        iF.addAction("com.android.music.metachanged");
        iF.addAction("com.android.music.playstatechanged");
        iF.addAction("com.android.music.playbackcomplete");
        iF.addAction("com.android.music.queuechanged");
        iF.addAction("com.android.music.metachanged");
        iF.addAction("com.htc.music.metachanged");
        iF.addAction("fm.last.android.metachanged");
        iF.addAction("com.sec.android.app.music.metachanged");
        iF.addAction("com.nullsoft.winamp.metachanged");
        iF.addAction("com.amazon.mp3.metachanged");
        iF.addAction("com.miui.player.metachanged");
        iF.addAction("com.real.IMP.metachanged");
        iF.addAction("com.sonyericsson.music.metachanged");
        iF.addAction("com.rdio.android.metachanged");
        iF.addAction("com.samsung.sec.android.MusicPlayer.metachanged");
        iF.addAction("com.meizu.media.music");
        this.getActivity().registerReceiver(mReceiver, iF);
        
    }

    public BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle extra = intent.getExtras();
//            title = extra.getString("track");
//            album = extra.getString("album");
//            artist= extra.getString("artist");

            // Al parecer el broadcastReceiver se ejecuta 2 veces cuando cambias de cancion. Usamos un boolean para evitar enviar los datos 2 veces.
            try {
                title = extra.getString("track");
                album = extra.getString("album");
                artist= extra.getString("artist");
            } catch (Exception e) {
                title = ("Unknown Title");
                album = ("Unknown Album");
                artist = ("Unknown Artist");
            }
//                all = "Title:"+ title +"\n Album:" + album + "\n Artist:" + artist;
            all = title +":title:" + album + ":album:" + artist;
            if (title!=null && album!=null && artist!=null) {
                sendMessage(all);
            }
        }
    };

    private static void GeneraNotificacion(Context context, String message){
        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.blumudi_icon)
                .setContentTitle("Blumudi")
                .setContentIntent(intent)
                .setPriority(1)
                .setContentText(message)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(9999, mBuilder.build());
    }

    @Override
    public void onStart() {
        super.onStart();
        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        if (!mBluetoothAdapter.isEnabled()) {
            //Pide al usuario activar el Bluetooth, si no lo activa se cierra la app.
        	
/*        	// Quitado el Domingo 31-05-2015, si se quita ya no pide conectar el bluetooth cada vez que cambiamos de tab
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
*/
            // Otherwise, setup the chat session
        } else if (mChatService == null) {
            setupChat();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mChatService != null) {
            mChatService.stop();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        if (mChatService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mChatService.getState() == BluetoothConnectionService.STATE_NONE) {
                // Start the Bluetooth chat services
                mChatService.start();
            }
        }
    }

    /**
     * Set up the UI and background operations for chat.
     */
    private void setupChat() {

        
        MainActivity m1 = (MainActivity)getActivity();
        if(m1.check==false) {
	        	
	        // Initialize the array adapter for the conversation thread
	        //mConversationArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.message);
	        // Initialize the BluetoothChatService to perform bluetooth connections
	        mChatService = new BluetoothConnectionService(getActivity(), mHandler);
	
	        m1.check=true;
	    }
        // Initialize the buffer for outgoing messages
        //mOutStringBuffer = new StringBuffer("");
        
    }

    /**
     * Makes this device discoverable.
     */
    private void ensureDiscoverable() {
        if (mBluetoothAdapter.getScanMode() !=
                BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }
    /**
     * Sends a message.
     *
     * @param message A string of text to send.
     */
    private void sendMessage(String message) {
        // Check that we're actually connected before trying anything
        try {
            if (mChatService.getState() != BluetoothConnectionService.STATE_CONNECTED) {
                Toast.makeText(getActivity(), R.string.not_connected, Toast.LENGTH_SHORT).show();
                return;
            }

            // Check that there's actually something to send
            if (message.length() > 0) {
                // Get the message bytes and tell the BluetoothChatService to write
                byte[] send = message.getBytes();
                mChatService.write(send);

                // Reset out string buffer to zero and clear the edit text field
                //mOutStringBuffer.setLength(0);
//            mOutEditText.setText(mOutStringBuffer);
            }
        }catch (Exception e){
            Log.e("OnSendMessage",e.toString());
        }
    }

    /**
     * Updates the status on the action bar.
     *
     * @param resId a string resource ID
     */
    private void setStatus(int resId) {
    	Activity activity = getActivity();
        if (null == activity) {
            return;
        }
        final ActionBar actionBar = activity.getActionBar();
        if (null == actionBar) {
            return;
        }
        actionBar.setSubtitle(resId);
    }

    /**
     * Updates the status on the action bar.
     *
     * @param subTitle status
     */
    private void setStatus(CharSequence subTitle) {
    	Activity activity = getActivity();
        if (null == activity) {
            return;
        }
        final ActionBar actionBar = activity.getActionBar();
        if (null == actionBar) {
            return;
        }
        actionBar.setSubtitle(subTitle);
    }

    /**
     * The Handler that gets information back from the BluetoothChatService
     */
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
        	Activity activity = getActivity();
            switch (msg.what) {
                case Constants.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothConnectionService.STATE_CONNECTED:
                            setStatus(getString(R.string.title_connected_to, mConnectedDeviceName));
                            //mConversationArrayAdapter.clear();
                            break;
                        case BluetoothConnectionService.STATE_CONNECTING:
                            setStatus(R.string.title_connecting);
                            break;
                        case BluetoothConnectionService.STATE_LISTEN:
                        case BluetoothConnectionService.STATE_NONE:
                            setStatus(R.string.title_not_connected);
                            break;
                    }
                    break;
                case Constants.MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                    //mConversationArrayAdapter.add(writeMessage);
                    break;
                case Constants.MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    String[] title = readMessage.split(":title:");
                    String[] album = title[1].split(":album:");
                    manager = new DataBaseManager(getActivity());
                    if (manager.find(title[0], album[0], album[1]) == null) {
                        manager.insertar(title[0], album[0], album[1]);
                    }
                    //manager.insertar(title[0], album[0], album[1]);
                    GeneraNotificacion(getActivity(), title[0] + " recibida");
                    
                    //all = title +":title:" + album + ":album:" + artist;

                        // Mensaje con el nombre del dispositivo incluido
                        //mConversationArrayAdapter.add(mConnectedDeviceName + ":  " + readMessage);
//                        mConversationArrayAdapter.add(readMessage);
                    break;
                case Constants.MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(Constants.DEVICE_NAME);
                    if (null != activity) {
                        Toast.makeText(activity, "Connected to "
                                + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case Constants.MESSAGE_TOAST:
                    if (null != activity) {
                        Toast.makeText(activity, msg.getData().getString(Constants.TOAST),
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    	   super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE_SECURE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, true);
                }
                break;
            case REQUEST_CONNECT_DEVICE_INSECURE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, false);
                }
                break;
                
                // Pide al usuario activar el Bluetooth, si no lo activa se cierra la app.
//            case REQUEST_ENABLE_BT:
//                // When the request to enable Bluetooth returns
//                if (resultCode == Activity.RESULT_OK) {
//                    // Bluetooth is now enabled, so set up a chat session
//                    setupChat();
//                } else {
//                    // User did not enable Bluetooth or an error occurred
//
//                    Toast.makeText(getActivity(), R.string.bt_not_enabled_leaving,
//                            Toast.LENGTH_SHORT).show();
//                    getActivity().finish();
//                }
        }
    }
    
    /**
     * Establish connection with other device
     *
     * @param data   An {@link Intent} with {@link DeviceListActivity#EXTRA_DEVICE_ADDRESS} extra.
     * @param secure Socket Security type - Secure (true) , Insecure (false)
     */
    private void connectDevice(Intent data, boolean secure) {
        // Get the device MAC address
        String address = data.getExtras()
                .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        // Get the BluetoothDevice object
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        // Attempt to connect to the device
        mChatService.connect(device, secure);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.secure_connect_scan: {
                // Launch the DeviceListActivity to see devices and do scan
                Intent serverIntent = new Intent(getActivity(), DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);
                return true;
            }
            case R.id.insecure_connect_scan: {
                // Launch the DeviceListActivity to see devices and do scan
                Intent serverIntent = new Intent(getActivity(), DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_INSECURE);
                return true;
            }
            case R.id.discoverable: {
                // Ensure this device is discoverable by others
                ensureDiscoverable();
                return true;
            }
        }
        return false;
    }
	
	
	
}