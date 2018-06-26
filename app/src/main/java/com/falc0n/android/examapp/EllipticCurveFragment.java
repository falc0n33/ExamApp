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

import com.falc0n.android.examapp.algs.elliptic_curve.EllipticCurve;

public class EllipticCurveFragment extends Fragment {
    private EditText aEdit, bEdit, fEdit, p1Edit, p2Edit;
    private Button mButton, x2Button, sumButton;
    private TextView mTextView;
    private int a, b, f;
    private EllipticCurve curve;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_elliptic_curve, container, false);
        aEdit = v.findViewById(R.id.curve_a);
        aEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        bEdit = v.findViewById(R.id.curve_b);
        bEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        fEdit = v.findViewById(R.id.curve_field);
        fEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        p1Edit = v.findViewById(R.id.curve_p1);
        p1Edit.setInputType(InputType.TYPE_CLASS_NUMBER);
        p2Edit = v.findViewById(R.id.curve_p2);
        p2Edit.setInputType(InputType.TYPE_CLASS_NUMBER);
        mButton = v.findViewById(R.id.curve_button);
        x2Button = v.findViewById(R.id.curve_x2);
        sumButton = v.findViewById(R.id.curve_sum);
        mTextView = v.findViewById(R.id.curve_textView);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (aEdit.getText().length() > 0 &&
                            bEdit.getText().length() > 0 &&
                            fEdit.getText().length() > 0) {
                        a = Integer.parseInt(aEdit.getText().toString());
                        b = Integer.parseInt(bEdit.getText().toString());
                        f = Integer.parseInt(fEdit.getText().toString());
                        curve = new EllipticCurve("x^3+ax+b", a, b, f);
                        StringBuilder sb = new StringBuilder();
                        sb.append("Discriminant = " + curve.getDiscriminant() + "\n\n");
                        sb.append(curve.tableToString() + "\n\n");
                        mTextView.setText(sb.toString());

                        try {
                            final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                        } catch (Exception e) {

                        }
                    } else {
                        mTextView.setText("");
                    }
                } catch (Exception e) {
                    mTextView.setText(getString(R.string.error));
                }
            }
        });

        x2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (p1Edit.getText().length() > 0 && curve != null) {
                        String res = curve.dub("p" + p1Edit.getText().toString());
                        mTextView.setText("2*p" + p1Edit.getText().toString() + " = " + res);
                        try {
                            final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                        } catch (Exception e) {
                        }
                    } else {
                        mTextView.setText("");
                    }
                } catch (Exception e) {
                    mTextView.setText(getString(R.string.error));
                }
            }
        });

        sumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (p1Edit.getText().length() > 0 &&
                            p2Edit.getText().length() > 0 &&
                            curve != null) {
                        String res = curve.sum("p" + p1Edit.getText().toString(), "p" + p2Edit.getText().toString());
                        mTextView.setText("p" + p1Edit.getText().toString() + " + p" + p2Edit.getText().toString() + " = " + res);
                        try {
                            final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                        } catch (Exception e) {
                        }
                    } else {
                        mTextView.setText("");
                    }
                } catch (Exception e) {
                    mTextView.setText(getString(R.string.error));
                }
            }
        });

        return v;
    }

}
