package client.trayIcon;

import java.awt.TrayIcon.MessageType;

public class TrayIconMessage {

	private String caption;
	private String text;
	private MessageType messageType;
	
	/**
	 * Construtor
	 * @param caption
	 * @param text
	 * @param messageType
	 */
	public TrayIconMessage(String caption, String text, MessageType messageType) {
		this.caption = caption;
		this.text = text;
		this.messageType = messageType;
	}
	
	/*
	 * GETTERs AND SETTERs
	 */
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public MessageType getMessageType() {
		return messageType;
	}
	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}
}
