package epitech.epiandroid;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import java.util.List;

/**
 * Created by girard_s on 26/01/2016 for Epitech.
 */
public class ModuleOverviewFragment extends android.support.v4.app.Fragment{
    public ListView _moduleView;

    private OnFragmentInteractionListener mListener;

    public ModuleOverviewFragment() {
        // Required empty public constructor
    }

    public static ModuleOverviewFragment newInstance(String param1, String param2) {
        ModuleOverviewFragment fragment = new ModuleOverviewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {

        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Auto-generated method stub
        int semester = MainActivity.getUser().getSemester();
        for (int i = 0; i <= semester; i++)
            menu.add(i, i, i, "Semester " + i);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        RequestManager.getInstance().getUserModule(item.getItemId(), new APIListener<List<ModuleOverview>>() {
            @Override
            public void getResult(List<ModuleOverview> object) {
                ModuleOverviewAdapter adapter = new ModuleOverviewAdapter(getContext(), object);
                _moduleView.setAdapter(adapter);
            }
        });
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_module_overview, container, false);

        _moduleView = (ListView) rootView.findViewById(R.id.moduleListView);
        RequestManager.getInstance().getUserModule(-1, new APIListener<List<ModuleOverview>>() {
            @Override
            public void getResult(List<ModuleOverview> object) {
                ModuleOverviewAdapter adapter = new ModuleOverviewAdapter(getContext(), object);
                _moduleView.setAdapter(adapter);
            }
        });

        _moduleView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final ModuleOverview module  = (ModuleOverview)_moduleView.getItemAtPosition(position);
                new AlertDialog.Builder(getContext())
                        .setTitle("Inscription")
                        .setMessage("Voulez vous vraiment vous inscrire au module " + module.getTitle())
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                RequestManager.getInstance().registerToModule(module, new APIListener<Boolean>(){
                                    public void getResult(Boolean bool) {
                                        if (bool == true);
                                        else {
                                            Toast toast = Toast.makeText(getContext(), "Cannot Register", Toast.LENGTH_SHORT);
                                            toast.show();
                                        }
                                    }
                                });
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });


        return rootView;
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
