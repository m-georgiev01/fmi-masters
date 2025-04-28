using SongServiceReference;
using System.ServiceModel;

namespace MusicManagerClient
{
    internal class Program
    {
        static async Task Main(string[] args)
        {
            var client = new SongServiceClient(new BasicHttpBinding(), new EndpointAddress("http://localhost:5001/Songs.wsdl"));

            Console.WriteLine("Search by song name:");
            string searchTerm = Console.ReadLine();

            var songs = await client.GetSongsByTitleAsync(searchTerm);

            if (songs.Length == 0)
            {
                Console.WriteLine("No songs found!");
            }
            else
            {
                Console.WriteLine("\nSongs found:");

                foreach (var song in songs)
                {
                    Console.WriteLine($"- {song.Name} by {song.Author}");
                }
            }

            await client.CloseAsync();

            Console.WriteLine("\nPress any key to exit...");
            Console.ReadKey();
        }
    }
}
