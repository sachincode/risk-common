package com.sachin.risk.common.data.model;

import java.io.Serializable;

/**
 * @author shicheng.zhang
 * @since 17-7-26 下午3:18
 */
public class IpKey implements Comparable<IpKey>, Serializable {

    private static final long serialVersionUID = -3467778184282638604L;

    final long start;
    final long end;

    public IpKey(long start, long end) {
        this.start = start;
        this.end = end;
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }

    @Override
    public int compareTo(IpKey t) {
        long t1 = start - t.start;
        if (t1 < 0) {
            return -1;
        }
        long t2 = end - t.end;
        if (t1 >= 0 && t2 <= 0) {
            return 0;
        }
        return 1;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "IpKey{" + "start=" + start + ", end=" + end + '}';
    }
}