using MusicManagerServer.Data;
using MusicManagerServer.Models;
using System.ServiceModel;

namespace MusicManagerServer.Services
{
    [ServiceContract]
    public interface ISongService
    {
        [OperationContract]
        IEnumerable<Song> GetSongsByTitle(string namePart);
    }

    public class SongService(SongDbContext context) : ISongService
    {
        private readonly SongDbContext _context = context;

        public IEnumerable<Song> GetSongsByTitle(string namePart)
        {
            return _context.Songs
                .Where(s => s.Name.ToLower().Contains(namePart.ToLower()));
        }
    }
}
