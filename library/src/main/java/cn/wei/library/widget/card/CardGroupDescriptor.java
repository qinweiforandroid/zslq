package cn.wei.library.widget.card;

import java.util.ArrayList;

public class CardGroupDescriptor {
    public ArrayList<? extends BaseCardDescriptor> cardDescriptors;
    public String groupId;
    public String groupTitle;
    public int groupIcon;
    public String groupIconUrl;

    public CardGroupDescriptor(ArrayList<? extends BaseCardDescriptor> cardDescriptors, String groupTitle, String groupId, int groupIcon) {
        super();
        this.cardDescriptors = cardDescriptors;
        this.groupTitle = groupTitle;
        this.groupIcon = groupIcon;
        this.groupId = groupId;
    }
}
