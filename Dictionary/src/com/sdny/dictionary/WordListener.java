package com.sdny.dictionary;

import android.app.Service;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.ClipboardManager.OnPrimaryClipChangedListener;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class WordListener extends Service {
	private OnPrimaryClipChangedListener listener = new OnPrimaryClipChangedListener() {
		public void onPrimaryClipChanged() {
			String word = getClipBoardData();
			if(word != null)
				Toast.makeText(getApplicationContext(), "We received : " + word, Toast.LENGTH_LONG).show();
			else
				Toast.makeText(getApplicationContext(), "We received : null", Toast.LENGTH_LONG).show();
		}
	};

	@Override
	public void onCreate() {
		Toast.makeText(getApplicationContext(), "Starting the service.", Toast.LENGTH_LONG).show();
		((ClipboardManager) getSystemService(CLIPBOARD_SERVICE))
				.addPrimaryClipChangedListener(listener);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	private String getClipBoardData() {
		String word = null;
		ClipboardManager cb = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
		if (cb.hasPrimaryClip()) {
			ClipData cd = cb.getPrimaryClip();
			if (cd.getDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
				word = (String) cb.getPrimaryClip().toString();
			}
		}
		return word;
	}
}