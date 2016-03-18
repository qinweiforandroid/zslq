package cn.wei.library.widget.card;

public class CardDescriptor extends BaseCardDescriptor {
	private String iconUrl;
	private int resIcon;
	private String label;
	private String id;

	public CardDescriptor(String id, String iconUrl, String label) {
		this.iconUrl = iconUrl;
		this.label = label;
		this.id = id;
		this.clazz = CardClassEnum.CardItemView;
	}

	public CardDescriptor(String id, int resIcon, String label) {
		this.resIcon = resIcon;
		this.label = label;
		this.id = id;
		this.clazz = CardClassEnum.CardItemView;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public int getResIcon() {
		return resIcon;
	}

	public void setResIcon(int resIcon) {
		this.resIcon = resIcon;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
