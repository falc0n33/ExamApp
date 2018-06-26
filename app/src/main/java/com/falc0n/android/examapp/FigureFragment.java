package com.falc0n.android.examapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.falc0n.android.examapp.algs.figure.Service;

import java.util.List;

public class FigureFragment extends Fragment {
    private EditText nEdit;
    private Button mButton;
    private TextView mTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_figure, container, false);
        nEdit = (EditText) v.findViewById(R.id.figure_n);
        nEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        mButton = (Button) v.findViewById(R.id.figure_button);
        mTextView = (TextView) v.findViewById(R.id.figure_textView);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nEdit.getText().toString().length() > 0) {
                    int n = Integer.parseInt(nEdit.getText().toString());
                    String result = calculate(n);
                    mTextView.setText(result);

                    try {
                        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                    } catch (Exception e) {

                    }
                } else {
                    mTextView.setText("");
                }
            }
        });

        return v;
    }

    private String calculate(int n) {
        StringBuilder sb = new StringBuilder();

        String figure = Service.getChars(n);
        sb.append("Figure: " + figure + "\n\n");
        List<String>[] operations = Service.getOperations2(figure);
        sb.append(Service.getStringOperations(operations) + "\n");
        sb.append(Service.getCayleyTable(operations).replaceAll("\t", getString(R.string.tab)) + "\n");
        List<String>[] groups = Service.getGroups(operations);
        sb.append(Service.getStringGroups(groups).replaceAll("\t", getString(R.string.tab)) + "\n");
        sb.append(Service.getLeftRightClass(operations, groups) + "\n");

        return sb.toString();
    }
}
