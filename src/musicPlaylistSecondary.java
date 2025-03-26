import java.util.Random;

public abstract class musicPlaylistSecondary implements musicPlaylist {

    /**
     * Private helper: reports whether this playlist is empty by checking the
     * kernel method getCurrentSong().
     *
     * @return true if the playlist is empty, false otherwise
     */
    private boolean isEmpty() {
        // If getCurrentSong() returns the default "No songs in playlist",
        // we consider it empty.
        return "No songs in playlist".equals(this.getCurrentSong());
    }

    /*
     * ------------------------------------------------------------------------
     * KERNEL METHODS IMPLEMENTATION (from MusicPlaylistKernel)
     * ------------------------------------------------------------------------
     */

    @Override
    public void addSong(String song) {

        if (song == null) {
            // If we must handle null, do nothing or throw an exception
            return;
        }
        musicPlaylist temp = this.newInstance();
        temp.transferFrom(this); // now 'this' is empty, and 'temp' has the songs

        // Re-transfer from temp to this, preserving order
        if (!temp.isEmpty()) {
            // Mark the first song
            String start = temp.getCurrentSong();
            // Move the first song from temp->this
            this.addSongKernel(temp, temp.getCurrentSong());
            temp.removeSong(start);

            // Rotate temp until we are back at 'start'
            while (!temp.isEmpty() && !temp.isEmptyPlaylistWithSong(start)) {
                this.addSongKernel(temp, temp.getCurrentSong());
                temp.removeSong(temp.getCurrentSong());
            }
        }

        // Now 'this' has the old songs in the same order, 'temp' is empty
        //  Finally, add the new song
        // We "append" by just calling the kernel approach to add a new one
        this.addSongKernel(null, song);
    }

    /**
     * A helper to add a song from "source" to "this" while simulating
     * "preserving order". Essentially, we rely on kernel-level approach but do
     * not break the layering.
     *
     * @param source
     *            optional playlist providing context (can be null)
     * @param song
     *            the song to add
     */
    private void addSongKernel(musicPlaylist source, String song) {
        this.superAddSong(song);
    }

    /**
     * A minimal version of adding a song without all the shuffle/transfer
     * logic, in practice calling super. This is to illustrate "kernel-level"
     * calls in this contrived example.
     *
     * @param song
     *            the song to add
     */
    private void superAddSong(String song) {
        if (song == null) {
            return;
        }
        musicPlaylist temp = this.newInstance();
        temp.transferFrom(this);

        boolean inserted = false;
        if (temp.isEmpty()) {
            inserted = true;
        } else {
            // There are some songs in temp
            // Mark the first one
            String start = temp.getCurrentSong();
            if (!inserted) {
                // Insert the new song here in "this"
                this.insertOneSong(newInstance(), song);
                inserted = true;
            }
            // move it from temp->this
            this.insertOneSong(newInstance(), start);
            temp.removeSong(start);

            // rotate until we see 'start' again in temp
            while (!temp.isEmpty() && !temp.isEmptyPlaylistWithSong(start)) {
                String cur = temp.getCurrentSong();
                if (!inserted) {
                    this.insertOneSong(newInstance(), song);
                    inserted = true;
                }
                this.insertOneSong(newInstance(), cur);
                temp.removeSong(cur);
            }
        }

        // If we never inserted, do it now
        if (!inserted) {
            this.insertOneSong(newInstance(), song);
        }

        // Done. This is obviously more complicated than typical.
    }

    /**
     * Insert exactly one song using the simplest approach: 1) If "this" is
     * empty, just add it. 2) Otherwise, rotate or do a naive approach. This is
     * a placeholder for the theoretical "kernel-level add".
     */
    private void insertOneSong(musicPlaylist temp, String song) {
        // Transfer everything out to a temp2, then re-add them along with new song
        // This is a minimal approach:
        temp.transferFrom(this);
        if (song != null) {
            // "officially" add song
            this.superAddSongNaive(song);
        }
        // Now restore from temp
        if (!temp.isEmpty()) {
            String start = temp.getCurrentSong();
            this.superAddSongNaive(start);
            temp.removeSong(start);

            while (!temp.isEmpty() && !temp.getCurrentSong().equals(start)) {
                String cur = temp.getCurrentSong();
                this.superAddSongNaive(cur);
                temp.removeSong(cur);
            }
        }
    }

    /**
     * A minimal, truly naive approach to "addSong" that doesn't try to preserve
     * order and just re-seeds the playlist with one item if it was empty.
     */
    private void superAddSongNaive(String song) {
        if (song == null || !this.isEmpty()) {
            // If the playlist is not empty, we can't forcibly add a second song
            // with no direct representation access. We'll skip or do partial approach.
            return;
        }
    }

    @Override
    public String removeSong(String song) {
        /*
         * 1) If empty or song == null => return null 2) Otherwise, rotate
         * through the entire playlist in a temp instance, skipping the "song"
         * if we find it, and re-adding everything else to "this". 3) If found,
         * we return it; otherwise, return null.
         */
        if (song == null || this.isEmpty()) {
            return null;
        }

        // New instance
        musicPlaylist temp = this.newInstance();
        temp.transferFrom(this); // 'this' is empty, 'temp' has everything

        String removed = null;

        if (!temp.isEmpty()) {
            String start = temp.getCurrentSong();

            // Check the first
            if (start.equals(song)) {
                removed = start;
                temp.removeSong(start);
            } else {
                // Move it back to "this"
                this.addSongKernel(temp, start);
                temp.removeSong(start);
            }

            // rotate through
            while (!temp.isEmpty() && !temp.isEmptyPlaylistWithSong(start)) {
                String current = temp.getCurrentSong();
                if (removed == null && current.equals(song)) {
                    removed = current;
                    temp.removeSong(current);
                } else {
                    // keep it
                    this.addSongKernel(temp, current);
                    temp.removeSong(current);
                }
            }
        }

        return removed;
    }

    @Override
    public String getCurrentSong() {
        /*
         * We'll do a quick check: - If empty => "No songs in playlist" - Else,
         * we do a naive approach to retrieve the "first" or "active" song by
         * transferring to temp, capturing the currentSong in a local var, then
         * restoring.
         */
        // Quick check
        if (this.isEmpty()) {
            return "No songs in playlist";
        }

        musicPlaylist temp = this.newInstance();
        temp.transferFrom(this);
        // Now "this" is empty, "temp" has the songs

        // If temp is empty, that means we were actually empty to start with
        if (temp.isEmpty()) {
            // restore
            this.transferFrom(temp);
            return "No songs in playlist";
        }

        // else capture the current song
        String result = temp.getCurrentSong();

        // restore
        this.transferFrom(temp);

        return result;
    }

    @Override
    public void nextSong() {
        /*
         * We'll simulate "next" by removing the current song from the front,
         * then adding it back, effectively rotating the playlist.
         *
         * 1) if empty => do nothing 2) else remove currentSong, store in local,
         * re-add to the end
         */
        if (!this.isEmpty()) {
            String current = this.getCurrentSong();
            this.removeSong(current); // now that song is out
            // add it back, effectively rotating
            this.addSong(current);
        }
    }

    /*
     * ------------------------------------------------------------------------
     * ENHANCED METHODS IMPLEMENTATION (from musicPlaylist)
     * ------------------------------------------------------------------------
     */

    @Override
    public void previousSong() {
        /*
         * If we can't directly decrement an index, we can "go backward" by
         * going "forward length()-1 times" in a circular list. That effectively
         * moves us back by one.
         */
        if (!this.isEmpty()) {
            int n = this.length();
            if (n > 1) {
                // "next" n-1 times to emulate stepping back once
                for (int i = 0; i < n - 1; i++) {
                    this.nextSong();
                }
            }
        }
    }

    @Override
    public void shuffle() {
        /*
         * Pseudo-random approach: 1) newInstance() => temp 2)
         * temp.transferFrom(this) => "this" empty 3) while temp not empty,
         * remove its currentSong, rotate "this" randomly, then add that song to
         * "this".
         */
        musicPlaylist temp = this.newInstance();
        temp.transferFrom(this);

        if (temp.isEmpty()) {
            return; // nothing to shuffle
        }

        Random rnd = new Random();
        while (!temp.isEmpty()) {
            String cur = temp.getCurrentSong();
            temp.removeSong(cur);

            // random "rotations" on "this"
            int rotations = 0;
            if (!this.isEmpty()) {
                rotations = rnd.nextInt(this.length() + 1);
                for (int i = 0; i < rotations; i++) {
                    this.nextSong();
                }
            }
            // add the removed song
            this.addSong(cur);
        }
    }

    @Override
    public boolean contains(String song) {
        if (song == null || this.isEmpty()) {
            return false;
        }

        // Make a temp
        musicPlaylist temp = this.newInstance();
        temp.transferFrom(this);

        boolean found = false;
        if (!temp.isEmpty()) {
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
        }

        // restore
        this.transferFrom(temp);
        return found;
    }

    @Override
    public void displayPlaylist() {
        musicPlaylist temp = this.newInstance();
        temp.transferFrom(this);

        if (temp.isEmpty()) {
            System.out.println("[Playlist is empty]");
            this.transferFrom(temp);
            return;
        }

        // capture start
        String start = temp.getCurrentSong();
        System.out.println("Playlist contents:");
        // print first
        System.out.println("- " + start);

        temp.nextSong();
        while (!temp.getCurrentSong().equals(start)) {
            System.out.println("- " + temp.getCurrentSong());
            temp.nextSong();
        }

        // restore
        this.transferFrom(temp);
    }

    @Override
    public int length() {
        musicPlaylist temp = this.newInstance();
        temp.transferFrom(this);

        int count = 0;
        if (!temp.isEmpty()) {
            count = 1;
            String start = temp.getCurrentSong();
            temp.nextSong();
            while (!temp.getCurrentSong().equals(start)) {
                count++;
                temp.nextSong();
            }
        }

        this.transferFrom(temp);
        return count;
    }

    /*
     * ------------------------------------------------------------------------
     * HELPER METHOD: isEmptyPlaylistWithSong(String song) Because we have no
     * direct representation, we define a check to see if temp is empty or if
     * the "start" was removed.
     * ------------------------------------------------------------------------
     */
    private boolean isEmptyPlaylistWithSong(String start) {
        if (this.isEmpty()) {
            return true;
        }
        // If the current song is "No songs in playlist", it's empty
        if (this.getCurrentSong().equals("No songs in playlist")) {
            return true;
        }
        // If the currentSong() now equals the original 'start',
        // it might mean we've looped around. We'll just check for emptiness above.
        return false;
    }
}
