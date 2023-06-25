package Timer;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class TimeScheduled {

    private static Timer timer;

    public static void main(String[] args) {
        setScheduledTask(9, 0, 0); // 设置每天早上9点执行任务
    }

    /**
     * 设置定时任务，每天在指定时间执行一次
     * @param hour 小时数（24小时制）
     * @param minute 分钟数
     * @param second 秒数
     */
    public static void setScheduledTask(int hour, int minute, int second) {
        Calendar currentTime = Calendar.getInstance();
        Calendar scheduledTime = (Calendar)currentTime.clone();
        scheduledTime.set(Calendar.HOUR_OF_DAY, hour);
        scheduledTime.set(Calendar.MINUTE, minute);
        scheduledTime.set(Calendar.SECOND, second);

        // 如果当前时间已经超过了设定的时间，则将任务执行时间加1天，避免立即执行
        if (scheduledTime.before(currentTime)) {
            scheduledTime.add(Calendar.DAY_OF_MONTH, 1);
        }

        long delayMillis = scheduledTime.getTimeInMillis() - currentTime.getTimeInMillis();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // 在这里编写定时任务需要执行的操作，例如打印日志或发送邮件等。
                SendMessageDing sendMessageDing = new SendMessageDing();
                sendMessageDing.sendMessage();
                System.out.println("定时任务执行了！");
            }
        };

        timer = new Timer();
        timer.scheduleAtFixedRate(task, delayMillis, 24 * 60 * 60 * 1000);
        System.out.println("定时任务已启动，每天" + hour + ":" + minute + ":" + second + "执行一次。");
    }
}
