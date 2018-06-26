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

import com.falc0n.android.examapp.algs.rsa.Pair;
import com.falc0n.android.examapp.algs.rsa.RSA;

public class RSASignatureFragment extends Fragment {

    private Button signButton, verifyButton;
    private EditText mEdit, sigEdit, qEdit, pEdit, eEdit;
    private int message, signature, p, q, e;
    private TextView mTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.fragment_rsasignature, container, false);

        mEdit = (EditText) v.findViewById(R.id.rsa_sig_message);
        mEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        sigEdit = (EditText) v.findViewById(R.id.rsa_sig_signature);
        sigEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        qEdit = (EditText) v.findViewById(R.id.rsa_sig_q);
        qEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        pEdit = (EditText) v.findViewById(R.id.rsa_sig_p);
        pEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        eEdit = (EditText) v.findViewById(R.id.rsa_sig_e);
        eEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        mTextView = (TextView) v.findViewById(R.id.rsa_sig_textView);
        signButton = (Button) v.findViewById(R.id.rsa_sig_button_sign);
        verifyButton = (Button) v.findViewById(R.id.rsa_sig_button_verify);

        signButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEdit.getText().toString().length() > 0 &&
                        qEdit.getText().toString().length() > 0 &&
                        pEdit.getText().toString().length() > 0 &&
                        eEdit.getText().toString().length() > 0) {
                    try {
                        message = Integer.parseInt(mEdit.getText().toString());
                        q = Integer.parseInt(qEdit.getText().toString());
                        p = Integer.parseInt(pEdit.getText().toString());
                        e = Integer.parseInt(eEdit.getText().toString());

                        RSA rsa = new RSA(p, q, e);
                        Pair signature = rsa.getSignature(message);
                        String result = "Signature:\n" + signature.getU().toString();
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

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEdit.getText().toString().length() > 0 &&
                        sigEdit.getText().toString().length() > 0 &&
                        qEdit.getText().toString().length() > 0 &&
                        pEdit.getText().toString().length() > 0 &&
                        eEdit.getText().toString().length() > 0) {
                    try {
                        message = Integer.parseInt(mEdit.getText().toString());
                        signature = Integer.parseInt(sigEdit.getText().toString());
                        q = Integer.parseInt(qEdit.getText().toString());
                        p = Integer.parseInt(pEdit.getText().toString());
                        e = Integer.parseInt(eEdit.getText().toString());

                        RSA rsa = new RSA(p, q, e);
                        Pair verify = rsa.verifySignature(message, signature);
                        String result = "Verification:\n" + verify.getU().toString();
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
