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

import com.falc0n.android.examapp.algs.asmuth_bloom.AsmuthBloom;
import com.falc0n.android.examapp.algs.asmuth_bloom.Pair;

import java.util.ArrayList;
import java.util.List;

public class AsmuthBloomEnctyptFragment extends Fragment {
    private EditText mEdit, nEdit, tEdit, pEdit, rEdit, dEdit;
    private Button addButton, clearButton, mButton;
    private TextView mTextView;
    private long m, n, t, p, r;
    private List<Long> ds;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_asmuth_bloom_encrypt, container, false);
        mEdit = v.findViewById(R.id.asmuth_bloom_encrypt_m);
        mEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        nEdit = v.findViewById(R.id.asmuth_bloom_encrypt_n);
        nEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        tEdit = v.findViewById(R.id.asmuth_bloom_encrypt_t);
        tEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        pEdit = v.findViewById(R.id.asmuth_bloom_encrypt_p);
        pEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        rEdit = v.findViewById(R.id.asmuth_bloom_encrypt_r);
        rEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        dEdit = v.findViewById(R.id.asmuth_bloom_encrypt_d);
        dEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        addButton = v.findViewById(R.id.asmuth_bloom_encrypt_add);
        clearButton = v.findViewById(R.id.asmuth_bloom_encrypt_clear);
        mButton = v.findViewById(R.id.asmuth_bloom_encrypt_button);
        mTextView = v.findViewById(R.id.asmuth_bloom_encrypt_textView);

        ds = new ArrayList<>();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dEdit.getText().length() > 0) {
                    long d = Long.parseLong(dEdit.getText().toString());
                    ds.add(d);
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < ds.size(); i++) {
                        sb.append("d" + (i + 1) + ": " + ds.get(i) + "\n");
                    }
                    mTextView.setText(sb.toString());
                    dEdit.setText("");
                }
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ds.clear();
                mTextView.setText("Cleared");
                dEdit.setText("");
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (mEdit.getText().length() > 0 &&
                            nEdit.getText().length() > 0 &&
                            tEdit.getText().length() > 0 &&
                            pEdit.getText().length() > 0 &&
                            rEdit.getText().length() > 0 &&
                            ds.size() > 0) {
                        m = Long.parseLong(mEdit.getText().toString());
                        n = Long.parseLong(nEdit.getText().toString());
                        t = Long.parseLong(tEdit.getText().toString());
                        p = Long.parseLong(pEdit.getText().toString());
                        r = Long.parseLong(rEdit.getText().toString());

                        long[] di = new long[ds.size()];
                        for (int i = 0; i < ds.size(); i++)
                            di[i] = ds.get(i);
                        Pair encrypted = AsmuthBloom.encrypt(m, n, t, p, r, di);
                        mTextView.setText(encrypted.getU().toString());
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
