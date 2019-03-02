package com.example.blusmart.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import me.jerryhanks.timelineview.model.Status;
import me.jerryhanks.timelineview.model.TimeLine;

public class MyTimeline extends TimeLine {

    private String title;

    public MyTimeline(Status status, @NonNull String title) {
        super(status);
        this.title = title;
    }


    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && this.getClass() != obj.getClass()) {
            return false;
        }
        if (obj instanceof MyTimeline) {
            return title.equals(((MyTimeline) obj).title);
        }

        return true;

    }

    @Override
    public int hashCode() {
        if (title != null) {
            return title.hashCode();
        } else {
            return 0;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}