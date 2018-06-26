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

import com.falc0n.android.examapp.algs.el_gamal.ElGamal;
import com.falc0n.android.examapp.algs.el_gamal.Pair;

import java.util.Arrays;

public class ElGamalSignatureFragment extends Fragment {

    private EditText mEdit, pEdit, gEdit, xEdit, kEdit, signEdit;
    private Button signButton, verifyButton;
    private TextView mTextView;
    private int m, p, g, x, k;
    private ElGamal elGamal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_el_gamal_signature, container, false);
        mEdit = v.findViewById(R.id.el_sig_message);
        mEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        signEdit = v.findViewById(R.id.el_sig_signature);
        signEdit.setInputType(InputType.TYPE_CLASS_TEXT);
        pEdit = v.findViewById(R.id.el_sig_p);
        pEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        gEdit = v.findViewById(R.id.el_sig_g);
        gEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        xEdit = v.findViewById(R.id.el_sig_x);
        xEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        kEdit = v.findViewById(R.id.el_sig_k);
        kEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        signButton = v.findViewById(R.id.el_sig_button);
        verifyButton = v.findViewById(R.id.el_sig_verify);
        mTextView = v.findViewById(R.id.el_sig_textView);

        signButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEdit.getText().length() > 0 &&
                        pEdit.getText().length() > 0 &&
                        gEdit.getText().length() > 0 &&
                        xEdit.getText().length() > 0 &&
                        kEdit.getText().length() > 0) {
                    try {
                        m = Integer.parseInt(mEdit.getText().toString());
                        p = Integer.parseInt(pEdit.getText().toString());
                        g = Integer.parseInt(gEdit.getText().toString());
                        x = Integer.parseInt(xEdit.getText().toString());
                        k = Integer.parseInt(kEdit.getText().toString());
                        elGamal = new ElGamal(p, g, x, k);
                        Pair signature = elGamal.getSignature(m);
                        mTextView.setText(signature.getU().toString());
                    } catch (Exception e) {
                        mTextView.setText(getString(R.string.error));
                    }
                } else {
                    mTextView.setText("");
                }

                try {
                    final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                } catch (Exception e) {
                }
            }
        });

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEdit.getText().length() > 0 &&
                        signEdit.getText().length() > 0 &&
                        pEdit.getText().length() > 0 &&
                        gEdit.getText().length() > 0 &&
                        xEdit.getText().length() > 0 &&
                        kEdit.getText().length() > 0) {
                    try {
                        m = Integer.parseInt(mEdit.getText().toString());
                        p = Integer.parseInt(pEdit.getText().toString());
                        g = Integer.parseInt(gEdit.getText().toString());
                        x = Integer.parseInt(xEdit.getText().toString());
                        k = Integer.parseInt(kEdit.getText().toString());

                        String s = signEdit.getText().toString();
                        String[] sn = s.split("(\\D|\\s)+");
                        long[] rs = {Long.parseLong(sn[0]), Long.parseLong(sn[1])};

                        elGamal = new ElGamal(p, g, x, k);
                        Pair signature = elGamal.verifySignature(m, rs);
                        mTextView.setText(signature.getU().toString());
                    } catch (Exception e) {
                        mTextView.setText(getString(R.string.error));
                    }
                } else {
                    mTextView.setText("");
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
