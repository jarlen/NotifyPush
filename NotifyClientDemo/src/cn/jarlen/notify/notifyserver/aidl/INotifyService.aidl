package cn.jarlen.notify.notifyserver.aidl;
import cn.jarlen.notify.notifyserver.bean.NotifyBean;
interface INotifyService {
	/**
	 * 向通知服务推送消息
	 * @param notify
	 * 消息体
	 */
	void pushNotify(in NotifyBean notify);

}
