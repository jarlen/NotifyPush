package cn.jarlen.notify.notify.service;

/**
 * 消息队列
 * 
 * @author hjl
 * 
 */
public class NotifyQueue {

	private boolean mLock = false;
	private Action<?> sHead;

	public synchronized void add(Action<?> action) {

		if (sHead == null) {
			sHead = action;
		} else {

			Action<?> head = sHead;
			if (head != null && head.equals(action)) {
				return;
			}

			while (head.mNext != null) {
				head = head.mNext;
				if (head != null && head.equals(action)) {
					return;
				}
			}

			head.mNext = action;
		}

		tryToShowNext();
	}

	private synchronized void tryToShowNext() {
		if (sHead == null || mLock) {
			return;
		}
		mLock = true;

		Action<?> request = sHead;
		sHead = sHead.mNext;
		request.mNext = null;

		request.onAction();
	}

	public synchronized void notifyNextAction() {
		mLock = false;
		tryToShowNext();
	}

	public static abstract class Action<T> {

		protected T mBadge;
		private Action<?> mNext;

		public Action(T badge) {
			mBadge = badge;
		}

		public abstract void onAction();

		public T getBadge() {
			return mBadge;
		}

		@Override
		public boolean equals(Object o) {
			if (o == null || !(o instanceof Action)) {
				return false;
			}
			if (o == this) {
				return true;
			}
			return mBadge.equals(((Action<?>) o).mBadge);
		}
	}
}
