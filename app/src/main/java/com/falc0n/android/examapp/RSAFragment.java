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

public class RSAFragment extends Fragment {
    private Button mButton;
    private EditText messageEdit, qEdit, pEdit, eEdit;
    private int message, p, q, e;
    private TextView mTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_rsa, container, false);
        messageEdit = (EditText) v.findViewById(R.id.rsa_message);
        messageEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        qEdit = (EditText) v.findViewById(R.id.rsa_q);
        qEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        pEdit = (EditText) v.findViewById(R.id.rsa_p);
        pEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        eEdit = (EditText) v.findViewById(R.id.rsa_e);
        eEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        mTextView = (TextView) v.findViewById(R.id.rsa_textView);
        mButton = (Button) v.findViewById(R.id.rsa_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (messageEdit.getText().toString().length() > 0 &&
                        qEdit.getText().toString().length() > 0 &&
                        pEdit.getText().toString().length() > 0 &&
                        eEdit.getText().toString().length() > 0) {
                    try {
                        message = Integer.parseInt(messageEdit.getText().toString());
                        q = Integer.parseInt(qEdit.getText().toString());
                        p = Integer.parseInt(pEdit.getText().toString());
                        e = Integer.parseInt(eEdit.getText().toString());

                        String result = calculate(message, p, q, e);
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    private String calculate(int message, int p, int q, int e) {
        RSA rsa = new RSA(p, q, e);

        StringBuilder sb = new StringBuilder();
        sb.append(rsa.toString());
        sb.append("-----------------------------------------");
        sb.append("\nEncryption / Decryption\n");
        Pair enctypted = rsa.encrypt(message);
        sb.append(enctypted.getU() + "\n");
        Pair decrypted = rsa.decrypt((Long) enctypted.getV());
        sb.append(decrypted.getU() + "\n");
        sb.append("-----------------------------------------");
        sb.append("\nSignature verification\n");
        Pair signature = rsa.getSignature(message);
        sb.append(signature.getU() + "\n");
        Pair verify = rsa.verifySignature(message, (long) signature.getV());
        sb.append(verify.getU() + "\n");

        return sb.toString();
    }
}
