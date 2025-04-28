
using Microsoft.EntityFrameworkCore;
using MusicManagerServer.Data;
using MusicManagerServer.Services;
using SoapCore;

namespace MusicManagerServer
{
    public class Program
    {
        public static void Main(string[] args)
        {
            var builder = WebApplication.CreateBuilder(args);

            // Add services to the container.

            builder.Services.AddControllers();
            // Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
            builder.Services.AddEndpointsApiExplorer();
            builder.Services.AddSwaggerGen();

            builder.Services.AddDbContext<SongDbContext>(options =>
                options.UseSqlite("Data Source=songs.db"));

            builder.Services.AddSoapCore();
            builder.Services.AddScoped<ISongService, SongService>();

            var app = builder.Build();

            // Configure the HTTP request pipeline.
            if (app.Environment.IsDevelopment())
            {
                app.UseSwagger();
                app.UseSwaggerUI();
            }

            app.UseHttpsRedirection();

            app.UseAuthorization();

            app.MapControllers();

            app.UseSoapEndpoint<ISongService>("/Songs.wsdl", new SoapEncoderOptions());

            app.Run();
        }
    }
}
