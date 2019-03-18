package ch.eddex.comandcon;
import java.util.List;

public class AcronymDef {
    public String sf;
    public List<LongForm> lfs;

    public static class LongForm{
        public String lf;
        public int freq;
        public int since;
        public List<Variation> vars;
    }
    public static class Variation {
        public String lf;
        public int freq;
        public int since;
    }

}