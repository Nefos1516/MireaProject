package ru.mirea.netelev.mireaproject;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalculatorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalculatorFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Button button;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CalculatorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalculatorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalculatorFragment newInstance(String param1, String param2) {
        CalculatorFragment fragment = new CalculatorFragment();
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_calculator, container, false);
        TextView textView = view.findViewById(R.id.textView2);
        process(view, textView, R.id.zero);
        process(view, textView, R.id.one);
        process(view, textView, R.id.two);
        process(view, textView, R.id.three);
        process(view, textView, R.id.four);
        process(view, textView, R.id.five);
        process(view, textView, R.id.six);
        process(view, textView, R.id.seven);
        process(view, textView, R.id.eight);
        process(view, textView, R.id.nine);
        process(view, textView, R.id.equals);
        process(view, textView, R.id.divided);
        process(view, textView, R.id.delete);
        process(view, textView, R.id.minus);
        process(view, textView, R.id.plus);
        process(view, textView, R.id.multiply);
        process(view, textView, R.id.point);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void process(View view, TextView textView, int id){
        button =  view.findViewById(id);
        String text = (String) button.getText();
        button.setOnClickListener(view1 -> {
            switch (text){
                case "C":
                    textView.setText("");
                    break;
                case "=":
                    textView.append("=" + calculate(textView.getText().toString()));
                    break;
                default:
                    textView.append(text);
            }
        });
    }

    private String calculate(String s){
        float ans = 0;
        if (s.contains("+"))
        {
            String[] a = s.split("\\+");
            ans = Float.parseFloat(a[0]) + Float.parseFloat(a[1]);
        }
        else if (s.contains("-"))
        {
            String[] a = s.split("-");
            ans = Float.parseFloat(a[0]) - Float.parseFloat(a[1]);
        }
        else if (s.contains("*"))
        {
            String[] a = s.split("\\*");
            ans = Float.parseFloat(a[0]) * Float.parseFloat(a[1]);
        }
        else if (s.contains("/"))
        {
            String[] a = s.split("/");
            ans = Float.parseFloat(a[0]) / Float.parseFloat(a[1]);
        }
        else
        {
            ans = Float.parseFloat(s);
        }
        return Float.toString(ans);
    }
}