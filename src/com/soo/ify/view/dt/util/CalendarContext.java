/**
 *        http://www.june.com
 * Copyright © 2015 June.Co.Ltd. All Rights Reserved.
 */
package com.soo.ify.view.dt.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.soo.ify.R;

/**用于统一管理日历的上下文
 * @author Soo
 */
public class CalendarContext {
    
    private static final String DATEFORMAT = "yyyy-MM-dd hh:mm:ss";
    private static final Locale DEFAULT_LOCALE = Locale.getDefault();
    
    private Context context;
    private Locale locale = DEFAULT_LOCALE;
    private Calendar minCalendar;
    private Calendar maxCalendar;
    private Calendar curCalendar;
    
    public CalendarContext(Context context, AttributeSet attrs) {
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CalendarView);
        String minDateStr = typedArray.getString(R.styleable.CalendarView_minDate);
        String maxDateStr = typedArray.getString(R.styleable.CalendarView_maxDate);
        try {
            DateFormat SDFORMATER = new SimpleDateFormat(DATEFORMAT, locale);
            minCalendar = newCalendar(SDFORMATER.parse(minDateStr));
            maxCalendar = newCalendar(SDFORMATER.parse(maxDateStr));
            mustBefore(minCalendar, maxCalendar);
        } catch (Exception e) {
            minCalendar = newCalendar();
            minCalendar.set(Calendar.YEAR, 2016);
            minCalendar.set(Calendar.MONTH, 0);
            minCalendar.set(Calendar.DAY_OF_MONTH, 1);
            minCalendar.set(Calendar.HOUR_OF_DAY, 0);
            minCalendar.set(Calendar.MINUTE, 0);
            minCalendar.set(Calendar.SECOND, 0);
            
            maxCalendar = newCalendar();
            maxCalendar.set(Calendar.YEAR, 2050);
            maxCalendar.set(Calendar.MONTH, 11);
            maxCalendar.set(Calendar.DAY_OF_MONTH, 1);
            maxCalendar.set(Calendar.HOUR_OF_DAY, 0);
            maxCalendar.set(Calendar.MINUTE, 0);
            maxCalendar.set(Calendar.SECOND, 0);
        }
        curCalendar = newCalendar();
        
        typedArray.recycle();
        
    }
    
    public Context getContext() {
        return context;
    }
    
    public Locale getLocale() {
        return locale;
    }
    
    public Calendar getCurrentCalendar() {
        return curCalendar;
    }
    
    public Calendar getMinCalendar() {
        return minCalendar;
    }
    
    public Calendar getMaxCalendar() {
        return maxCalendar;
    }
    
    public Calendar newCalendar() {
        Calendar calendar = Calendar.getInstance(locale);
        return calendar;
    }
    
    public Calendar newCalendar(Date date) {
        Calendar calendar = newCalendar();
        calendar.setTime(date);
        return calendar;
    }
    
    public Calendar newCalendar(long millis) {
        Calendar calendar = newCalendar();
        calendar.setTimeInMillis(millis);
        return calendar;
    }
    
    public Calendar newCalendar(Calendar calendar) {
        Calendar t = newCalendar();
        t.setTimeInMillis(calendar.getTimeInMillis());
        return t;
    }
    
    public static void mustBefore(Calendar minCalendar, Calendar maxCalendar) {
        mustBefore(minCalendar.getTimeInMillis(), maxCalendar.getTimeInMillis());
    }
    
    public static void mustBefore(Date minDate, Date maxDate) {
        mustBefore(minDate.getTime(), maxDate.getTime());
    }
    
    public static void mustBefore(long minTime, long maxTime) {
        if (minTime >= maxTime) {
            throw new IllegalArgumentException("The min time must before than the max time");
        }
    }
    
    public static Calendar clamp(Calendar minCalendar, Calendar curCalendar, Calendar maxCalendar) {
        if (minCalendar.after(curCalendar)) {
            return minCalendar;
        }
        if (maxCalendar.before(curCalendar)) {
            return maxCalendar;
        }
        return curCalendar;
    }
}
