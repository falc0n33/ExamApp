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

import com.falc0n.android.examapp.algs.galois.GaloisField;

import java.util.List;
import java.util.Map;

public class GaloisFragment extends Fragment {
    private EditText nEdit, expEdit, polyEdit;
    private int n, exp;
    private String polynomial;
    private Button mButton;
    private TextView mTextView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_galois, container, false);
        nEdit = (EditText) v.findViewById(R.id.galois_n);
        nEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        expEdit = (EditText) v.findViewById(R.id.galois_exp);
        expEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        polyEdit = (EditText) v.findViewById(R.id.galois_poly);
        polyEdit.setInputType(InputType.TYPE_CLASS_TEXT);
        mButton = (Button) v.findViewById(R.id.galois_button);
        mTextView = (TextView) v.findViewById(R.id.galois_textView);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nEdit.getText().toString().length() > 0 &&
                        expEdit.getText().toString().length() > 0 &&
                        polyEdit.getText().toString().length() > 0) {
                    try {
                        n = Integer.parseInt(nEdit.getText().toString());
                        exp = Integer.parseInt(expEdit.getText().toString());
                        polynomial = polyEdit.getText().toString();
                        GaloisField field = new GaloisField(n, exp, polynomial);
                        String result = calculate(field).replaceAll("\t", getString(R.string.tab));
                        mTextView.setText(result);
                    } catch (Exception e) {
                        mTextView.setText(getString(R.string.error));
                    }

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

    private String calculate(GaloisField field) {
        StringBuilder sb = new StringBuilder();
        int[][] table = field.getTable();

        sb.append(field.tableToString(field.getTable()) + "\n");
        sb.append("Classes: ");
        Map<String, List<String>> classes = field.getClasses(table);
        sb.append(classes + "\n\n");
        sb.append("Traces: ");
        Map<String, Integer> traces = field.getTraces(table, classes);
        sb.append(traces + "\n\n");

        return sb.toString();
    }

}
