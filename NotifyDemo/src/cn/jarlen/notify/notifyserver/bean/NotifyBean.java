package cn.jarlen.notify.notifyserver.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 消息体
 * 
 * @author jarlen
 * 
 */
public class NotifyBean implements Parcelable {

	/**
	 * 消息的id
	 */
	private int notifyId = -1;

	/**
	 * 消息类型
	 */
	private int notifyType = -1;

	/**
	 * 消息标题
	 */
	private String notifyTitle = null;

	/**
	 * 消息内容
	 */
	private String notifyContent = null;

	/**
	 * 消息时间
	 */
	private long notifyTime = 0;

	/**
	 * 消息图标
	 */
	private String notifyIcon = null;

	/**
	 * 消息其他
	 */
	private String notifyOthers = null;

	public NotifyBean() {
		// TODO Auto-generated constructor stub
	}

	public NotifyBean(Parcel parcel) {
		if (parcel != null) {
			notifyId = parcel.readInt();
			notifyType = parcel.readInt();
			notifyTitle = parcel.readString();
			notifyContent = parcel.readString();
			notifyTime = parcel.readLong();
			notifyIcon = parcel.readString();
			notifyOthers = parcel.readString();
		}
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		if (dest != null) {
			dest.writeInt(notifyId);
			dest.writeInt(notifyType);
			dest.writeString(notifyTitle);
			dest.writeString(notifyContent);
			dest.writeLong(notifyTime);
			dest.writeString(notifyIcon);
			dest.writeString(notifyOthers);
		}
	}

	public static final Parcelable.Creator<NotifyBean> CREATOR = new Parcelable.Creator<NotifyBean>() {

		@Override
		public NotifyBean createFromParcel(Parcel parcel) {
			// TODO Auto-generated method stub
			return new NotifyBean(parcel);
		}

		@Override
		public NotifyBean[] newArray(int size) {
			// TODO Auto-generated method stub
			return new NotifyBean[size];
		}

	};

	public void setNotifyContent(String notifyContent) {
		this.notifyContent = notifyContent;
	}

	public void setNotifyIcon(String notifyIcon) {
		this.notifyIcon = notifyIcon;
	}

	public void setNotifyId(int notifyId) {
		this.notifyId = notifyId;
	}

	public void setNotifyOthers(String notifyOthers) {
		this.notifyOthers = notifyOthers;
	}

	public void setNotifyTime(long notifyTime) {
		this.notifyTime = notifyTime;
	}

	public void setNotifyTitle(String notifyTitle) {
		this.notifyTitle = notifyTitle;
	}

	public void setNotifyType(int notifyType) {
		this.notifyType = notifyType;
	}

	public String getNotifyContent() {
		return notifyContent;
	}

	public String getNotifyIcon() {
		return notifyIcon;
	}

	public int getNotifyId() {
		return notifyId;
	}

	public String getNotifyOthers() {
		return notifyOthers;
	}

	public long getNotifyTime() {
		return notifyTime;
	}

	public String getNotifyTitle() {
		return notifyTitle;
	}

	public int getNotifyType() {
		return notifyType;
	}

}
