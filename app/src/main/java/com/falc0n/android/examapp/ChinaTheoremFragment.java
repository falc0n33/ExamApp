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

import com.falc0n.android.examapp.algs.china_theorem.Pair;
import com.falc0n.android.examapp.algs.china_theorem.Theorem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChinaTheoremFragment extends Fragment {
    private EditText pEdit, nEdit;
    private Button addButton, clearButton, mButton;
    private TextView mTextView;
    private List<long[]> parts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_china_theorem, container, false);
        pEdit = v.findViewById(R.id.china_theorem_p);
        pEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        nEdit = v.findViewById(R.id.china_theorem_n);
        nEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        addButton = v.findViewById(R.id.china_theorem_add);
        clearButton = v.findViewById(R.id.china_theorem_clear);
        mButton = v.findViewById(R.id.china_theorem_button);
        mTextView = v.findViewById(R.id.china_theorem_textView);
        parts = new ArrayList<>();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pEdit.getText().length() > 0 &&
                        nEdit.getText().length() > 0) {
                    long p = Long.parseLong(pEdit.getText().toString());
                    long n = Long.parseLong(nEdit.getText().toString());
                    long[] part = {p, n};
                    parts.add(part);
                    pEdit.setText("");
                    nEdit.setText("");
                    pEdit.requestFocus();
                    StringBuilder sb = new StringBuilder();
                    for (long[] pt : parts)
                        sb.append(Arrays.toString(pt) + "\n");
                    mTextView.setText(sb.toString());
                }
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parts.clear();
                pEdit.setText("");
                nEdit.setText("");
                mTextView.setText("Cleared");
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (parts.size() > 0) {
                        long[][] pairs = new long[parts.size()][1];
                        for (int i = 0; i < parts.size(); i++) {
                            pairs[i] = parts.get(i);
                        }
                        Pair res = Theorem.solve(pairs);
                        mTextView.setText(res.getU().toString());
                        parts.clear();
                    }
                } catch (Exception e) {
                    mTextView.setText(getString(R.string.error));
                }

                try {
                    final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                } catch (Exception e) {

                }
            }
        });



        return v;
    }

}
