package swt6.orm.domain;

import javax.persistence.Entity;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Issue extends Task implements Serializable {

    public enum Severity {
        low, medium, high
    }

    private LocalDateTime foundDate;
    private LocalDateTime fixedDate;
    private Severity severity;

    public Issue() {}

    public Issue(LocalDateTime foundDate, LocalDateTime fixedDate, Severity severity) {
        this.foundDate = foundDate;
        this.fixedDate = fixedDate;
        this.severity = severity;
    }

    public Issue(String title, String description, long estimate, Status status, LocalDateTime foundDate, LocalDateTime fixedDate, Severity severity) {
        super(title, description, estimate, status);
        this.foundDate = foundDate;
        this.fixedDate = fixedDate;
        this.severity = severity;
    }

    public LocalDateTime getFoundDate() {
        return foundDate;
    }

    public void setFoundDate(LocalDateTime foundDate) {
        this.foundDate = foundDate;
    }

    public LocalDateTime getFixedDate() {
        return fixedDate;
    }

    public void setFixedDate(LocalDateTime fixedDate) {
        this.fixedDate = fixedDate;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer(super.toString());
        sb.append(" Issue{" +
                "foundDate=" + foundDate +
                ", fixedDate=" + fixedDate +
                ", severity=" + severity +
                '}');
        return sb.toString();
    }
}
