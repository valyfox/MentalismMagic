package tk.valyfox.telepathicwizard.pack;

public class Pack {
    public String name, fileName;

    public Pack(String fn, String n) {
        fileName = fn;
        name = n.substring(1);
    }
}
