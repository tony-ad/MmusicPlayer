package com.adui.musicplayer.model;

import java.util.ArrayList;
import java.util.List;

import com.adui.musicplayer.layout.HScrollView.musicViewPager.frag_1_ListView.ListView_allMusic;
import com.adui.musicplayer.layout.HScrollView.musicViewPager.frag_1_ListView.ListView_ilikemusic;

public class Gongju {
	public static List<Music> whatModelForList(int model) {
		List<Music> musics = new ArrayList<Music>();
		switch (model) {
		case 0:
			musics = ListView_allMusic.giveList0();
			break;
		case 1:
			musics = ListView_ilikemusic.giveList1();
			break;
		case 2:

			break;
		case 3:

			break;

		default:
			break;
		}
		return musics;
	}
}
