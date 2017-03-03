package tokenhacks.tokenmusic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;

public class MainActivity extends FragmentActivity  {
    private FragmentTabHost m_tabhost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_tabhost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        m_tabhost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        m_tabhost.addTab(m_tabhost.newTabSpec("Queue").setIndicator("First2").setContent(new Intent(this, FragmentTabQueue.class)));
  //      m_tabhost.addTab(m_tabhost.newTabSpec("Genres").setIndicator("First2").setContent(new Intent(this, FragmentTabGenres.class)));
  //      m_tabhost.addTab(m_tabhost.newTabSpec("Artists").setIndicator("Second2").setContent(new Intent(this, FragmentTabArtists.class)));
  //      m_tabhost.addTab(m_tabhost.newTabSpec("Songs").setIndicator("First2").setContent(new Intent(this, FragmentTabSongs.class)));
  //      m_tabhost.addTab(m_tabhost.newTabSpec("Albums").setIndicator("First2").setContent(new Intent(this, FragmentTabAlbums.class)));

        m_tabhost.setCurrentTab(0);
    }
}



