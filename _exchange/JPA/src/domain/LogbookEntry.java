package swt6.orm.domain;

import java.time.LocalDateTime;


public class LogbookEntry {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String activity;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public LogbookEntry() {

    }

    public LogbookEntry(String activity, LocalDateTime startTime, LocalDateTime endTime) {
        super();
        this.activity = activity;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

}