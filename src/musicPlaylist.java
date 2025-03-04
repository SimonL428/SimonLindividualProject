
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/* preassignment: I choose the music playlist because i feel like a playlist is a lot similar to the
* data structures that we have discussed in class, and also a lot like osu cse component.
* Moreover, it is something that I have been using for a long time so I am more familiar
* with how it should work in real life. I did do this but forgot to add this in java file
*/

/**
 * A simple implementation of a Music Playlist.
 * This class allows users to add, remove, and navigate through songs.
 */
public class musicPlaylist {
    private List<String> playlist; // Stores the songs
    private int currentSongIndex;  // Tracks the current song

    /**
     * Constructor initializes an empty playlist.
     */
    public musicPlaylist() {
        this.playlist = new ArrayList<>();
        this.currentSongIndex = 0; // Default starting index
    }

    /**
     * Adds a song to the end of the playlist.
     *
     * @param song The song to add
     */
    public void addSong(String song) {
        this.playlist.add(song);
    }

    /**
     * Removes a specified song from the playlist.
     *
     * @param song The song to remove
     * @return The removed song, or null if it was not found.
     */
    public String removeSong(String song) {
        if (this.playlist.contains(song)) {
            this.playlist.remove(song);
            return song;
        }
        return null;
    }

    /**
     * Returns the currently selected song.
     *
     * @return The current song or "No songs in playlist" if empty.
     */
    public String getCurrentSong() {
        if (this.playlist.isEmpty()) {
            return "No songs in playlist";
        }
        return this.playlist.get(this.currentSongIndex);
    }

    /**
     * Advances to the next song in the playlist, wrapping around if at the end.
     */
    public void nextSong() {
        if (!this.playlist.isEmpty()) {
            this.currentSongIndex = (this.currentSongIndex + 1) % this.playlist.size();
        }
    }

    /**
     * Moves to the previous song in the playlist, wrapping around if at the beginning.
     */
    public void previousSong() {

    }

    /**
     * Randomly shuffles the songs in the playlist.
     */
    public void shuffle() {
        Collections.shuffle(this.playlist);
        this.currentSongIndex = 0; // Reset index after shuffle
    }

    /**
     * Removes all songs from the playlist.
     */
    public void clear() {
        this.playlist.clear();
        this.currentSongIndex = 0;
    }

    /**
     * Checks whether the playlist contains a specific song.
     *
     * @param song The song to check
     * @return true if the song exists in the playlist, false otherwise.
     */
    public boolean contains(String song) {
        return this.playlist.contains(song);
    }

    /**
     * Displays the playlist for debugging and demonstration.
     */
    public void displayPlaylist() {
    }

    /**
     * Main method to demonstrate the component in action.
     */
    public static void main(String[] args) {
        musicPlaylist myPlaylist = new musicPlaylist();

        // Adding songs
        myPlaylist.addSong("Song A");
        myPlaylist.addSong("Song B");
        myPlaylist.addSong("Song C");
        myPlaylist.displayPlaylist();

        // Navigating songs
        myPlaylist.nextSong();
        System.out.println("Next Song: " + myPlaylist.getCurrentSong());

        myPlaylist.nextSong();
        System.out.println("Next Song: " + myPlaylist.getCurrentSong());

        myPlaylist.previousSong();
        System.out.println("Previous Song: " + myPlaylist.getCurrentSong());

        // Checking if a song exists
        System.out.println("Contains 'Song B'? " + myPlaylist.contains("Song B"));

        // Removing a song
        myPlaylist.removeSong("Song B");
        myPlaylist.displayPlaylist();

        // Shuffle playlist
        myPlaylist.shuffle();
        System.out.println("After Shuffle:");
        myPlaylist.displayPlaylist();

        // Clear playlist
        myPlaylist.clear();
        System.out.println("After Clearing:");
        myPlaylist.displayPlaylist();
    }
}

}

