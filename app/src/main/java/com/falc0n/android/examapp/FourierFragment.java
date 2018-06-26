package com.falc0n.android.examapp;

import android.content.Context;
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

import com.falc0n.android.examapp.algs.furier.DFT;
import com.falc0n.android.examapp.algs.furier.GaloisField;
import com.falc0n.android.examapp.algs.furier.Pair;

import java.util.Arrays;

public class FourierFragment extends Fragment {
    private EditText nEdit, expEdit, polyEdit, vecEdit;
    private int n, exp;
    private String polynomial, vector;
    private Button mButton;
    private TextView mTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fourier, container, false);
        nEdit = (EditText) v.findViewById(R.id.fourier_n);
        nEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        expEdit = (EditText) v.findViewById(R.id.fourier_exp);
        expEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        polyEdit = (EditText) v.findViewById(R.id.fourier_poly);
        polyEdit.setInputType(InputType.TYPE_CLASS_TEXT);
        vecEdit = (EditText) v.findViewById(R.id.fourier_vector);
        vecEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        mButton = (Button) v.findViewById(R.id.fourier_button);
        mTextView = (TextView) v.findViewById(R.id.fourier_textView);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nEdit.getText().toString().length() > 0 &&
                        expEdit.getText().toString().length() > 0 &&
                        polyEdit.getText().toString().length() > 0 &&
                        vecEdit.getText().toString().length() > 0) {
                    try {
                        n = Integer.parseInt(nEdit.getText().toString());
                        exp = Integer.parseInt(expEdit.getText().toString());
                        polynomial = polyEdit.getText().toString();
                        vector = vecEdit.getText().toString();
                        DFT dft = new DFT(n, exp, polynomial);
                        String result = calculate(dft, vector);
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

    private String calculate(DFT dft, String vector) {
        StringBuilder sb = new StringBuilder();
        String[] in = vector.split("(?!^)");
        GaloisField field = dft.getField();
        sb.append(field + "\n");
        sb.append(field.tableToString(field.getTable()).replaceAll("\t", getString(R.string.tab)) + "\n");
        sb.append("Normal: V = " + Arrays.toString(in) + "\n");
        sb.append("Exponental: V = " + Arrays.toString(dft.getVec(in)) + "\n\n");

        sb.append("Direct convert:\n");
        Pair p = dft.directConvert(in);
        String[] dVec = (String[]) p.getV();
        sb.append(p.getU() + "\n");

        sb.append("Reverse convert:\n");
        p = dft.reverseConvert(dVec);
        String[] rVec = (String[]) p.getV();
        sb.append(p.getU() + "\n\n");

        sb.append("Test time polynomial:\n");
        Pair timeTest = dft.testTimePolynomial(rVec);
        sb.append(timeTest.getU() + "\n");
        sb.append("V = " + Arrays.toString((String[]) timeTest.getV()) + "\n\n");

        sb.append("Test spectre polynomial:\n");
        Pair spectreTest = dft.testSpectrePolynomial(dVec);
        sb.append(spectreTest.getU() + "\n");
        sb.append("V = " + Arrays.toString((String[]) spectreTest.getV()) + "\n");

        return sb.toString();
    }
}
