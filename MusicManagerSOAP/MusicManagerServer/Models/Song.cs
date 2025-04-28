using System.Runtime.Serialization;

namespace MusicManagerServer.Models
{
    [DataContract]
    public class Song
    {
        [DataMember]
        public int Id { get; set; }

        [DataMember]
        public string Name { get; set; }

        [DataMember]
        public string Author { get; set; }
    }
}
