import components.standard.Standard;

/**
 * Kernel interface for a Music Playlist component.
 *
 * <p>
 * This interface defines the <em>minimal methods</em> needed to manage the
 * basic functionality of a music playlist. It also extends the {@code Standard}
 * interface from OSU libraries, which means methods such as {@code clear()},
 * {@code newInstance()}, and {@code transferFrom()} are already included
 * (though hidden).
 * </p>
 *
 * @author ...
 */
public interface MusicPlaylistKernel extends Standard<musicPlaylist> {

    /**
     * Adds the given song to the end of this playlist.
     *
     * @param song
     *            the song to add
     * @requires song != null // (or any other preconditions)
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
     * @ensures <pre>
     *          if [song was in this playlist] then
     *              [song is removed from this playlist and this method returns song]
     *          else
     *              [this playlist is unchanged and this method returns null]
     *          </pre>
     */
    String removeSong(String song);

    /**
     * Reports the current song in the playlist.
     *
     * @return the current song, or a default message (e.g., "No songs in
     *         playlist") if this playlist is empty
     * @ensures <pre>
     *          [returns the song at the current index, or a default string if empty]
     *          </pre>
     */
    String getCurrentSong();

    /**
     * Advances to the next song in this playlist, wrapping around if at the
     * end.
     *
     * @ensures <pre>
     *          [the current index is incremented by 1 modulo the length of this playlist]
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
     * @ensures songs.entry(index) = song AND songs.length() = #songs + 1
     */
    public void insertSongAt(String song, int index);

    /**
     * Removes and returns the song at the specified position.
     *
     * @param index
     *            position of the song to remove
     * @return the removed song
     * @requires 0 <= index < songs.length()
     * @ensures songs.length() = #songs – 1
     */
    public String removeSongAt(int index);

    /**
     * Removes and returns the song that is currently selected.
     *
     * @return the removed song, or null if the playlist was empty
     * @ensures if songs.length() > 0 then songs.length() = #songs – 1 AND
     *          result = old(songs.entry(old(currentIndex))) else result = null
     */
    public String removeCurrentSong();

    /**
     * Jump directly to the given position in the playlist.
     *
     * @param index
     *            the new current‐song index
     * @requires 0 <= index < songs.length()
     * @ensures currentIndex = index
     */
    public void goToSong(int index);

}
