using Microsoft.EntityFrameworkCore;
using MusicManagerServer.Models;

namespace MusicManagerServer.Data
{
    public class SongDbContext(DbContextOptions<SongDbContext> options) : DbContext(options)
    {
        public DbSet<Song> Songs { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);

            modelBuilder.Entity<Song>().HasData(
                new Song { Id = 1, Name = "Bohemian Rhapsody", Author = "Queen" },
                new Song { Id = 2, Name = "Stairway to Heaven", Author = "Led Zeppelin" },
                new Song { Id = 3, Name = "Imagine", Author = "John Lennon" },
                new Song { Id = 4, Name = "Hey Jude", Author = "The Beatles" },
                new Song { Id = 5, Name = "Smells Like Teen Spirit", Author = "Nirvana" },
                new Song { Id = 6, Name = "Hey, Soul Sister", Author = "Train" }
            );
        }
    }
}
