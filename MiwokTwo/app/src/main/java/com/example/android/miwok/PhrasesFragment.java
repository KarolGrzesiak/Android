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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NumbersFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NumbersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhrasesFragment extends Fragment{
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
                releaseMediaPlayer();
            }

        }
    };
    public PhrasesFragment(){}
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list,container,false);
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("minto wuksus.", "Where are you going?", R.raw.phrase_where_are_you_going));
        words.add(new Word("tinne oyaase'ne?", "What is your name?", R.raw.phrase_what_is_your_name));
        words.add(new Word("oyaaset...", "My name is...", R.raw.phrase_my_name_is));
        words.add(new Word("michiekses?", "How are you feeling?", R.raw.phrase_how_are_you_feeling));
        words.add(new Word("kuchi achit.", "I'm feeling good.", R.raw.phrase_im_feeling_good));
        words.add(new Word("eenes'aa?", "Are you coming?", R.raw.phrase_are_you_coming));
        words.add(new Word("hee'eenem.", "Yes, I'm coming.", R.raw.phrase_yes_im_coming));
        words.add(new Word("eeneem.", "I'm coming.", R.raw.phrase_im_coming));
        words.add(new Word("yoowutis.", "Let's go.", R.raw.phrase_lets_go));
        words.add(new Word("enni'nem.", "Come here.", R.raw.phrase_come_here));


        WordAdapter adapter = new WordAdapter(getActivity(),words,R.color.category_phrases);
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