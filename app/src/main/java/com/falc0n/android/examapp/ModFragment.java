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

import com.falc0n.android.examapp.algs.mod.Solver;

public class ModFragment extends Fragment {
    private EditText aEdit, bEdit, mEdit;
    private int a, b, m;
    private Button mButton;
    private TextView mTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mod, container, false);
        aEdit = (EditText) v.findViewById(R.id.mod_a);
        aEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        bEdit = (EditText) v.findViewById(R.id.mod_b);
        bEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        mEdit = (EditText) v.findViewById(R.id.mod_m);
        mEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        mButton = (Button) v.findViewById(R.id.mod_button);
        mTextView = (TextView) v.findViewById(R.id.mod_textView);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (aEdit.getText().toString().length() > 0 &&
                        bEdit.getText().toString().length() > 0 &&
                        mEdit.getText().toString().length() > 0) {
                    try {
                        a = Integer.parseInt(aEdit.getText().toString());
                        b = Integer.parseInt(bEdit.getText().toString());
                        m = Integer.parseInt(mEdit.getText().toString());
                        String result = Solver.solve(new int[][]{{a, b, m}});
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
}
