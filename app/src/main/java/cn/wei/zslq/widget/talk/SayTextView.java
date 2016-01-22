package cn.wei.zslq.widget.talk;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class SayTextView extends TextView {
	private Context mContext;
	private ArrayList<SpanEntity> spans;

	public static class SpanEntity {
		public int startIndex;
		public int endIndex;
		public String id;
		public SpanEntity(int startIndex, int endIndex, String id) {
			this.startIndex = startIndex;
			this.endIndex = endIndex;
			this.id = id;
		}
		
		
	}

	public interface OnLightTextListener {
		void onLightTextClickListener(String id);
	}

	private OnLightTextListener listener;

	public SayTextView(Context context) {
		super(context);
		mContext = context;
	}

	public SayTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
	}

	private int mStart = -1;
	private int mEnd = -1;
	private String content;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean result = super.onTouchEvent(event);
		int action = event.getAction();
		int x = (int) event.getX();
		int y = (int) event.getY();

		x -= getTotalPaddingLeft();
		y -= getTotalPaddingTop();

		x += getScrollX();
		y += getScrollY();

		Layout layout = getLayout();
		int line = layout.getLineForVertical(y);
		int off = layout.getOffsetForHorizontal(line, x);

		CharSequence text = getText();
		if (TextUtils.isEmpty(text) || !(text instanceof Spannable)) {
			return result;
		}

		Spannable buffer = (Spannable) text;
		ClickableSpan[] link = buffer.getSpans(off, off, ClickableSpan.class);

		if (link.length != 0) {
			if (action == MotionEvent.ACTION_DOWN) {
				mStart = buffer.getSpanStart(link[0]);
				mEnd = buffer.getSpanEnd(link[0]);

				if (mStart >= 0 && mEnd >= mStart) {
					buffer.setSpan(new BackgroundColorSpan(Color.GRAY), mStart, mEnd,
							Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				}
			} else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
				if (mStart >= 0 && mEnd >= mStart) {
					buffer.setSpan(new BackgroundColorSpan(Color.TRANSPARENT), mStart, mEnd,
							Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
					mStart = -1;
					mEnd = -1;
				}
			}

			return true;
		} else {
			if (mStart >= 0 && mEnd >= mStart) {
				buffer.setSpan(new BackgroundColorSpan(Color.TRANSPARENT), mStart, mEnd,
						Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				mStart = -1;
				mEnd = -1;
			}
			Selection.removeSelection(buffer);
			return false;
		}
	}

	@Override
	public boolean hasFocusable() {
		return false;
	}

	public void initializeData(String content, ArrayList<SpanEntity> spans,
			OnLightTextListener listener) {
		this.spans = spans;
		this.listener = listener;
		this.content = content;
		Spannable span = setClickableSpan(content);
		if (null != span) {
			setMovementMethod(LinkMovementMethod.getInstance());
			setText(span);
		} else {
			setText(content);
		}
	}

	private Spannable setClickableSpan(String text) {
		if (TextUtils.isEmpty(text)) {
			return null;
		}
		SpannableString span = new SpannableString(text);
		for (int i = 0; i < spans.size(); i++) {
			SpanEntity entity = spans.get(i);
			span.setSpan(new MyClickSpan(entity.id), entity.startIndex, entity.endIndex,
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		return span;
	}

	public class MyClickSpan extends ClickableSpan {
		private String id;

		public MyClickSpan(String id) {
			this.id = id;
		}

		@Override
		public void onClick(View arg0) {
			Spannable spannable = (Spannable) ((TextView) arg0).getText();
			Selection.removeSelection(spannable);
			listener.onLightTextClickListener(id);
		}

		@Override
		public void updateDrawState(TextPaint ds) {
			ds.setUnderlineText(false);
			ds.setColor(Color.BLUE);
		}
	}
}
