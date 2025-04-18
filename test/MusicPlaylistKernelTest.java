
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class MusicPlaylistKernelTest {

    // addSong tests

    @Test
    public void addSong_single() {
        MusicPlaylist p = new MusicPlaylistOnSequence();
        p.addSong("A");
        assertEquals(1, p.length());
        assertEquals("A", p.getCurrentSong());
    }

    @Test
    public void addSong_multiple() {
        MusicPlaylist p = new MusicPlaylistOnSequence();
        p.addSong("A");
        p.addSong("B");
        p.addSong("C");
        assertEquals(3, p.length());
        assertEquals("A", p.getCurrentSong());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addSong_null() {
        MusicPlaylist p = new MusicPlaylistOnSequence();
        p.addSong(null);
    }

    // removeSong tests

    @Test
    public void removeSong_existing() {
        MusicPlaylist p = new MusicPlaylistOnSequence();
        p.addSong("A");
        p.addSong("B");
        String removed = p.removeSong("A");
        assertEquals("A", removed);
        assertEquals(1, p.length());
        assertEquals("B", p.getCurrentSong());
    }

    @Test
    public void removeSong_notPresent() {
        MusicPlaylist p = new MusicPlaylistOnSequence();
        p.addSong("A");
        assertNull(p.removeSong("X"));
        assertEquals(1, p.length());
    }

    @Test
    public void removeSong_null() {
        MusicPlaylist p = new MusicPlaylistOnSequence();
        p.addSong("A");
        assertNull(p.removeSong(null));
        assertEquals(1, p.length());
    }

    // getCurrentSong tests

    @Test
    public void getCurrentSong_empty() {
        MusicPlaylist p = new MusicPlaylistOnSequence();
        assertEquals("No songs in playlist", p.getCurrentSong());
    }

    @Test
    public void getCurrentSong_afterAdd() {
        MusicPlaylist p = new MusicPlaylistOnSequence();
        p.addSong("A");
        assertEquals("A", p.getCurrentSong());
    }

    @Test
    public void getCurrentSong_afterAdvance() {
        MusicPlaylist p = new MusicPlaylistOnSequence();
        p.addSong("A");
        p.addSong("B");
        p.nextSong();
        assertEquals("B", p.getCurrentSong());
    }

    // nextSong tests

    @Test
    public void nextSong_empty() {
        MusicPlaylist p = new MusicPlaylistOnSequence();
        p.nextSong();
        assertEquals("No songs in playlist", p.getCurrentSong());
    }

    @Test
    public void nextSong_single() {
        MusicPlaylist p = new MusicPlaylistOnSequence();
        p.addSong("A");
        p.nextSong();
        assertEquals("A", p.getCurrentSong());
    }

    @Test
    public void nextSong_wraps() {
        MusicPlaylist p = new MusicPlaylistOnSequence();
        p.addSong("A");
        p.addSong("B");
        p.addSong("C");
        p.nextSong(); // B
        p.nextSong(); // C
        p.nextSong(); // back to A
        assertEquals("A", p.getCurrentSong());
    }

    // insertSongAt tests

    @Test
    public void insertSongAt_middle() {
        MusicPlaylist p = new MusicPlaylistOnSequence();
        p.addSong("A");
        p.addSong("C");
        p.insertSongAt("B", 1);
        assertEquals(3, p.length());
        p.nextSong();
        assertEquals("B", p.getCurrentSong());
    }

    @Test
    public void insertSongAt_end() {
        MusicPlaylist p = new MusicPlaylistOnSequence();
        p.addSong("A");
        p.insertSongAt("B", 1);
        assertEquals(2, p.length());
        p.nextSong();
        assertEquals("B", p.getCurrentSong());
    }

    @Test(expected = IllegalArgumentException.class)
    public void insertSongAt_invalidIndex() {
        MusicPlaylist p = new MusicPlaylistOnSequence();
        p.insertSongAt("A", 2);
    }

    // removeSongAt tests

    @Test
    public void removeSongAt_start() {
        MusicPlaylist p = new MusicPlaylistOnSequence();
        p.addSong("A");
        p.addSong("B");
        String r = p.removeSongAt(0);
        assertEquals("A", r);
        assertEquals(1, p.length());
        assertEquals("B", p.getCurrentSong());
    }

    @Test
    public void removeSongAt_end() {
        MusicPlaylist p = new MusicPlaylistOnSequence();
        p.addSong("A");
        p.addSong("B");
        String r = p.removeSongAt(1);
        assertEquals("B", r);
        assertEquals(1, p.length());
        assertEquals("A", p.getCurrentSong());
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeSongAt_invalidIndex() {
        MusicPlaylist p = new MusicPlaylistOnSequence();
        p.addSong("A");
        p.removeSongAt(5);
    }

    // removeCurrentSong tests

    @Test
    public void removeCurrentSong_empty() {
        MusicPlaylist p = new MusicPlaylistOnSequence();
        assertNull(p.removeCurrentSong());
    }

    @Test
    public void removeCurrentSong_single() {
        MusicPlaylist p = new MusicPlaylistOnSequence();
        p.addSong("A");
        String r = p.removeCurrentSong();
        assertEquals("A", r);
        assertEquals(0, p.length());
        assertEquals("No songs in playlist", p.getCurrentSong());
    }

    @Test
    public void removeCurrentSong_multiple() {
        MusicPlaylist p = new MusicPlaylistOnSequence();
        p.addSong("A");
        p.addSong("B");
        p.nextSong(); // now on B
        String r = p.removeCurrentSong();
        assertEquals("B", r);
        assertEquals(1, p.length());
        assertEquals("A", p.getCurrentSong());
    }

    // goToSong tests

    @Test
    public void goToSong_valid() {
        MusicPlaylist p = new MusicPlaylistOnSequence();
        p.addSong("A");
        p.addSong("B");
        p.goToSong(1);
        assertEquals("B", p.getCurrentSong());
    }

    @Test(expected = IllegalArgumentException.class)
    public void goToSong_negative() {
        MusicPlaylist p = new MusicPlaylistOnSequence();
        p.addSong("A");
        p.goToSong(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void goToSong_tooLarge() {
        MusicPlaylist p = new MusicPlaylistOnSequence();
        p.addSong("A");
        p.goToSong(2);
    }
}
