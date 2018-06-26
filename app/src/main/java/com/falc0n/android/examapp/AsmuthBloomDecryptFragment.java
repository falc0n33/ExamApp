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

import com.falc0n.android.examapp.algs.asmuth_bloom.AsmuthBloom;
import com.falc0n.android.examapp.algs.asmuth_bloom.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AsmuthBloomDecryptFragment extends Fragment {

    private EditText pEdit, dEdit, mEdit;
    private Button addButton, clearButton, mButton;
    private TextView mTextView;
    private long p, d, m;
    private List<long[]> parts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_asmuth_bloom_decrypt, container, false);

        pEdit = v.findViewById(R.id.asmuth_bloom_decrypt_p);
        pEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        dEdit = v.findViewById(R.id.asmuth_bloom_decrypt_d);
        dEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        mEdit = v.findViewById(R.id.asmuth_bloom_decrypt_m);
        mEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        addButton = v.findViewById(R.id.asmuth_bloom_decrypt_add);
        clearButton = v.findViewById(R.id.asmuth_bloom_decrypt_clear);
        mButton = v.findViewById(R.id.asmuth_bloom_decrypt_button);
        mTextView = v.findViewById(R.id.asmuth_bloom_decrypt_textView);

        parts = new ArrayList<>();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pEdit.getText().length() > 0 &&
                        dEdit.getText().length() > 0 &&
                        mEdit.getText().length() > 0) {
                    p = Long.parseLong(pEdit.getText().toString());
                    d = Long.parseLong(dEdit.getText().toString());
                    m = Long.parseLong(mEdit.getText().toString());

                    long[] part = {p, d, m};
                    parts.add(part);

                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < parts.size(); i++)
                        sb.append("k" + (i + 1) + ": " + Arrays.toString(parts.get(i)) + "\n");
                    mTextView.setText(sb.toString());
                    //pEdit.setText("");
                    dEdit.setText("");
                    mEdit.setText("");
                    dEdit.requestFocus();
                }
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parts.clear();
                pEdit.setText("");
                dEdit.setText("");
                mEdit.setText("");
                mTextView.setText("Cleared");
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (parts.size() > 0) {
                        long[][] ps = new long[parts.size()][];
                        for (int i = 0; i < parts.size(); i++)
                            ps[i] = parts.get(i);
                        Pair decrypted = AsmuthBloom.decrypt(ps);
                        mTextView.setText(decrypted.getU().toString());
                        parts.clear();
                        pEdit.setText("");
                        dEdit.setText("");
                        mEdit.setText("");
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
