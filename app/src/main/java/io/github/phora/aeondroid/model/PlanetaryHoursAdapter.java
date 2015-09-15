package io.github.phora.aeondroid.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

import io.github.phora.aeondroid.EphemerisUtils;
import io.github.phora.aeondroid.R;
import io.github.phora.aeondroid.drawables.EquilateralTriangle;
import io.github.phora.aeondroid.drawables.Rhombus;
import swisseph.SweDate;

/**
 * Created by phora on 9/9/15.
 */
public class PlanetaryHoursAdapter extends ArrayAdapter<PlanetaryHour> {

    private static Drawable BASE_CHAKRA = null;
    private static Drawable SACRAL_CHAKRA = null;
    private static Drawable SOLAR_CHAKRA = null;
    private static Drawable HEART_CHAKRA = null;
    private static Drawable THROAT_CHAKRA = null;
    private static Drawable SIXTH_CHAKRA = null;
    private static Drawable CROWN_CHAKRA = null;

    private int hourSelection = -1;
    private int hourStyle = 0;

    public PlanetaryHoursAdapter(Context context, ArrayList<PlanetaryHour> phours) {
        super(context, 0, phours);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PlanetaryHour ph = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.phours_item, parent, false);
        }

        ImageView pHoursIcon = (ImageView)convertView.findViewById(R.id.PlanetaryHours_Icon);
        TextView pHoursName = (TextView)convertView.findViewById(R.id.PlanetaryHours_Name);
        TextView pHoursTime = (TextView)convertView.findViewById(R.id.PlanetaryHours_Time);
        View pDayStripe = convertView.findViewById(R.id.PlanetaryHours_DayStripe);

        String[] planets = getContext().getResources().getStringArray(R.array.PlanetNames);
        Date d = SweDate.getDate(ph.getHourStamp());

        if (hourSelection == position) {
            TypedValue typedValue = new TypedValue();
            getContext().getTheme().resolveAttribute(R.attr.PlanetaryHours_Current, typedValue, true);
            if (typedValue.type == TypedValue.TYPE_REFERENCE) {
                convertView.setBackgroundResource(typedValue.resourceId);
            } else {
                convertView.setBackgroundColor(typedValue.data);
            }
        }
        else {
            convertView.setBackgroundResource(0);
        }

        pHoursTime.setText(EphemerisUtils.DATE_FMT.format(d));
        pHoursName.setText(planets[ph.getPlanetType()]);
        //Log.d("PlanetaryHoursAdapter", "Is night?: " + ph.isNight());
        if (!ph.isNight()) {
            pDayStripe.setVisibility(View.INVISIBLE);
        }
        else {
            pDayStripe.setVisibility(View.VISIBLE);
        }
        //Log.d("PlanetaryHoursAdapter", "Drawable set: "+ph.getPlanetType());

        if (hourStyle == 0) {
            pHoursIcon.setImageDrawable(getChakraDrawable(ph.getPlanetType()));
        }
        else if (hourStyle == 1) {
            pHoursIcon.setImageResource(getPlanetSymbol(ph.getPlanetType()));
        }

        return convertView;
    }

    private boolean getIsDark() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        boolean isdark = preferences.getBoolean("isDark", false);
        return isdark;
    }
    
    private int getPlanetSymbol(int i) {
        if (getIsDark()) {
            switch (i) {
                case 0:
                    return R.drawable.sun_dark;
                case 1:
                    return R.drawable.venus_dark;
                case 2:
                    return R.drawable.mercury_dark;
                case 3:
                    return R.drawable.moon_dark;
                case 4:
                    return R.drawable.saturn_dark;
                case 5:
                    return R.drawable.jupiter_dark;
                case 6:
                    return R.drawable.mars_dark;
                default:
                    return 0;
            }
        } else {
            switch (i) {
                case 0:
                    return R.drawable.sun_light;
                case 1:
                    return R.drawable.venus_light;
                case 2:
                    return R.drawable.mercury_light;
                case 3:
                    return R.drawable.moon_light;
                case 4:
                    return R.drawable.saturn_light;
                case 5:
                    return R.drawable.jupiter_light;
                case 6:
                    return R.drawable.mars_light;
                default:
                    return 0;
            }            
        }
    }

    private Drawable getChakraDrawable(int i) {
        Drawable d = null;
        switch(i) {
            case 0:
                if (SOLAR_CHAKRA == null) {
                    SOLAR_CHAKRA = new EquilateralTriangle(Color.argb(255, 255, 200, 0),
                            EquilateralTriangle.Direction.SOUTH);
                }
                d = SOLAR_CHAKRA;
                break;
            case 1:
                if (HEART_CHAKRA == null) {
                    HEART_CHAKRA = new Rhombus(Color.GREEN, Rhombus.Direction.VERTICAL);
                }
                d = HEART_CHAKRA;
                break;
            case 2:
                if (THROAT_CHAKRA == null) {
                    THROAT_CHAKRA = new EquilateralTriangle(Color.argb(255, 0, 178, 255),
                            EquilateralTriangle.Direction.SOUTH);
                }
                d = THROAT_CHAKRA;
                break;
            case 3:
                if (SIXTH_CHAKRA == null) {
                    SIXTH_CHAKRA =  new EquilateralTriangle(Color.BLUE,
                            EquilateralTriangle.Direction.SOUTH);
                }
                d = SIXTH_CHAKRA;
                break;
            case 4:
                if (BASE_CHAKRA == null) {
                    BASE_CHAKRA = new EquilateralTriangle(Color.RED,
                            EquilateralTriangle.Direction.NORTH);
                }
                d = BASE_CHAKRA;
                break;
            case 5:
                if (CROWN_CHAKRA == null) {
                    CROWN_CHAKRA =  new EquilateralTriangle(Color.argb(255, 121, 0, 255),
                            EquilateralTriangle.Direction.SOUTH);
                }
                d = CROWN_CHAKRA;
                break;
            case 6:
                if (SACRAL_CHAKRA == null) {
                    SACRAL_CHAKRA = new EquilateralTriangle(Color.argb(255, 255, 116, 0),
                            EquilateralTriangle.Direction.NORTH);
                }
                d = SACRAL_CHAKRA;
                break;
        }
        return d;
    }

    public void setHourSelection(int hourSelection) {
        boolean call_invalidate = (this.hourSelection != hourSelection);
        this.hourSelection = hourSelection;
        if (call_invalidate) {
            notifyDataSetChanged();
        }
    }

    public int getHourSelection() {
        return hourSelection;
    }

    public int getHourStyle() {
        return hourStyle;
    }

    public void setHourStyle(int hourStyle) {
        boolean call_invalidate = (this.hourStyle != hourStyle);
        this.hourStyle = hourStyle;
        if (call_invalidate) {
            notifyDataSetChanged();
        }
    }
}
