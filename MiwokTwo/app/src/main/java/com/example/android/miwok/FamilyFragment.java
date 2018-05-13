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
public class FamilyFragment extends Fragment{
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
                releaseMediaPlayer();;
            } else if (focusChange == AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                releaseMediaPlayer();
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mediaPlayer.start();
            }

        }
    };
    public FamilyFragment(){}
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list,container,false);
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("epe","father",R.drawable.family_father,R.raw.family_father));
        words.add(new Word("eta","mother",R.drawable.family_mother,R.raw.family_mother));
        words.add(new Word("angsi","son",R.drawable.family_son,R.raw.family_son));
        words.add(new Word("tune","daughter",R.drawable.family_daughter,R.raw.family_daughter));
        words.add(new Word("taachi","older brother",R.drawable.family_older_brother,R.raw.family_older_brother));
        words.add(new Word("chalitti","younger brother",R.drawable.family_younger_brother,R.raw.family_younger_brother));
        words.add(new Word("tete","older sister",R.drawable.family_older_sister,R.raw.family_older_sister));
        words.add(new Word("kolliti","younger sister",R.drawable.family_younger_sister,R.raw.family_younger_sister));
        words.add(new Word("ama","grandmother",R.drawable.family_grandmother,R.raw.family_grandmother));
        words.add(new Word("paapa","grandfather",R.drawable.family_grandfather,R.raw.family_grandfather));


        WordAdapter adapter = new WordAdapter(getActivity(),words,R.color.category_family);
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