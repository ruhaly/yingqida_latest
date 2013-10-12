package com.yingqida.richplay.baseapi.file;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.ToneGenerator;

import com.yingqida.richplay.R;
import com.yingqida.richplay.baseapi.AppLog;
import com.yingqida.richplay.baseapi.common.RichResource;
import com.yingqida.richplay.baseapi.common.RichResource.ShareKey;

public class SoundUtil {

	public static SoundUtil ins = new SoundUtil();

	private static Map<Integer, Integer> soundSrc;

	private static SoundPool spool;

	private static ToneGenerator tonePlayer = new ToneGenerator(
			AudioManager.STREAM_MUSIC, 100);

	private SoundUtil() {
	}

	public synchronized void initSoundResource(Context context) {
		AppLog.out("wjd", "initSoundResource()", AppLog.LEVEL_INFO);
		int[] numId = { R.raw.oggle };
		int[] soundId = { R.raw.oggle };
		spool = new SoundPool(numId.length, AudioManager.STREAM_NOTIFICATION, 0);
		soundSrc = new HashMap<Integer, Integer>(numId.length);
		for (int i = 0; i < soundId.length; i++)
			soundSrc.put(numId[i], spool.load(context, soundId[i], 1));
	}

	public synchronized void play(int id, Context context) {
		if (null == spool)
			initSoundResource(context);
		int sid = soundSrc.get(id);
		if (-1 != sid) {
			float volumn = context.getSharedPreferences(
					RichResource.CONFIG_NAME, 0).getFloat(
					ShareKey.SETTING_VOLUMN_VALUE, 1);
			spool.play(sid, volumn, volumn, 1, 0, 1);
		}
	}

	public synchronized void play(int id, Context context, float volumn) {
		if (null == spool)
			initSoundResource(context);
		Integer sid = soundSrc.get(id);
		if (-1 != sid) {
			spool.play(sid, volumn, volumn, 1, 0, 1);
		}
	}

	public synchronized void releaseSoundPool() {
		if (null != spool)
			spool.release();
		spool = null;
	}

	public void playTone() {
		tonePlayer.startTone(ToneGenerator.TONE_DTMF_0, 100);
	}
}
