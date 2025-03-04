/**
 * Enhanced interface for Music Playlist component.
 *
 * <p>
 * This interface extends {@link MusicPlaylistKernel} to provide more advanced
 * functionality, while still allowing the implementation to rely on the minimal
 * kernel methods and the {@code Standard} methods.
 * </p>
 *
 * <p>
 * Clients of this interface should use these methods for convenience but
 * understand that they are built on top of the kernel operations.
 * </p>
 *
 * @author ...
 */
public interface MusicPlaylist extends MusicPlaylistKernel {

    /**
     * Moves to the previous song in this playlist, wrapping around if at the
     * beginning.
     *
     * @ensures <pre>
     *          [the current index is decremented by 1 modulo the length of this playlist]
     *          </pre>
     */
    void previousSong();

    /**
     * Randomly shuffles the songs in this playlist.
     *
     * @ensures <pre>
     *          [the order of songs in this playlist is randomly permuted, and
     *           the current index is set to 0 or some implementation-defined value]
     *          </pre>
     */
    void shuffle();

    /**
     * Checks if this playlist contains the specified song.
     *
     * @param song
     *            the song to check for
     * @return true if this playlist contains {@code song}, false otherwise
     * @requires song != null
     * @ensures <pre>
     *          [returns true if song is in this playlist, false otherwise]
     *          </pre>
     */
    boolean contains(String song);

    /**
     * Displays the contents of this playlist (e.g., for debugging or demo).
     *
     * @ensures <pre>
     *          [the contents of this playlist are displayed to the user in some format]
     *          </pre>
     */
    void displayPlaylist();

    /**
     * (Optional) Returns the number of songs in this playlist.
     *
     * @return the length of this playlist
     * @ensures <pre>
     *          [returns the total number of songs in this playlist]
     *          </pre>
     */
    int length();

    /*
     * Note: We do NOT write method bodies here, because this is an interface.
     * Implementation will happen in your concrete class.
     */
}
