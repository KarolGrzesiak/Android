package com.example.android.miwok;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT;
import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK;



public class ColorsFragment extends Fragment{
    private MediaPlayer mediaPlayer;
    private MediaPlayer.OnCompletionListener completionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };
    private AudioManager audioManager;
    private AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener= new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange==AudioManager.AUDIOFOCUS_LOSS){
                mediaPlayer.stop();
                releaseMediaPlayer();
            }
            else if (focusChange == AUDIOFOCUS_LOSS_TRANSIENT) {
               releaseMediaPlayer();
            } else if (focusChange == AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                releaseMediaPlayer();
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mediaPlayer.start();
            }

        }
    };
    public ColorsFragment(){}
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list,container,false);
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("wetetti","red",R.drawable.color_red,R.raw.color_red));
        words.add(new Word("chokokki","green",R.drawable.color_green,R.raw.color_green));
        words.add(new Word("takaakki","brown",R.drawable.color_brown,R.raw.color_brown));
        words.add(new Word("topoppi","gray",R.drawable.color_gray,R.raw.color_gray));
        words.add(new Word("kululli","black",R.drawable.color_black,R.raw.color_black));
        words.add(new Word("kelelli","white",R.drawable.color_white,R.raw.color_white));
        words.add(new Word("topiisa","dusty yellow",R.drawable.color_dusty_yellow,R.raw.color_dusty_yellow));
        words.add(new Word("chiwiita","mustard yellow",R.drawable.color_mustard_yellow,R.raw.color_mustard_yellow));



        WordAdapter adapter = new WordAdapter(getActivity(),words,R.color.category_colors);
        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                releaseMediaPlayer();
                int result = audioManager.requestAudioFocus(onAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    Word word = words.get(i);
                    mediaPlayer = MediaPlayer.create(getActivity(), word.getMusicResourceId());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(completionListener);
                }
            }
        });
        return rootView;
    }
    private void releaseMediaPlayer(){
        if (mediaPlayer!=null){
            mediaPlayer.release();
            mediaPlayer=null;
            audioManager.abandonAudioFocus(onAudioFocusChangeListener);
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}