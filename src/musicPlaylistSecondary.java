// File: MusicPlaylistSecondary.java

import java.util.Random;

/**
 * Secondary implementation for MusicPlaylist component.
 *
 * <p>
 * This abstract class implements the enhanced operations defined in
 * {@link MusicPlaylist} (e.g. {@code previousSong()}, {@code shuffle()},
 * {@code contains()}, {@code displayPlaylist()}, {@code length()}) in terms of
 * the minimal kernel and standard operations. Concrete subclasses must
 * implement the kernel methods and the {@code Standard} methods.
 * </p>
 *
 * @author …
 */
public abstract class MusicPlaylistSecondary implements MusicPlaylist {

    /**
     * Default message returned by {@link #getCurrentSong()} when the playlist
     * is empty.
     */
    private static final String EMPTY_MESSAGE = "No songs in playlist";

    /**
     * Reports whether the playlist is empty.
     *
     * @return {@code true} if {@link #getCurrentSong()} equals
     *         {@link #EMPTY_MESSAGE}, {@code false} otherwise.
     */
    private boolean isEmpty() {
        return this.getCurrentSong().equals(EMPTY_MESSAGE);
    }

    @Override
    public void previousSong() {
        if (!this.isEmpty()) {
            int n = this.length();
            if (n > 1) {
                // move backwards by rotating forward n-1 times
                for (int i = 0; i < n - 1; i++) {
                    this.nextSong();
                }
            }
        }
    }

    @Override
    public void shuffle() {
        MusicPlaylist temp = this.newInstance();
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

    @Override
    public boolean contains(String song) {
        if (song == null || this.isEmpty()) {
            return false;
        }
        boolean found = false;
        MusicPlaylist temp = this.newInstance();
        temp.transferFrom(this);
        String start = temp.getCurrentSong();
        do {
            if (temp.getCurrentSong().equals(song)) {
                found = true;
                break;
            }
            temp.nextSong();
        } while (!temp.getCurrentSong().equals(start));
        this.transferFrom(temp);
        return found;
    }

    @Override
    public void displayPlaylist() {
        // Remember the current song before we drain 'this'
        String originalCurrent = this.getCurrentSong();

        MusicPlaylist temp = this.newInstance();
        temp.transferFrom(this);

        // If empty, just print empty message
        if (temp.getCurrentSong().equals(EMPTY_MESSAGE)) {
            System.out.println("[Playlist is empty]");
            this.transferFrom(temp);
            return;
        }

        String start = temp.getCurrentSong();
        System.out.println("Playlist contents:");

        do {
            String s = temp.getCurrentSong();
            boolean isCurr = s.equals(originalCurrent);
            System.out.println("- " + s + (isCurr ? " <-- Current Song" : ""));
            temp.nextSong();
        } while (!temp.getCurrentSong().equals(start));

        // Restore into 'this'
        this.transferFrom(temp);
    }

    @Override
    public int length() {
        MusicPlaylist temp = this.newInstance();
        temp.transferFrom(this);
        if (temp.getCurrentSong().equals(EMPTY_MESSAGE)) {
            this.transferFrom(temp);
            return 0;
        }
        int count = 1;
        String start = temp.getCurrentSong();
        while (true) {
            temp.nextSong();
            if (temp.getCurrentSong().equals(start)) {
                break;
            }
            count++;
        }
        this.transferFrom(temp);
        return count;
    }

    /**
     * Two playlists are equal if and only if they have the same runtime class,
     * the same length, the same current song, and the same sequence of songs in
     * the same circular order.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        MusicPlaylist other = (MusicPlaylist) obj;
        // copy this
        MusicPlaylist thisCopy = this.newInstance();
        thisCopy.transferFrom(this);
        // copy other
        MusicPlaylist otherCopy = this.newInstance();
        otherCopy.transferFrom(other);
        boolean eq;
        try {
            if (thisCopy.length() != otherCopy.length()) {
                eq = false;
            } else {
                String thisCurr = thisCopy.getCurrentSong();
                String otherCurr = otherCopy.getCurrentSong();
                if (!thisCurr.equals(otherCurr)) {
                    eq = false;
                } else {
                    eq = true;
                    int n = thisCopy.length();
                    for (int i = 0; i < n; i++) {
                        if (!thisCopy.getCurrentSong()
                                .equals(otherCopy.getCurrentSong())) {
                            eq = false;
                            break;
                        }
                        thisCopy.nextSong();
                        otherCopy.nextSong();
                    }
                }
            }
        } finally {
            this.transferFrom(thisCopy);
            other.transferFrom(otherCopy);
        }
        return eq;
    }

    /**
     * Computes a hash code consistent with {@link #equals(Object)}.
     */
    @Override
    public int hashCode() {
        MusicPlaylist copy = this.newInstance();
        copy.transferFrom(this);
        int h = 1;
        int n = copy.length();
        for (int i = 0; i < n; i++) {
            String s = copy.getCurrentSong();
            h = 31 * h + (s == null ? 0 : s.hashCode());
            copy.nextSong();
        }
        try {
            return h;
        } finally {
            this.transferFrom(copy);
        }
    }

    /**
     * Returns a string representation of the playlist as a circular list
     * starting at the current song. The current song is marked with *…*.
     */
    @Override
    public String toString() {
        MusicPlaylist copy = this.newInstance();
        copy.transferFrom(this);
        StringBuilder sb = new StringBuilder();
        if (copy.getCurrentSong().equals(EMPTY_MESSAGE)) {
            sb.append("[]");
        } else {
            sb.append("[");
            String start = copy.getCurrentSong();
            String curr = this.getCurrentSong();
            do {
                String s = copy.getCurrentSong();
                if (s.equals(curr)) {
                    sb.append("*").append(s).append("*");
                } else {
                    sb.append(s);
                }
                copy.nextSong();
                if (!copy.getCurrentSong().equals(start)) {
                    sb.append(", ");
                }
            } while (!copy.getCurrentSong().equals(start));
            sb.append("]");
        }
        this.transferFrom(copy);
        return sb.toString();
    }

    // Kernel methods (addSong, removeSong, getCurrentSong, nextSong,
    // insertSongAt, removeSongAt, removeCurrentSong, goToSong) and Standard
    // methods (clear, newInstance, transferFrom) remain abstract for subclasses.

}
