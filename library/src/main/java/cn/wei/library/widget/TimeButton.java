package cn.wei.library.widget;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by qinwei on 2015/12/1 11:43
 * email:qinwei_it@163.com
 */
public class TimeButton extends Button {
    private TimeTask timeTask;
    private int length;
    private int curTime;
    private TimeChangedListener listener;
    public TimeButton(Context context) {
        super(context);
        initializeView();
    }

    public TimeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView();
    }

    public TimeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeView();
    }

    private void initializeView() {
    }
    public void setOnTimeChangedListener(TimeChangedListener listener) {
        this.listener = listener;
    }
    public void start() {
        start(60);
    }

    public void start(int length) {
        this.length = length;
        curTime = length;
        if (listener != null) {
            setEnabled(false);
            listener.onTimeStart();
        }
        timeTask = new TimeTask();
        timeTask.execute(length);
    }

    public class TimeTask extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... params) {
            try {
                for (int i = 0; i < length; i++) {
                    curTime--;
                    publishProgress(curTime);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            if (listener != null) {
                setEnabled(true);
                listener.onTimeCompleted();
            }
            super.onPostExecute(integer);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            if (listener != null) {
                listener.onTimeChanged(values[0]);
            }
            super.onProgressUpdate(values);
        }
    }

    public interface TimeChangedListener {
        void onTimeStart();

        void onTimeChanged(int ss);

        void onTimeCompleted();
    }
}
