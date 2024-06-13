package model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "task_table")
public class ToDoModel {
    @ColumnInfo(name="task_id")
    @PrimaryKey(autoGenerate = true)
    public long taskId;

    public String task;
    public Priority priority;
    @ColumnInfo(name="due_date")
    public Date date;
    public  Date time;
    @ColumnInfo(name="is_done")
    public boolean isDone;
    @ColumnInfo(name="created_at")
    public Date dateCreated;

    public ToDoModel(String task, Priority priority, Date date, Date time, boolean isDone, Date dateCreated) {
        this.task = task;
        this.priority = priority;
        this.date = date;
        this.time = time;
        this.isDone = isDone;
        this.dateCreated = dateCreated;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return "ToDoModel{" +
                "taskId=" + taskId +
                ", task='" + task + '\'' +
                ", priority=" + priority +
                ", date=" + date +
                ", time=" + time +
                ", isDone=" + isDone +
                ", dateCreated=" + dateCreated +
                '}';
    }
}



