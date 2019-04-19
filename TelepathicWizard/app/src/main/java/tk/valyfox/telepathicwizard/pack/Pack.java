package tk.valyfox.telepathicwizard.pack;

public class Pack {
    public String name, fileName, productId;
    public boolean locked = true;

    public Pack(String fn, String n, String id) {
        fileName = fn;
        name = n.substring(1);
        productId = id;
        if(id == null) {
            locked = false;
        } else {
            locked = true;
        }
    }
}
