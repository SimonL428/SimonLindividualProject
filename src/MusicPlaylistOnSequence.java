
import components.sequence.Sequence;
import components.sequence.Sequence1L;

/**
 * A concrete implementation of the MusicPlaylist component using an OSU
 * {@link components.sequence.Sequence} to store song titles.
 *
 * <p>
 * <b>Representation Invariant:</b>
 * <ul>
 * <li>If {@code songs.length() == 0}, then {@code currentIndex == 0}.</li>
 * <li>If {@code songs.length() > 0}, then
 * {@code 0 <= currentIndex < songs.length()}.</li>
 * </ul>
 * </p>
 *
 * <p>
 * <b>Correspondence:</b> <br>
 * Playlist ⟷ &lt;songs.entry(0), …, songs.entry(n-1)&gt; with current song at
 * {@code currentIndex}.
 * </p>
 *
 * @author Simon Luo
 */
public class MusicPlaylistOnSequence extends MusicPlaylistSecondary {

    /**
     * Sequence of song titles.
     */
    private Sequence<String> songs;

    /**
     * Index of the current song.
     */
    private int currentIndex;

    /**
     * Constructor: Initializes an empty MusicPlaylistOnSequence.
     *
     * @ensures <pre>
     *          songs.length() = 0  and
     *          currentIndex = 0
     *          </pre>
     */
    public MusicPlaylistOnSequence() {
        this.songs = new Sequence1L<>();
        this.currentIndex = 0;
    }

    // Standard methods

    @Override
    public final void clear() {
        this.songs.clear();
        this.currentIndex = 0;
    }

    @Override
    public final MusicPlaylist newInstance() {
        return new MusicPlaylistOnSequence();
    }

    @Override
    public final void transferFrom(MusicPlaylist source) {
        if (source == this) {
            throw new IllegalArgumentException("Cannot transfer from self");
        }
        if (!(source instanceof MusicPlaylistOnSequence)) {
            throw new IllegalArgumentException(
                    "Source must be MusicPlaylistOnSequence");
        }
        MusicPlaylistOnSequence other = (MusicPlaylistOnSequence) source;
        this.songs.transferFrom(other.songs);
        this.currentIndex = other.currentIndex;
        other.currentIndex = 0;
    }

    // Kernel methods

    @Override
    public final void addSong(String song) {
        if (song == null) {
            throw new IllegalArgumentException("song cannot be null");
        }
        this.songs.add(this.songs.length(), song);
        if (this.songs.length() == 1) {
            this.currentIndex = 0;
        }
    }

    @Override
    public final String removeSong(String song) {
        if (song == null) {
            return null;
        }
        int idx = -1;
        int len = this.songs.length();
        for (int i = 0; i < len; i++) {
            if (this.songs.entry(i).equals(song)) {
                idx = i;
                break;
            }
        }
        if (idx == -1) {
            return null;
        }
        String removed = this.songs.remove(idx);
        if (this.songs.length() == 0) {
            this.currentIndex = 0;
        } else if (idx < this.currentIndex) {
            this.currentIndex--;
        } else if (idx == this.currentIndex) {
            if (this.currentIndex >= this.songs.length()) {
                this.currentIndex = 0;
            }
        }
        return removed;
    }

    @Override
    public final String getCurrentSong() {
        if (this.songs.length() == 0) {
            return "No songs in playlist";
        }
        return this.songs.entry(this.currentIndex);
    }

    @Override
    public final void nextSong() {
        if (this.songs.length() > 0) {
            this.currentIndex = (this.currentIndex + 1) % this.songs.length();
        }
    }

    @Override
    public final void insertSongAt(String song, int index) {
        if (song == null) {
            throw new IllegalArgumentException("song cannot be null");
        }
        if (index < 0 || index > this.songs.length()) {
            throw new IllegalArgumentException("index out of bounds");
        }
        this.songs.add(index, song);
        if (this.songs.length() > 1 && index <= this.currentIndex) {
            this.currentIndex++;
        }
    }

    @Override
    public final String removeSongAt(int index) {
        if (index < 0 || index >= this.songs.length()) {
            throw new IllegalArgumentException("index out of bounds");
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

    @Override
    public final String removeCurrentSong() {
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

    @Override
    public final void goToSong(int index) {
        if (index < 0 || index >= this.songs.length()) {
            throw new IllegalArgumentException("index out of bounds");
        }
        this.currentIndex = index;
    }

}
