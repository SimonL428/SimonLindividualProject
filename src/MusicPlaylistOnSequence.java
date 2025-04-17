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
public class MusicPlaylistOnSequence extends musicPlaylist {

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

    /**
     * Jump directly to the given position in the playlist.
     *
     * @param index
     *            the new current‐song index
     * @requires 0 <= index < songs.length()
     * @ensures currentIndex = index
     */
    public void goToSong(int index) {
        if (index < 0 || index >= this.songs.length()) {
            throw new IllegalArgumentException("Index out of bounds");
        }
        this.currentIndex = index;
    }

    /**
     * Removes and returns the song that is currently selected.
     *
     * @return the removed song, or null if the playlist was empty
     * @ensures if songs.length() > 0 then songs.length() = #songs – 1 AND
     *          result = old(songs.entry(old(currentIndex))) else result = null
     */
    public String removeCurrentSong() {
        if (this.songs.length() == 0) {
            return null;
        }
        String removed = this.songs.remove(this.currentIndex);
        if (this.songs.length() == 0) {
            this.currentIndex = 0;
        } else if (this.currentIndex >= this.songs.length()) {
            this.currentIndex = 0;
        }
        return removed;
    }

    /**
     * Inserts the given song at the specified position.
     *
     * @param song
     *            the song to insert
     * @param index
     *            position at which to insert
     * @requires song != null AND 0 <= index <= songs.length()
     * @ensures songs.entry(index) = song AND songs.length() = #songs + 1
     */
    public void insertSongAt(String song, int index) {
        if (song == null) {
            throw new IllegalArgumentException("song cannot be null");
        }
        if (index < 0 || index > this.songs.length()) {
            throw new IllegalArgumentException("Index out of bounds");
        }
        this.songs.add(index, song);
        // adjust currentIndex if insertion occurred before or at current
        if (this.songs.length() > 1 && index <= this.currentIndex) {
            this.currentIndex++;
        }
    }

    /**
     * Removes and returns the song at the specified position.
     *
     * @param index
     *            position of the song to remove
     * @return the removed song
     * @requires 0 <= index < songs.length()
     * @ensures songs.length() = #songs – 1
     */
    public String removeSongAt(int index) {
        if (index < 0 || index >= this.songs.length()) {
            throw new IllegalArgumentException("Index out of bounds");
        }
        String removed = this.songs.remove(index);
        // adjust currentIndex
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

}
