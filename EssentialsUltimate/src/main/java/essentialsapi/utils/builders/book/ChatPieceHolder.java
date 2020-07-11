
package essentialsapi.utils.builders.book;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;

public class ChatPieceHolder {

	
	public ChatPieceHolder(String text, ClickEvent click, HoverEvent hover) {
		this.text = text;
		this.click = click;
		this.hover = hover;
	}
	public ChatPieceHolder(String text) {
		this(text, null, null);
	}
	
	private String text;
	private ClickEvent click;
	private HoverEvent hover;
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public ClickEvent getClick() {
		return click;
	}
	
	public void setClick(ClickEvent click) {
		this.click = click;
	}
	
	public HoverEvent getHover() {
		return hover;
	}
	
	public void setHover(HoverEvent hover) {
		this.hover = hover;
	}
	
	public void setClickLink(String link) {
		setClick(new ClickEvent(ClickEvent.Action.OPEN_URL, link));
	}
	public void setHoverText(String text) {
		setHover(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(text).create()));
	}
}