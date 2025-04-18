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

    @Override
    public String toString() {
        int n = this.length();
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < n; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(this.getCurrentSong());
            this.nextSong();
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof musicPlaylist)) {
            return false;
        }
        musicPlaylist other = (musicPlaylist) obj;
        return this.toString().equals(other.toString());
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    /*
     * Kernel methods (addSong, removeSong, getCurrentSong, nextSong) and
     * Standard methods (clear, newInstance, transferFrom) remain abstract, to
     * be implemented by a concrete subclass.
     */

    // TODO: delete contracts as they're inherited
}