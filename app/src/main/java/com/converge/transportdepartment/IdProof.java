package com.converge.transportdepartment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IdProof#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IdProof extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TableLayout tableLayout2;
    TableLayout tableLayout3;
    TableLayout tableLayout4;

    private static int count=0;

    public IdProof() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IdProof.
     */
    // TODO: Rename and change types and number of parameters
    public static IdProof newInstance(String param1, String param2) {
        IdProof fragment = new IdProof();
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
        // Inflate the layout for this fragment
//        return
        count=0;
              View view =  inflater.inflate(R.layout.fragment_id_proof, container, false);
        Button button = (Button)view.findViewById(R.id.buttonNextIdProofNext);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickIdProof(v);
            }
        });

//        Button buttonAdd= (Button) view.findViewById(R.id.buttonAdd);
//        buttonAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onClickIdProof(v);
//            }
//        });
//
//        Button buttonRemove = (Button) view.findViewById(R.id.buttonRemove);
//        buttonRemove.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onClickIdProof(v);
//            }
//        });

        ImageView img1 =(ImageView) view.findViewById(R.id.buttonAdd);
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickIdProof(v);
            }
        });

        ImageView img2 =(ImageView) view.findViewById(R.id.buttonRemove);
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickIdProof(v);
            }
        });

        tableLayout2 = (TableLayout) view.findViewById(R.id.table2);
        tableLayout3 = (TableLayout) view.findViewById(R.id.table3);
        tableLayout4 = (TableLayout) view.findViewById(R.id.table4);
            return view;
    }

    public void onClickIdProof(View view) {
        switch (view.getId()) {
            case R.id.buttonNextIdProofNext:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_home, LicenseApplication.newInstance("4", "1")).commit();
//                }
                break;
            case R.id.buttonBackPersonalDetail:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_home, LicenseApplication.newInstance("2", "1")).commit();
                break;
            case R.id.buttonClearPersonalDetail:

                break;
            case R.id.buttonAdd:
                if(count==0)
                {
                    count++;
                    tableLayout2.setVisibility(View.VISIBLE);
                }
                else if(count==1)
                {
                    count++;
                    tableLayout3.setVisibility(View.VISIBLE);
                }
                else if(count==2)
                {
                    count++;
                    tableLayout4.setVisibility(View.VISIBLE);
                }
                else if(count==3)
                {
                    showToast("Cannot Add More");
                }
                break;
            case R.id.buttonRemove:
                if(count==0)
                {
                    showToast("Cannot Remove More");
                }
                else if(count==1)
                {
                    count--;
                    tableLayout2.setVisibility(View.INVISIBLE);
                }
                else if(count==2)
                {
                    count--;
                    tableLayout3.setVisibility(View.INVISIBLE);
                }
                else if(count==3)
                {
                    count--;
                    tableLayout4.setVisibility(View.GONE);
                }

                break;
        }
    }
    @Override
    public void onClick(View v) {

    }
    private void showToast(String s)
    {
        Toast.makeText(getActivity(),s,Toast.LENGTH_SHORT).show();
    }

}
