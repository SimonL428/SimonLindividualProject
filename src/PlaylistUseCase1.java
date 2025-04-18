// author Simon Luo
public class PlaylistUseCase1 {

    public static void main(String[] args) {
        // Create a new playlist
        MusicPlaylist playlist = new MusicPlaylistOnSequence();

        // Basic: add a few songs
        playlist.addSong("Here Comes the Sun");
        playlist.addSong("Imagine");
        playlist.addSong("Bohemian Rhapsody");
        playlist.addSong("Hotel California");

        // Show how many we have
        System.out.println("Initial length: " + playlist.length());
        // Display with current‐song marker
        playlist.displayPlaylist();

        // Advance twice
        playlist.nextSong();
        System.out.println("After nextSong(): " + playlist.getCurrentSong());
        playlist.nextSong();
        System.out.println("After nextSong(): " + playlist.getCurrentSong());

        // Go back one
        playlist.previousSong();
        System.out
                .println("After previousSong(): " + playlist.getCurrentSong());

        // Remove the current song
        String removed = playlist.removeCurrentSong();
        System.out.println("Removed current song: " + removed);

        // Show final state
        System.out.println("Final playlist (“toString”): " + playlist);
    }

}
