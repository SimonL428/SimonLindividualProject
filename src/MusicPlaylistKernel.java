
import components.standard.Standard;

/**
 * Kernel interface for a Music Playlist component.
 *
 * <p>
 * This interface defines the <em>minimal methods</em> needed to manage the
 * basic functionality of a music playlist. It extends the {@code Standard}
 * interface from OSU libraries, which provides generic methods such as
 * {@code clear()}, {@code newInstance()}, and {@code transferFrom()}.
 * </p>
 *
 * @author …
 */
public interface MusicPlaylistKernel extends Standard<MusicPlaylist> {

    /**
     * Adds the given song to the end of this playlist.
     *
     * @param song
     *            the song to add
     * @requires song != null
     * @ensures <pre>
     *          [song is appended to the end of this playlist]
     *          </pre>
     */
    void addSong(String song);

    /**
     * Removes the given song from this playlist, if present, and returns it.
     *
     * @param song
     *            the song to remove
     * @return the removed song, or {@code null} if not found
     * @requires song != null
     * @ensures <pre>
     *          if result != null then
     *              [song is removed and result = song]
     *          else
     *              [playlist is unchanged and result = null]
     *          </pre>
     */
    String removeSong(String song);

    /**
     * Reports the current song in the playlist.
     *
     * @return the current song, or the string "No songs in playlist" if this
     *         playlist is empty
     * @ensures <pre>
     *          [returns song at current index or default message if empty]
     *          </pre>
     */
    String getCurrentSong();

    /**
     * Advances to the next song in this playlist, wrapping around if at the
     * end.
     *
     * @ensures <pre>
     *          [currentIndex is incremented by 1 modulo playlist length]
     *          </pre>
     */
    void nextSong();

    /**
     * Inserts the given song at the specified position.
     *
     * @param song
     *            the song to insert
     * @param index
     *            position at which to insert
     * @requires song != null AND 0 <= index <= songs.length()
     * @ensures <pre>
     *          songs.entry(index) = song AND songs.length() = #songs + 1
     *          </pre>
     */
    void insertSongAt(String song, int index);

    /**
     * Removes and returns the song at the specified position.
     *
     * @param index
     *            position of the song to remove
     * @return the removed song
     * @requires 0 <= index < songs.length()
     * @ensures <pre>
     *          songs.length() = #songs - 1
     *          </pre>
     */
    String removeSongAt(int index);

    /**
     * Removes and returns the song that is currently selected.
     *
     * @return the removed song, or {@code null} if the playlist was empty
     * @ensures <pre>
     *          if songs.length() > 0 then songs.length() = #songs - 1
     *          </pre>
     */
    String removeCurrentSong();

    /**
     * Jumps directly to the given position in the playlist.
     *
     * @param index
     *            the new current‐song index
     * @requires 0 <= index < songs.length()
     * @ensures <pre>
     *          currentIndex = index
     *          </pre>
     */
    void goToSong(int index);

}
