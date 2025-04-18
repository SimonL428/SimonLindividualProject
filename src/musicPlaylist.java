// File: MusicPlaylist.java

/**
 * Enhanced interface for a Music Playlist component.
 *
 * <p>
 * This interface extends {@link MusicPlaylistKernel} to provide additional
 * convenience methods, built on top of the minimal kernel operations.
 * </p>
 *
 * @author Simon Luo
 */
public interface MusicPlaylist extends MusicPlaylistKernel {

    /**
     * Moves to the previous song in this playlist, wrapping around if at the
     * beginning.MusicPlaylist
     *
     * @ensures <pre>
     *          [currentIndex is decremented by 1 modulo playlist length]
     *          </pre>
     */
    void previousSong();

    /**
     * Randomly shuffles the songs in this playlist.
     *
     * @ensures <pre>
     *          [playlist is randomly permuted and currentIndex reset to 0]
     *          </pre>
     */
    void shuffle();

    /**
     * Checks if this playlist contains the specified song.
     *
     * @param song
     *            the song to check
     * @return true if this playlist contains {@code song}, false otherwise
     * @requires song != null
     * @ensures <pre>
     *          [returns true if song is in this playlist, false otherwise]
     *          </pre>
     */
    boolean contains(String song);

    /**
     * Displays the contents of this playlist to {@code System.out}, marking the
     * current song.
     *
     * @ensures <pre>
     *          [prints each song with an indicator for current song]
     *          </pre>
     */
    void displayPlaylist();

    /**
     * Returns the number of songs in this playlist.
     *
     * @return the length of this playlist
     * @ensures <pre>
     *          [returns total number of songs in this playlist]
     *          </pre>
     */
    int length();

}
