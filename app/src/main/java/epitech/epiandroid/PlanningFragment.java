package epitech.epiandroid;

import android.content.Context;
import android.graphics.Color;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PlanningFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PlanningFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlanningFragment extends android.support.v4.app.Fragment
        implements MonthLoader.MonthChangeListener, WeekView.EventClickListener, WeekView.EventLongPressListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private WeekView mWeekView;
    private View mView;
    private String mToken = "";
    private WeekView.EventClickListener mEventClickListener;
    private WeekView.EventLongPressListener mEventLongPressListener;
    private List<Event> mEventList = new ArrayList<Event>();
    private boolean isEventdone = false;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PlanningFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlanningFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlanningFragment newInstance(String param1, String param2) {
        PlanningFragment fragment = new PlanningFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_planning, container, false);
        // Inflate the layout for this fragment
        // Get a reference for the week view in the layout.


        mWeekView = (WeekView) mView.findViewById(R.id.weekView);

        // Set an action when any event is clicked.
        mWeekView.setOnEventClickListener(this);

        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.

        mWeekView.setMonthChangeListener(this);

        // Set long press listener for events.
        mWeekView.setEventLongPressListener(mEventLongPressListener);
        return mView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public Event getEventFromTitle(String title) {
        for (Iterator<Event> it = mEventList.iterator(); it.hasNext(); ) {
            Event e = it.next();
            if (e.get_actiTitle().equals(title))
                return e;
            else ;
        }
        return null;
    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
        Log.d("Loged", "On Event Click");
        final Event e = getEventFromTitle(event.getName());
        if (e.is_allowToken() == true) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Token");

// Set up the input
            final EditText input = new EditText(getContext());
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            builder.setView(input);

// Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mToken = input.getText().toString();
                    RequestManager.getInstance().useToken(e, mToken, new APIListener<Boolean>() {

                        @Override
                        public void getResult(Boolean object) {

                        }
                    });
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        }
    }


    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {

    }

    MonthLoader.MonthChangeListener mMonthChangeListener = new MonthLoader.MonthChangeListener() {
        @Override
        public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {
            // Populate the week view with some events.
            List<WeekViewEvent> events = getEvents(newYear, newMonth);
            return events;
        }
    };

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        List<WeekViewEvent> events = getEvents(newYear, newMonth);
        return events;    }

    public List<WeekViewEvent> getEvents(int newYear, int newMonth)
    {
        List<WeekViewEvent> myWeek = new ArrayList<>();
        String month = String.valueOf(newMonth);
        if (newMonth < 10)
            month = "0" + String.valueOf(newMonth);
        String dateS = String.valueOf(newYear) + "-" + month + "-01";
        String dateE = String.valueOf(newYear) + "-" + month + "-07";
        if (isEventDone == false) {
            RequestManager.getInstance().getEvents(dateS, dateE, new APIListener<List<Event>>() {
                @Override
                public void getResult(List<Event> object) {
                    mEventList = object;
                    isEventDone = true;
                    mWeekView.notifyDatasetChanged();
                }
            });//&start=2016-01-17&end=2016-01-22
        }
        int i = 0;
        for (Iterator<Event> it = mEventList.iterator(); it.hasNext();) {
            Event e = it.next();
            Calendar calStart = Calendar.getInstance();
            calStart.setTime(e.get_start());
            Calendar calEnd = Calendar.getInstance();
            calEnd.setTime(e.get_end());
            WeekViewEvent event = new WeekViewEvent(i, e.get_actiTitle(), calStart, calEnd);
            if (e.get_codeModule() == null || String.format("#%X", e.get_codeModule().hashCode()).length() != 9)
                ;
            else
                event.setColor(Color.parseColor(String.format("#%X", e.get_codeModule().hashCode())));
            if (e.get_actiTitle() != null)
                myWeek.add(event);
            i++;
        }
        return myWeek;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
