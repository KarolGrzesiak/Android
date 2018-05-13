package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT;
import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK;


public class NumbersFragment extends Fragment{
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
    public NumbersFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list,container,false);
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("lutti","one",R.drawable.number_one,R.raw.number_one));
        words.add(new Word("otiiko","two",R.drawable.number_two,R.raw.number_two));
        words.add(new Word("tolookosu","three",R.drawable.number_three,R.raw.number_three));
        words.add(new Word("oyyisa","four",R.drawable.number_four,R.raw.number_four));
        words.add(new Word("massokka","five",R.drawable.number_five,R.raw.number_five));
        words.add(new Word("temmokka","six",R.drawable.number_six,R.raw.number_six));
        words.add(new Word("kenekaku","seven",R.drawable.number_seven,R.raw.number_seven));
        words.add(new Word("kawinta","eight",R.drawable.number_eight,R.raw.number_eight));
        words.add(new Word("wo'e","nine",R.drawable.number_nine,R.raw.number_nine));
        words.add(new Word("na'aacha","ten",R.drawable.number_ten,R.raw.number_ten));


        WordAdapter adapter = new WordAdapter(getActivity(),words,R.color.category_numbers);
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