// author Simon Luo
public class PlaylistUseCase2 {

        public static void main(String[] args) {
                // Build two playlists
                MusicPlaylist rock = new MusicPlaylistOnSequence();
                rock.addSong("Stairway to Heaven");
                rock.addSong("Smoke on the Water");
                rock.addSong("Back in Black");

                MusicPlaylist pop = new MusicPlaylistOnSequence();
                pop.addSong("Billie Jean");
                pop.addSong("Like a Prayer");
                pop.addSong("Uptown Funk");
                pop.addSong("Shake It Off");

                // Insert and remove by index
                rock.insertSongAt("Sweet Child o' Mine", 1);
                System.out.println("After insert in rock: " + rock);

                String songAt2 = pop.removeSongAt(2);
                System.out.println("Removed from pop at index 2: " + songAt2);
                System.out.println("Pop now: " + pop);

                // Jump to a specific song
                rock.goToSong(2);
                System.out.println("Rock current song after goToSong(2): "
                                + rock.getCurrentSong());

                // Check contains & length
                System.out.println("Rock contains “Back in Black”? "
                                + rock.contains("Back in Black"));
                System.out.println("Pop length: " + pop.length());

                // Shuffle rock and show result
                rock.shuffle();
                System.out.println("Rock after shuffle: " + rock);

                // Compare two playlists
                MusicPlaylist rockCopy = new MusicPlaylistOnSequence();
                rockCopy.transferFrom(rock); // move songs out of rockCopy, leaving rock empty
                MusicPlaylist rockRestored = new MusicPlaylistOnSequence();
                rockRestored.transferFrom(rockCopy); // restore into rockRestored

                System.out.println("rock.equals(rockRestored)? "
                                + rock.equals(rockRestored));
                System.out.println(
                                "rock.hashCode() == rockRestored.hashCode()? "
                                                + (rock.hashCode() == rockRestored
                                                                .hashCode()));
        }

}
