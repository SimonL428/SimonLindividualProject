import java.util.Random;

public abstract class MusicPlaylistSecondary implements musicPlaylist {

    /**
     * The exact default message that {@code getCurrentSong()} returns when the
     * playlist is empty.
     */
    private static final String EMPTY_MESSAGE = "No songs in playlist";

    /**
     * Reports whether the playlist is empty, by observing the kernel method
     * {@link #getCurrentSong()}.
     *
     * @return {@code true} if {@code getCurrentSong()} equals
     *         {@link #EMPTY_MESSAGE}, {@code false} otherwise.
     */
    private boolean isEmpty() {
        return this.getCurrentSong().equals(EMPTY_MESSAGE);
    }

    /**
     * Moves to the previous song in this playlist, wrapping around if at the
     * beginning, by calling {@link #nextSong()} a total of {@code length() - 1}
     * times.
     */
    @Override
    public void previousSong() {
        if (!this.isEmpty()) {
            int n = this.length();
            if (n > 1) {
                for (int i = 0; i < n - 1; i++) {
                    this.nextSong();
                }
            }
        }
    }

    /**
     * Randomly permutes the songs in this playlist.
     * <p>
     * Algorithm:
     * <ol>
     * <li>Extract all songs into a temporary playlist via
     * {@link #newInstance()} and {@link #transferFrom(musicPlaylist)}.</li>
     * <li>Repeatedly remove the current song from the temp playlist, rotate
     * this playlist by a random count (0..length), then
     * {@link #addSong(String)} that removed song.</li>
     * </ol>
     * After the loop, this playlist is a pseudo‐random shuffle of the original.
     * </p>
     */
    @Override
    public void shuffle() {
        musicPlaylist temp = this.newInstance();
        temp.transferFrom(this);
        Random rnd = new Random();
        while (!temp.getCurrentSong().equals(EMPTY_MESSAGE)) {
            String song = temp.getCurrentSong();
            temp.removeSong(song);
            if (!this.getCurrentSong().equals(EMPTY_MESSAGE)) {
                int rotations = rnd.nextInt(this.length() + 1);
                for (int i = 0; i < rotations; i++) {
                    this.nextSong();
                }
            }
            this.addSong(song);
        }
    }

    /**
     * Reports whether the given song is in this playlist.
     * <p>
     * Algorithm:
     * <ol>
     * <li>Drain this playlist into a temp playlist.</li>
     * <li>Rotate through temp once, comparing each {@link #getCurrentSong()} to
     * {@code song}.</li>
     * <li>After scanning, {@link #transferFrom(musicPlaylist)} back to
     * restore.</li>
     * </ol>
     * </p>
     *
     * @param song
     *            the song to search for
     * @return {@code true} if {@code song} was found, {@code false} otherwise
     */
    @Override
    public boolean contains(String song) {
        if (song == null) {
            return false;
        }
        if (this.isEmpty()) {
            return false;
        }
        boolean found = false;
        musicPlaylist temp = this.newInstance();
        temp.transferFrom(this);
        String start = temp.getCurrentSong();
        if (start.equals(song)) {
            found = true;
        }
        temp.nextSong();
        while (!temp.getCurrentSong().equals(start)) {
            if (temp.getCurrentSong().equals(song)) {
                found = true;
            }
            temp.nextSong();
        }
        this.transferFrom(temp);
        return found;
    }

    /**
     * Prints the entire playlist to {@code System.out}, marking the current
     * song with " <-- Current Song".
     * <p>
     * Drains and restores the playlist via a temp instance to avoid direct
     * access to any internal representation.
     * </p>
     */
    @Override
    public void displayPlaylist() {
        musicPlaylist temp = this.newInstance();
        temp.transferFrom(this);
        if (temp.getCurrentSong().equals(EMPTY_MESSAGE)) {
            System.out.println("[Playlist is empty]");
            this.transferFrom(temp);
            return;
        }
        String start = temp.getCurrentSong();
        System.out.println("Playlist contents:");
        System.out.println("- " + start
                + (start.equals(this.getCurrentSong()) ? " <-- Current Song"
                        : ""));
        temp.nextSong();
        while (!temp.getCurrentSong().equals(start)) {
            String s = temp.getCurrentSong();
            System.out.println("- " + s
                    + (s.equals(this.getCurrentSong()) ? " <-- Current Song"
                            : ""));
            temp.nextSong();
        }
        this.transferFrom(temp);
    }

    /**
     * Counts the number of songs in this playlist by rotating through once.
     *
     * @return the number of songs
     */
    @Override
    public int length() {
        musicPlaylist temp = this.newInstance();
        temp.transferFrom(this);
        if (temp.getCurrentSong().equals(EMPTY_MESSAGE)) {
            this.transferFrom(temp);
            return 0;
        }
        int count = 1;
        String start = temp.getCurrentSong();
        temp.nextSong();
        while (!temp.getCurrentSong().equals(start)) {
            count++;
            temp.nextSong();
        }
        this.transferFrom(temp);
        return count;
    }

    /*
     * Kernel methods (addSong, removeSong, getCurrentSong, nextSong) and
     * Standard methods (clear, newInstance, transferFrom) remain abstract, to
     * be implemented by a concrete subclass.
     */

     //TODO： implement toString, equals, hashCode
     // TODO: delete contracts as they're inherited
}