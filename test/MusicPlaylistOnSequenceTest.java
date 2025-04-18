import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

public class MusicPlaylistOnSequenceTest {
    private MusicPlaylistOnSequence playlist;

    @Before
    public void setUp() {
        this.playlist = new MusicPlaylistKernelImpl();
    }

    @Test
    public void testAddAndGetCurrent() {
        assertEquals("No songs in playlist", this.playlist.getCurrentSong());
        this.playlist.addSong("A");
        assertEquals("A", this.playlist.getCurrentSong());
        this.playlist.addSong("B");
        assertEquals("A", this.playlist.getCurrentSong());
    }

    @Test
    public void testNextSongWraps() {
        this.playlist.addSong("A");
        this.playlist.addSong("B");
        assertEquals("A", this.playlist.getCurrentSong());
        this.playlist.nextSong();
        assertEquals("B", this.playlist.getCurrentSong());
        this.playlist.nextSong();
        assertEquals("A", this.playlist.getCurrentSong());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGoToSongInvalidLow() {
        this.playlist.goToSong(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGoToSongInvalidHigh() {
        this.playlist.goToSong(0); // still empty, any index ≥0 is out of bounds
    }

    @Test
    public void testGoToSong() {
        this.playlist.addSong("A");
        this.playlist.addSong("B");
        this.playlist.goToSong(1);
        assertEquals("B", this.playlist.getCurrentSong());
    }

    @Test
    public void testRemoveSong() {
        this.playlist.addSong("A");
        this.playlist.addSong("B");
        this.playlist.addSong("C");
        assertEquals("B", this.playlist.removeSong("B"));
        assertFalse(this.playlist.contains("B"));
        assertNull(this.playlist.removeSong("X"));
    }

    @Test
    public void testRemoveCurrentSong() {
        this.playlist.addSong("A");
        this.playlist.addSong("B");
        assertEquals("A", this.playlist.removeCurrentSong());
        assertEquals("B", this.playlist.getCurrentSong());
        this.playlist.clear();
        assertNull(this.playlist.removeCurrentSong());
    }

    @Test
    public void testInsertSongAt() {
        this.playlist.insertSongAt("X", 0);
        assertEquals("X", this.playlist.getCurrentSong());
        this.playlist.addSong("Z");
        this.playlist.insertSongAt("Y", 1);
        this.playlist.goToSong(1);
        assertEquals("Y", this.playlist.getCurrentSong());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInsertSongAtInvalid() {
        this.playlist.insertSongAt("Y", 2); // only 0 or 1 valid
    }

    @Test
    public void testRemoveSongAt() {
        this.playlist.addSong("A");
        this.playlist.addSong("B");
        this.playlist.addSong("C");
        assertEquals("B", this.playlist.removeSongAt(1));
        assertEquals(2, this.playlist.length());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveSongAtInvalid() {
        this.playlist.removeSongAt(0); // empty
    }
}
