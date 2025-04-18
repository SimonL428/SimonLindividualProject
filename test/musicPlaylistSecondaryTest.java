import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class musicPlaylistSecondaryTest {

    private MusicPlaylistOnSequence p1, p2;

    @Before
    public void setUp() {
        this.p1 = new MusicPlaylistOnSequence();
        this.p2 = new MusicPlaylistOnSequence();
        // seed both with the same three songs
        for (String s : new String[] { "A", "B", "C" }) {
            this.p1.addSong(s);
            this.p2.addSong(s);
        }
    }

    @Test
    public void testLengthAndClear() {
        assertEquals(3, this.p1.length());
        this.p1.clear();
        assertEquals(0, this.p1.length());
    }

    @Test
    public void testPreviousSong() {
        // at start, current = "A"
        assertEquals("A", this.p1.getCurrentSong());
        this.p1.previousSong(); // with only three songs, rotating twice wraps backward by one
        assertEquals("C", this.p1.getCurrentSong());
        // advance to "C" explicitly
        this.p1.nextSong();
        this.p1.nextSong();
        assertEquals("C", this.p1.getCurrentSong());
        this.p1.previousSong();
        assertEquals("B", this.p1.getCurrentSong());
    }

    @Test
    public void testContains() {
        assertTrue(this.p1.contains("B"));
        assertFalse(this.p1.contains("X"));
    }

    @Test
    public void testToStringEqualsHashCode() {
        // initial ordering is ["A","B","C"]
        assertEquals("[A, B, C]", this.p1.toString());
        assertTrue(this.p1.equals(this.p2));
        assertEquals(this.p1.hashCode(), this.p2.hashCode());
        // mutate p2
        this.p2.removeSong("B");
        assertFalse(this.p1.equals(this.p2));
    }

    @Test
    public void testShuffleKeepsAllSongs() {
        this.p1.shuffle();
        assertEquals(3, this.p1.length());
        for (String s : new String[] { "A", "B", "C" }) {
            assertTrue(this.p1.contains(s));
        }
    }
}
