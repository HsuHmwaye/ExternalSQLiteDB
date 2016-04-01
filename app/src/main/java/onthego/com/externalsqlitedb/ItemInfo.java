package onthego.com.externalsqlitedb;

/**
 * Created by onthego on 22/3/2016.
 */
public class ItemInfo {

private String ItemId;

    public ItemInfo(String itemId, String name) {
        ItemId = itemId;
        Name = name;
    }

    public ItemInfo() {
    }

    private String Name;

    @Override
    public String toString() {
        return "ItemId='" + ItemId + '\'' +
                ", \n Name='" + Name + '\'' ;

    }

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String itemId) {
        ItemId = itemId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
