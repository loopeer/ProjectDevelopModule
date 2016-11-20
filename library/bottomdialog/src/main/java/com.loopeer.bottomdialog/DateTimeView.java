package com.loopeer.bottomdialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IntDef;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

public class DateTimeView extends LinearLayout {

    private static final int DEFAULT_START_YEAR = 1900;
    private static final int DEFAULT_END_YEAR = 2100;

    @SuppressLint("UniqueConstants")
    @Retention(RetentionPolicy.RUNTIME)
    @IntDef(value = {
            DATE_VIEW, TIME_VIEW, DATE_TIME_VIEW,
    })
    public @interface DateTimeMode {
    }

    public static final int DATE_VIEW = 0x01;
    public static final int TIME_VIEW = 0x04;
    public static final int DATE_TIME_VIEW = DATE_VIEW|TIME_VIEW;

    private static final TwoDigitFormatter sTwoDigitFormatter = new TwoDigitFormatter();
    private final java.text.DateFormat mDateFormat = new SimpleDateFormat(DATE_FORMAT);

    private static final String DATE_FORMAT = "MM/dd/yyyy";
    private NumberPicker mPickerMonth;
    private NumberPicker mPickerDay;
    private NumberPicker mPickerYear;
    private NumberPicker mPickerHour;
    private NumberPicker mPickerMinute;
    private View mViewTimeColon;
    private View mViewSpace;
    private OnDateTimeChangedListener mOnDateTimeChangedListener;
    private Calendar mTempCalendar;
    private Calendar mCurrentCalendar;
    private Calendar mMinDate;
    private Calendar mMaxDate;
    private int mNumberOfMonths;
    private String[] mShortMonths;
    private int mShowType;

    public DateTimeView(Context context) {
        this(context, null);
    }

    public DateTimeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DateTimeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

        setCurrentCalendar(Calendar.getInstance());

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.DateTimeView);
        int startYear = array.getInt(R.styleable.DateTimeView_startYearValue,
                DEFAULT_START_YEAR);
        int endYear = array.getInt(R.styleable.DateTimeView_endYearValue, DEFAULT_END_YEAR);
        String minDate = array.getString(R.styleable.DateTimeView_minDateValue);
        String maxDate = array.getString(R.styleable.DateTimeView_maxDateValue);
        mShowType = array.getInt(R.styleable.DateTimeView_showDateTimeView, DATE_TIME_VIEW);
        array.recycle();

        inflate(getContext(), R.layout.view_date_time_picker, this);
        mPickerYear = (NumberPicker) findViewById(R.id.picker_year);
        mPickerMonth = (NumberPicker) findViewById(R.id.picker_month);
        mPickerDay = (NumberPicker) findViewById(R.id.picker_day);
        mPickerHour = (NumberPicker) findViewById(R.id.picker_hour);
        mPickerMinute = (NumberPicker) findViewById(R.id.picker_minute);
        mViewSpace = findViewById(R.id.view_space);
        mViewTimeColon = findViewById(R.id.view_time_colon);

        updateView();
        NumberPicker.OnValueChangeListener onChangeListener = new NumberPicker.OnValueChangeListener() {
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mTempCalendar.setTimeInMillis(mCurrentCalendar.getTimeInMillis());
                // take care of wrapping of days and months to update greater fields
                if (picker == mPickerDay) {
                    int maxDayOfMonth = mTempCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                    if (oldVal == maxDayOfMonth && newVal == 1) {
                        mTempCalendar.add(Calendar.DAY_OF_MONTH, 1);
                    } else if (oldVal == 1 && newVal == maxDayOfMonth) {
                        mTempCalendar.add(Calendar.DAY_OF_MONTH, -1);
                    } else {
                        mTempCalendar.add(Calendar.DAY_OF_MONTH, newVal - oldVal);
                    }
                } else if (picker == mPickerMonth) {
                    if (oldVal == 11 && newVal == 0) {
                        mTempCalendar.add(Calendar.MONTH, 1);
                    } else if (oldVal == 0 && newVal == 11) {
                        mTempCalendar.add(Calendar.MONTH, -1);
                    } else {
                        mTempCalendar.add(Calendar.MONTH, newVal - oldVal);
                    }
                } else if (picker == mPickerYear) {
                    mTempCalendar.set(Calendar.YEAR, newVal);
                } else if (picker == mPickerHour) {
                    int minValue = mPickerHour.getMinValue();
                    int maxValue = mPickerHour.getMaxValue();
                    if (oldVal == maxValue && newVal == minValue) {
                        mTempCalendar.add(Calendar.HOUR_OF_DAY, 1);
                    } else if (oldVal == minValue && newVal == maxValue) {
                        mTempCalendar.add(Calendar.HOUR_OF_DAY, -1);
                    } else {
                        mTempCalendar.add(Calendar.HOUR_OF_DAY, newVal - oldVal);
                    }
                } else if (picker == mPickerMinute) {
                    int minValue = mPickerMinute.getMinValue();
                    int maxValue = mPickerMinute.getMaxValue();
                    if (oldVal == maxValue && newVal == minValue) {
                        mTempCalendar.add(Calendar.MINUTE, 1);
                    } else if (oldVal == minValue && newVal == maxValue) {
                        mTempCalendar.add(Calendar.MINUTE, -1);
                    } else {
                        mTempCalendar.add(Calendar.MINUTE, newVal - oldVal);
                    }
                } else {
                    throw new IllegalArgumentException();
                }
                // now set the date to the adjusted one
                update(mTempCalendar);
            }
        };

        mPickerHour.setMinValue(0);
        mPickerHour.setMaxValue(23);
        mPickerHour.setFormatter(sTwoDigitFormatter);
        mPickerHour.setOnValueChangedListener(onChangeListener);

        mPickerMinute.setMinValue(0);
        mPickerMinute.setMaxValue(59);
        mPickerMinute.setOnLongPressUpdateInterval(100);
        mPickerMinute.setFormatter(sTwoDigitFormatter);
        mPickerMinute.setOnValueChangedListener(onChangeListener);

        mPickerYear.setOnLongPressUpdateInterval(100);
        mPickerYear.setOnValueChangedListener(onChangeListener);

        mPickerMonth.setFormatter(sTwoDigitFormatter);
        mPickerMonth.setMinValue(0);
        mPickerMonth.setMaxValue(mNumberOfMonths - 1);
        mPickerMonth.setDisplayedValues(mShortMonths);
        mPickerMonth.setOnLongPressUpdateInterval(200);
        mPickerMonth.setOnValueChangedListener(onChangeListener);

        mPickerDay.setOnLongPressUpdateInterval(100);
        mPickerDay.setOnValueChangedListener(onChangeListener);
        mPickerDay.setFormatter(sTwoDigitFormatter);

        mTempCalendar.clear();
        if (!TextUtils.isEmpty(minDate)) {
            if (!parseDate(minDate, mTempCalendar)) {
                mTempCalendar.set(startYear, 0, 1);
            }
        } else {
            mTempCalendar.set(startYear, 0, 1);
        }
        setMinDate(mTempCalendar.getTimeInMillis());

        // set the max date giving priority of the maxDate over endYear
        mTempCalendar.clear();
        if (!TextUtils.isEmpty(maxDate)) {
            if (!parseDate(maxDate, mTempCalendar)) {
                mTempCalendar.set(endYear, 11, 31);
            }
        } else {
            mTempCalendar.set(endYear, 11, 31);
        }
        setMaxDate(mTempCalendar.getTimeInMillis());

        // initialize to current date
        mCurrentCalendar.setTimeInMillis(System.currentTimeMillis());
        update(mCurrentCalendar);
    }

    public void setDateTimeMode(@DateTimeMode int mode){
        updateView(mode);
    }

    private void updateView(int mode){
        mShowType = mode;
        updateView();
    }

    private void updateView() {
        switch (mShowType){
            case DATE_VIEW:
                mViewTimeColon.setVisibility(GONE);
                mViewSpace.setVisibility(GONE);
                mPickerMinute.setVisibility(GONE);
                mPickerHour.setVisibility(GONE);

                mPickerYear.setVisibility(VISIBLE);
                mPickerMonth.setVisibility(VISIBLE);
                mPickerDay.setVisibility(VISIBLE);
                break;
            case TIME_VIEW:
                mViewSpace.setVisibility(GONE);
                mPickerYear.setVisibility(GONE);
                mPickerMonth.setVisibility(GONE);
                mPickerDay.setVisibility(GONE);

                mViewTimeColon.setVisibility(VISIBLE);
                mPickerMinute.setVisibility(VISIBLE);
                mPickerHour.setVisibility(VISIBLE);
                break;
            case DATE_TIME_VIEW:
                mViewSpace.setVisibility(VISIBLE);
                mPickerYear.setVisibility(VISIBLE);
                mPickerMonth.setVisibility(VISIBLE);
                mPickerDay.setVisibility(VISIBLE);
                mViewTimeColon.setVisibility(VISIBLE);
                mPickerMinute.setVisibility(VISIBLE);
                mPickerHour.setVisibility(VISIBLE);
                break;
        }
    }

    private boolean parseDate(String date, Calendar outDate) {
        try {
            outDate.setTime(mDateFormat.parse(date));
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public void update(Calendar calendar) {
        update(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE));
    }

    public Calendar getCurrentDate() {
        return mCurrentCalendar;
    }

    public void update(int year, int month, int dayOfMonth, int hour, int minute) {
        setDate(year, month, dayOfMonth, hour, minute);
        updateSpinners();
        notifyDateTimeChanged();
    }

    private void setDate(int year, int month, int dayOfMonth, int hour, int minute) {
        mCurrentCalendar.set(year, month, dayOfMonth, hour, minute);
        if (mCurrentCalendar.before(mMinDate)) {
            mCurrentCalendar.setTimeInMillis(mMinDate.getTimeInMillis());
        } else if (mCurrentCalendar.after(mMaxDate)) {
            mCurrentCalendar.setTimeInMillis(mMaxDate.getTimeInMillis());
        }
    }

    private void notifyDateTimeChanged() {
        if (mOnDateTimeChangedListener != null) {
            mOnDateTimeChangedListener.onTimeChanged(
                    this,
                    getYear(),
                    getMonth(),
                    getDayOfMonth(),
                    getCurrentHour(),
                    getCurrentMinute());
        }
    }

    public int getYear() {
        return mCurrentCalendar.get(Calendar.YEAR);
    }

    public int getMonth() {
        return mCurrentCalendar.get(Calendar.MONTH);
    }

    public int getDayOfMonth() {
        return mCurrentCalendar.get(Calendar.DAY_OF_MONTH);
    }

    public void setCurrentCalendar(Calendar calendar) {
        mTempCalendar = getCalendarForLocale(calendar);
        mMinDate = getCalendarForLocale(calendar);
        mMaxDate = getCalendarForLocale(calendar);
        mCurrentCalendar = getCalendarForLocale(calendar);

        mNumberOfMonths = mTempCalendar.getActualMaximum(Calendar.MONTH) + 1;
        mShortMonths = new DateFormatSymbols().getShortMonths();
        mShortMonths = new String[mNumberOfMonths];
        for (int i = 0; i < mNumberOfMonths; ++i) {
            mShortMonths[i] = String.format("%d", i + 1);
        }
    }

    private Calendar getCalendarForLocale(Calendar oldCalendar) {
        if (oldCalendar == null) {
            return Calendar.getInstance();
        } else {
            final long currentTimeMillis = oldCalendar.getTimeInMillis();
            Calendar newCalendar = Calendar.getInstance();
            newCalendar.setTimeInMillis(currentTimeMillis);
            return newCalendar;
        }
    }

    public int getCurrentHour() {
        int currentHour = mPickerHour.getValue();
        return currentHour;
    }

    public int getCurrentMinute() {
        return mPickerMinute.getValue();
    }

    public void setMinDate(long minDate) {
        mTempCalendar.setTimeInMillis(minDate);
        if (mTempCalendar.get(Calendar.YEAR) == mMinDate.get(Calendar.YEAR)
                && mTempCalendar.get(Calendar.DAY_OF_YEAR) != mMinDate.get(Calendar.DAY_OF_YEAR)) {
            return;
        }
        mMinDate.setTimeInMillis(minDate);
        updateSpinners();
    }

    public void setMaxDate(long maxDate) {
        mTempCalendar.setTimeInMillis(maxDate);
        if (mTempCalendar.get(Calendar.YEAR) == mMaxDate.get(Calendar.YEAR)
                && mTempCalendar.get(Calendar.DAY_OF_YEAR) != mMaxDate.get(Calendar.DAY_OF_YEAR)) {
            return;
        }
        mMaxDate.setTimeInMillis(maxDate);
        updateSpinners();
    }

    private void updateSpinners() {
        // set the spinner ranges respecting the min and max dates
        if (mCurrentCalendar.equals(mMinDate)) {
            mPickerDay.setMinValue(mCurrentCalendar.get(Calendar.DAY_OF_MONTH));
            mPickerDay.setMaxValue(mCurrentCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            mPickerDay.setWrapSelectorWheel(false);
            mPickerMonth.setDisplayedValues(null);
            mPickerMonth.setMinValue(mCurrentCalendar.get(Calendar.MONTH));
            mPickerMonth.setMaxValue(mCurrentCalendar.getActualMaximum(Calendar.MONTH));
            mPickerMonth.setWrapSelectorWheel(false);
        } else if (mCurrentCalendar.equals(mMaxDate)) {
            mPickerDay.setMaxValue(mCurrentCalendar.get(Calendar.DAY_OF_MONTH));
            mPickerDay.setMinValue(mCurrentCalendar.getActualMinimum(Calendar.DAY_OF_MONTH));
            mPickerDay.setWrapSelectorWheel(false);
            mPickerMonth.setDisplayedValues(null);
            mPickerMonth.setMinValue(mCurrentCalendar.getActualMinimum(Calendar.MONTH));
            mPickerMonth.setMaxValue(mCurrentCalendar.get(Calendar.MONTH));
            mPickerMonth.setWrapSelectorWheel(false);
        } else {
            mPickerDay.setMaxValue(mCurrentCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            mPickerDay.setMinValue(1);
            mPickerDay.setWrapSelectorWheel(true);
            mPickerMonth.setDisplayedValues(null);
            mPickerMonth.setMinValue(0);
            mPickerMonth.setMaxValue(11);
            mPickerMonth.setWrapSelectorWheel(true);
        }

        String[] displayedValues = Arrays.copyOfRange(mShortMonths,
                mPickerMonth.getMinValue(), mPickerMonth.getMaxValue() + 1);
        mPickerMonth.setDisplayedValues(displayedValues);

        mPickerYear.setMinValue(mMinDate.get(Calendar.YEAR));
        mPickerYear.setMaxValue(mMaxDate.get(Calendar.YEAR));
        mPickerYear.setWrapSelectorWheel(false);

        mPickerYear.setValue(mCurrentCalendar.get(Calendar.YEAR));
        mPickerMonth.setValue(mCurrentCalendar.get(Calendar.MONTH));
        mPickerDay.setValue(mCurrentCalendar.get(Calendar.DAY_OF_MONTH));

        mPickerHour.setValue(mCurrentCalendar.get(Calendar.HOUR_OF_DAY));
        mPickerMinute.setValue(mCurrentCalendar.get(Calendar.MINUTE));
    }

    public void setOnDateTimeChangedListener(OnDateTimeChangedListener onDateTimeChangedListener) {
        mOnDateTimeChangedListener = onDateTimeChangedListener;
    }

    public interface OnDateTimeChangedListener {
        void onTimeChanged(DateTimeView view, int year, int month, int day, int hourOfDay, int minute);
    }

    private static class TwoDigitFormatter implements NumberPicker.Formatter {
        final StringBuilder mBuilder = new StringBuilder();

        java.util.Formatter mFmt;

        final Object[] mArgs = new Object[1];

        TwoDigitFormatter() {
            final Locale locale = Locale.getDefault();
            init(locale);
        }

        private void init(Locale locale) {
            mFmt = createFormatter(locale);
        }

        public String format(int value) {
            final Locale currentLocale = Locale.getDefault();
            init(currentLocale);
            mArgs[0] = value;
            mBuilder.delete(0, mBuilder.length());
            mFmt.format("%02d", mArgs);
            return mFmt.toString();
        }

        private java.util.Formatter createFormatter(Locale locale) {
            return new java.util.Formatter(mBuilder, locale);
        }
    }

}
