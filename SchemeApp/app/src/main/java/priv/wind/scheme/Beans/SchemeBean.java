package priv.wind.scheme.Beans;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Keep;

/**
 * @author Dongbaicheng
 * @version 2018/5/10
 */
@Entity
public class SchemeBean {
    @Id
    public Long id;

    public String title;

    public String content;

    public String beginTime;

    public String endTime;

    @Keep()
    public SchemeBean(Long id, String title, String content, String beginTime,
            String endTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    @Generated(hash = 103674839)
    public SchemeBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBeginTime() {
        return this.beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFormatBeginTime(){
        String time = "";

        try {
            String[] temp = this.beginTime.split("-");
            time = String.format("%s月%s日 %s:%s", temp[0], temp[1], temp[2], temp[3]);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return time;
    }

    public String getFormatEndTime(){
        String time = "";

        try {
            String[] temp = this.endTime.split("-");
            time = String.format("%s月%s日 %s:%s", temp[0], temp[1], temp[2], temp[3]);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return time;
    }
}
