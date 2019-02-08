package com.example.rahul.technicial_side_app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rahul.technicial_side_app.DataTypes.Customer;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link dutyPage.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link dutyPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class dutyPage extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ArrayList<Customer> customers;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public dutyPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @param param2 Parameter 2.
     * @return A new instance of fragment dutyPage.
     */
    // TODO: Rename and change types and number of parameters
    public static dutyPage newInstance(ArrayList<Customer> customers, String param2) {
        dutyPage fragment = new dutyPage();
        fragment.customers=customers;
        Bundle args = new Bundle();
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

        //customer.add(new Customer("Salman k","Jayanagar 7th block,3rd street","326",0,0,"9008522228"));
        //customer.add(new Customer("Rahul George","Mysore road,sector 3","22",0,0,"7795319391"));
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_duty_page,container,false);
        ListView lv=view.findViewById(R.id.lv);

        Log.e("duty page","duty page loaded");
        Custom_duty_item items=new Custom_duty_item(getActivity(),this.customers);
        lv.setAdapter(items);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent a=new Intent(getContext(),duty_map.class);
                Toast.makeText(getContext(),"customer id"+customers.get(i).getName(),Toast.LENGTH_SHORT).show();
                a.putExtra("customer",customers.get(i));
                startActivity(a);
            }
        });

        TextView username=view.findViewById(R.id.username);
        TextView homeno=view.findViewById(R.id.homeno);
        TextView address=view.findViewById(R.id.street);
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
