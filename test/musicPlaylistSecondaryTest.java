
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashSet;

import org.junit.Test;

// Simon Luo

public class MusicPlaylistSecondaryTest {

    // previousSong tests

    @Test
    public void previousSong_empty() {
        MusicPlaylist p = new MusicPlaylistOnSequence();
        p.previousSong();
        assertEquals("No songs in playlist", p.getCurrentSong());
    }

    @Test
    public void previousSong_single() {
        MusicPlaylist p = new MusicPlaylistOnSequence();
        p.addSong("A");
        p.previousSong();
        assertEquals("A", p.getCurrentSong());
    }

    @Test
    public void previousSong_multi() {
        MusicPlaylist p = new MusicPlaylistOnSequence();
        p.addSong("A");
        p.addSong("B");
        p.addSong("C");
        // current is A, previous should wrap to C
        p.previousSong();
        assertEquals("C", p.getCurrentSong());
    }

    // shuffle tests

    @Test
    public void shuffle_empty() {
        MusicPlaylist p = new MusicPlaylistOnSequence();
        p.shuffle();
        assertEquals(0, p.length());
    }

    @Test
    public void shuffle_single() {
        MusicPlaylist p = new MusicPlaylistOnSequence();
        p.addSong("A");
        p.shuffle();
        assertEquals(1, p.length());
        assertEquals("A", p.getCurrentSong());
    }

    @Test
    public void shuffle_multisong_preservesContents() {
        MusicPlaylist p = new MusicPlaylistOnSequence();
        p.addSong("A");
        p.addSong("B");
        p.addSong("C");
        int lenBefore = p.length();
        HashSet<String> before = new HashSet<>();
        before.add("A");
        before.add("B");
        before.add("C");

        p.shuffle();

        assertEquals(lenBefore, p.length());
        HashSet<String> after = new HashSet<>();
        for (int i = 0; i < p.length(); i++) {
            after.add(p.getCurrentSong());
            p.nextSong();
        }
        assertEquals(before, after);
    }

    // contains tests

    @Test
    public void contains_null() {
        MusicPlaylist p = new MusicPlaylistOnSequence();
        p.addSong("A");
        assertFalse(p.contains(null));
    }

    @Test
    public void contains_empty() {
        MusicPlaylist p = new MusicPlaylistOnSequence();
        assertFalse(p.contains("A"));
    }

    @Test
    public void contains_presentAndAbsent() {
        MusicPlaylist p = new MusicPlaylistOnSequence();
        p.addSong("X");
        p.addSong("Y");
        assertTrue(p.contains("Y"));
        assertFalse(p.contains("Z"));
    }

    // displayPlaylist tests

    @Test
    public void displayPlaylist_empty() {
        MusicPlaylist p = new MusicPlaylistOnSequence();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream orig = System.out;
        System.setOut(new PrintStream(out));
        p.displayPlaylist();
        System.setOut(orig);
        String s = out.toString().trim();
        assertEquals("[Playlist is empty]", s);
    }

    @Test
    public void displayPlaylist_twoSongs_currentFirst() {
        MusicPlaylist p = new MusicPlaylistOnSequence();
        p.addSong("A");
        p.addSong("B");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream orig = System.out;
        System.setOut(new PrintStream(out));
        p.displayPlaylist();
        System.setOut(orig);
        String printed = out.toString();
        assertTrue(printed.contains("- A <-- Current Song"));
        assertTrue(printed.contains("- B"));
    }

    @Test
    public void displayPlaylist_afterAdvance() {
        MusicPlaylist p = new MusicPlaylistOnSequence();
        p.addSong("A");
        p.addSong("B");
        p.nextSong(); // current = B
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream orig = System.out;
        System.setOut(new PrintStream(out));
        p.displayPlaylist();
        System.setOut(orig);
        String printed = out.toString();
        assertTrue(printed.contains("- A"));
        assertTrue(printed.contains("- B <-- Current Song"));
    }

    // length tests

    @Test
    public void length_empty() {
        MusicPlaylist p = new MusicPlaylistOnSequence();
        assertEquals(0, p.length());
    }

    @Test
    public void length_single() {
        MusicPlaylist p = new MusicPlaylistOnSequence();
        p.addSong("A");
        assertEquals(1, p.length());
    }

    @Test
    public void length_multi() {
        MusicPlaylist p = new MusicPlaylistOnSequence();
        p.addSong("A");
        p.addSong("B");
        p.addSong("C");
        assertEquals(3, p.length());
    }
}
