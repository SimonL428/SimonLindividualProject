import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.sound.midi.Sequence;

import components.sequence.Sequence1L;

/**
 * A concrete implementation for a Music Playlist component.
 *
 * <p>
 * This component represents a playlist as an ordered sequence of song t tles
 * (using an OSU {@code Sequence<String>}) plus an integer {@code currentIndex}
 * that denotes the current song.
 * </p>
 *
 * <p>
 * <b>Convention (Representation Invariant):</b> l>
 * <li>If {@code songs.length() == 0}, then {@code currentIndex == 0}</li>
 * <li>If {@code songs.length() > 0}, then 0 ≤ {@code currentIndex} <
 * {@code songs.length()}.</li>
 * </ul>
 * </p>
 *
 * <p>
 * <b>Correspondence:</b>
 *
 * The abstract playlist <s0, s1, …, s(n-1)> with current song sₖ is represented
 * by: l>
 * <li>{@code songs.entry(0) == s0, …, songs.entry(n-1) == s(n-1)}</li>
 * <li>{@code currentIndex == k} (if {@code songs.length() > 0}).</li>
 * </ul>
 *
 * If the playlist is empty, we define the current song to be "No songs in
 * playlist".
 * </p>
 *
 * <p>
 * This class implements both the kernel methods (such as {@code addSong},
 * {@code removeSong}, {@code getCurrentSong}, and {@code nextSo
 * g}) and the Standard methods (such as {@code cle
 * r}, {@code newInstance}, {@code transf as well as enhanc d operations (like
 * {@code previousSong}, {@code shuffle}, {@code contains}, {@code
 * displayPlaylist}, and {@code length}).
 * </p>
 *
 * @author ...
 */
public class MusicPlaylistKernel implements musicPlaylist {

    /**
     * Sequence of song titles.
     */
    private Sequence<String> songs;

    /**
     * Index of the current song.
     */
    private int currentIndex;

    /**
     * Constructor: Initializes an empty MusicPlaylist.
     *
     * @ensures a new playlist is empty and {@code currentIndex == 0}.
     */
    public MusicPlaylist1() {
        this.songs = new Sequence1L<String>();
        this.currentIndex = 0;
    }

    // ==========================
    // Standard Methods (from Standard interface)
    // ==========================

    /**
     * Clears this playlist.
     *
     * 
     * @ensures this playlist becomes empty and {@code currentIndex} is reset to
     *          0.
     */
    @Override
    public void clear() {
        this.songs.clear();
        this.currentIndex = 0;
    }

    /**
     * Returns a new, empty instance of MusicPlaylist1.
     *
     * @return a new empty MusicPlaylist1 instance.
     * @ensures the returned instance is empty.
     */
    @Override
    public MusicPlaylist1 newInstance() {
        return new MusicPlaylist1();
    }

    /**
     * Transfers ll data from {@code source} to this playlist. The source
     * becomes empty.
     *
     * 
     * @param source
     *            the playlist from which to transfer data.
     * @requires source != null and source is not this. thi playlist has exactly
     *           the songs that were in source, and source becomes empty.
     */
    @Override
    public void transferFrom(musicPlaylist source) {
        if (source == this) {
            throw new IllegalArgumentException("Cannot transfer from self");
        }
        if (!(source instanceof MusicPlaylist1)) {
            throw new IllegalArgumentException(
                    "source must be an instance of MusicPlaylist1");
        }
        MusicPlaylist1 src = (MusicPlaylist1) source;
        this.songs.transferFrom(src.songs);
        this.currentIndex = src.currentIndex;
        // Invalidate the source.
        src.currentIndex = 0;
    }

    // ==========================
    // Kernel Methods (minimal operations)
    // ==========================

    /**
     * Adds the given song to the end of this playlist.
     *
     * 
     * @param song
     *            the song to add.
     * @requires song != null.
     * @ensures the song is appended at the end of the playlist.
     */
    @Override
    public void addSong(String song) {
        if (song == null) {
            throw new IllegalArgumentException("song cannot be null");
        }
        this.songs.add(this.songs.length(), song);
        if (this.songs.length() == 1) {
            this.currentIndex = 0;
        }
    }

    /**
     * Removes the given song from the playlist, if present, and returns it.
     *
     * 
     * @param song
     *            the song to remove.
     * @return the removed song if found; otherwise, {@code null}.
     * @ensures if the song was in the playlist, it is removed and the
     *          {@code currentIndex} is adjusted to remain valid.
     */
    @Override
    public String removeSong(String song) {
        if (song == null) {
            return null;
        }
        int index = -1;
        for (int i = 0; i < this.songs.length(); i++) {
            if (this.songs.entry(i).equals(song)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            return null;
        }
        String removed = this.songs.remove(index);
        if (this.songs.length() == 0) {
            this.currentIndex = 0;
        } else if (index < this.currentIndex) {
            this.currentIndex--;
        } else if (index == this.currentIndex) {
            if (this.currentIndex >= this.songs.length()) {
                this.currentIndex = 0;
            }
        }
        return removed;
    }

    /**
     * Returns the current song in the playlist.
     *
     * 
     * @return the song at {@code currentIndex} or "No songs in playlist" if
     *         empty.
     * @ensures returns the current song according to the representation.
     */
    @Override
    public String getCurrentSong() {
        if (this.songs.length() == 0) {
            return "No songs in playlist";
        }
        return this.songs.entry(this.currentIndex);
    }

    /**
     * Advances to the next song in the playlist, wrapping around if needed.
     *
     * @ensures {@code currentIndex} is incremented modulo the number of songs.
     */
    @Override
    public void nextSong() {
        if (this.songs.length() > 0) {
            this.currentIndex = (this.currentIndex + 1) % this.songs.length();
        }
    }

    // ==========================
    // Enhanced Methods (built atop kernel methods)
    // ==========================

    /**
     *
     * Moves to the previous song in the playlist, wrapping around if at the
     * beginning.
     *
     * @ensures {@code currentIndex} is decremented modulo the number of songs.
     */
    @Override
    public void previousSong() {
        if (this.songs.length() > 0) {
            this.currentIndex = (this.currentIndex - 1 + this.songs.length())
                    % this.songs.length();
        }
    }

    /**
     * Randomly s uffles the songs in the playlist. Resets the
     * {@code currentIndex} to 0.
     *
     * 
     * @ensures the order of songs is randomly permuted and {@code currentIndex}
     *          becomes 0.
     */
    @Override
    public void shuffle() {
        if (this.songs.length() > 0) {
            // Copy songs into a temporary ArrayList.
            List<String> tempList = new ArrayList<>();
            for (int i = 0; i < this.songs.length(); i++) {
                tempList.add(this.songs.entry(i));
            }
            // Shuffle using Java's Collections.shuffle.
            Collections.shuffle(tempList);
            // Clear the sequence and re-add the shuffled songs.
            this.songs.clear();
            for (String s : tempList) {
                this.songs.add(this.songs.length(), s);
            }
            this.currentIndex = 0;
        }
    }

    /**
     * Checks whether the playlist contains the specified song.
     *
     * 
     * @param song
     *            the song to search for.
     * @return true if the playlist contains the song; false otherwise.
     * @requires song != null.
     * @ensures returns true if and only if the song is present in the playlist.
     */
    @Override
    public boolean contains(String song) {
        if (song == null) {
            throw new IllegalArgumentException("song cannot be null");
        }
        for (int i = 0; i < this.songs.length(); i++) {
            if (this.songs.entry(i).equals(song)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Displays the contents of the playlist. The current song is marked.
     *
     * 
     * @ensures prints all songs in order with an indication of which song is
     *          current.
     */
    @Override
    public void displayPlaylist() {
        if (this.songs.length() == 0) {
            System.out.println("[Playlist is empty]");
            return;
        }
        System.out.println("Playlist contents:");
        for (int i = 0; i < this.songs.length(); i++) {
            String marker = (i == this.currentIndex) ? " <-- Current Song" : "";
            System.out.println("- " + this.songs.entry(i) + marker);
        }
    }

    /**
     * Returns the number of songs in the playlist.
     *
     * @return the length of the playlist.
     * @ensures returns {@code songs.length()}.
     */
    @Override
    public int length() {
        return this.songs.length();
    }
}
