package epitech.epiandroid;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProjectViewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProjectViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProjectViewFragment extends android.support.v4.app.Fragment {
    private Project _project;
    View view;
    private TextView _projectName;
    private ProgressBar prg;

    private OnFragmentInteractionListener mListener;

    public ProjectViewFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ProjectViewFragment newInstance(ProjectOverview project) {
        ProjectViewFragment fragment = new ProjectViewFragment();
        Bundle args = new Bundle();
        args.putString("scolarYear", project.getScolarYear());
        args.putString("moduleCode", project.getModuleCode());
        args.putString("instanceCode", project.getInstanceCode());
        args.putString("activityCode", project.getActivityCode());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_project_view, container, false);
        prg = (ProgressBar)view.findViewById(R.id.progressProject);
        _projectName = (TextView) view.findViewById(R.id.projectName);
        RequestManager.getInstance().getProjectData(getArguments().getString("scolarYear"),
                getArguments().getString("moduleCode"),
                getArguments().getString("instanceCode"),
                getArguments().getString("activityCode"), new APIListener<Project>() {
                    @Override
                    public void getResult(Project object) {
                        _project = object;
                        prg.setVisibility(View.GONE);
                        Log.d("Value", String.valueOf(_project.getTimePercentage()));
                        prg.setProgress((int) _project.getTimePercentage());
                        prg.setVisibility(View.VISIBLE);
                        _projectName.setText(_project.get_projectTitle());
                    }
                });
        return view;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
